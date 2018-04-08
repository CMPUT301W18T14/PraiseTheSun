/*
 * Copyright © 2018 Chase Buhler, Imtihan Ahmed, Thomas Lafrance, Ryan Romano, Stephen Packer,
 * Alden Emerson Ern Tan
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualbert.cs.tasko.Commands.DataCommands;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Delete;

/**
 * An extension of the DeleteCommand class. When a DeleteNotificationCommand is executed,
 * it will attempt to query our database using Elasticsearch in order to delete
 * the notification with a given notificationId from the database.
 *
 * @see DeleteCommand
 * @author tlafranc
 */
public class DeleteNotificationCommand extends DeleteCommand<String> {
    private String notificationId;

    /**
     * DeleteNotificationCommand Constructor. Requires a notificationid to be initialized
     *
     * @param notificationId The notificationId of the notification to be deleted
     */
    public DeleteNotificationCommand(String notificationId) {
        super(notificationId, "DeleteNotificationCommand");
        this.notificationId = notificationId;
    }

    @Override
    public void execute() {
        DeleteNotificationAsyncTask delete = new DeleteNotificationAsyncTask();
        delete.execute(notificationId);
    }

    @Override
    public void undo() {

    }

    private class DeleteNotificationAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            JestWrapper.verifySettings();

            Delete delete = new Delete.Builder(strings[0]).index(JestWrapper.getIndex()).type
                    ("notification").build();
            try {
                JestWrapper.getClient().execute(delete);
            } catch (IOException e){
                Log.i("Delete Task Command", "Could not execute the delete task Jest");
            }

            return null;
        }
    }
}
