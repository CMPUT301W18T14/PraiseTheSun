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

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.BidList;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationFactory;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationType;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.JestWrapper;
import ca.ualbert.cs.tasko.data.NoInternetException;
import io.searchbox.core.Delete;

/**
 * An extension of the DeleteCommand class. When a DeleteTaskCommand is executed, it will
 * attempt to query our database using Elasticsearch in order to delete the task object
 * passed as a parameter from the database.
 *
 * @see DeleteCommand
 * @author Chase Buhler
 */
public class DeleteTaskCommand extends DeleteCommand<Task> {

    private Task task;

    public DeleteTaskCommand(Task task){
        super(task, "DeleteTaskCommand");
        this.task = task;
    }

    @Override
    public void execute() {
        //Update Bids related to this task on another thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataManager dm = DataManager.getInstance();
                try {
                    BidList bids = dm.getTaskBids(task.getId());
                    for(Bid bid: bids.getBids()) {
                        dm.deleteBid(bid);
                    }
                } catch (NoInternetException e){
                    Log.i("Delete Task Error", "Unable to remove bids from task due to lost " +
                            "connection");
                }
            }
        }).start();
        DeleteTaskAsycTask delete = new DeleteTaskAsycTask();
        delete.execute(task.getId());
    }

    @Override
    public void undo() {

    }

    private class DeleteTaskAsycTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            JestWrapper.verifySettings();

            Delete delete = new Delete.Builder(strings[0]).index(JestWrapper.getIndex()).type
                    ("task").build();
            try {
                JestWrapper.getClient().execute(delete);
            } catch (IOException e){
                Log.i("Delete Task Command", "Could not execute the delete task Jest");
            }

            return null;
        }
    }
}
