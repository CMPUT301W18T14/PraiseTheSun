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
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.JestWrapper;
import ca.ualbert.cs.tasko.data.NoInternetException;
import io.searchbox.core.Delete;

/**
 * An extension of the DeleteCommand class. When a DeleteBidCommand is executed, it will
 * attempt to query our database using Elasticsearch in order to delete the bid object
 * passed into the class as a parameter from the database.
 *
 * @see DeleteCommand
 * @author Chase Buhler
 */
public class DeleteBidCommand extends DeleteCommand<Bid> {

    private Bid bid;

    public DeleteBidCommand(Bid bid){
        super(bid, "DeleteBidCommand");
        this.bid = bid;
    }

    @Override
    public void execute() {
        try {
            DataManager dm = DataManager.getInstance();
            Task task = dm.getTask(bid.getTaskID());
            if (task != null) {
                if (bid.getValue() == task.getMinBid()) {
                    BidList bids = dm.getTaskBids(task.getId());
                    bids.removeBid(bid);
                    task.setMinBid(bids.getMinBid().getValue());
                    dm.putTask(task);
                }
            }
        } catch (NoInternetException e){
            Log.i("Delete Bid Command", "Failed to update minBid due to lost connection");
        }
        DeleteBidAsyncTask delete = new DeleteBidAsyncTask();
        delete.execute(bid.getBidID());
    }

    @Override
    public void undo() {

    }

    private static class DeleteBidAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            JestWrapper.verifySettings();

            Delete delete = new Delete.Builder(strings[0]).index(JestWrapper.getIndex()).type
                    ("bid").build();
            try {
                JestWrapper.getClient().execute(delete);
            } catch (IOException e){
                Log.i("Delete Bid Command", "Could not execute the delete bid Jest statement");
            }

            return null;
        }
    }
}
