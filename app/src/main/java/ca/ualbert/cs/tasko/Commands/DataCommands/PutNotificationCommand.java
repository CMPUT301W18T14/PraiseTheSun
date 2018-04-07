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

import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by chase on 3/27/2018.
 */

public class PutNotificationCommand extends PutCommand<Notification> {

    Notification not;

    /**
     * Constructor for the PutNotificationCommand. Requires the parameter notification in order
     * to be initialized.
     *
     * @param notification the notification object to be stored in the database
     */
    public PutNotificationCommand(Notification notification){
        super(notification, "PutNotificationCommand");
        not = notification;
    }

    /**
     * Once execute is called. The method first builds a put request and then
     * attempts to add notification to the elastic search Database.
     */
    @Override
    public void execute() {
        PutNotificationTask pnt = new PutNotificationTask();
        pnt.execute(not);
        try{
            not.setId(pnt.get());
        } catch (Exception e){
            Log.i("Error", "Things went wrong in putNotification AsyncObject");
        }
    }

    //TODO
    @Override
    public void undo() {
        //TODO: Implement delete from database
    }

    /**
     * Return true because we can undo this command by deleting the notification that
     * we added.
     * @return true
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    private class PutNotificationTask extends AsyncTask<Notification, Void, String> {

        @Override
        protected String doInBackground(Notification... notifications) {
            JestWrapper.verifySettings();

            Index index = new Index.Builder(notifications[0]).index(JestWrapper.getIndex())
                    .type("notification")
                    .build();

            try{
                DocumentResult result = JestWrapper.getClient().execute(index);
                if(result.isSucceeded()){
                    return result.getId();
                }
            } catch (Exception e){
                Log.i("Error", "The application failed to build and send the task");
            }

            return null;
        }
    }
}
