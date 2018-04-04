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
import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.JestWrapper;
import ca.ualbert.cs.tasko.data.NoInternetException;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * A class that implements the interface PutCommand. When a PutCommand is executed, it will
 * attempt to store the a given bid into our database.
 *
 * @see PutCommand
 * @author tlafranc
 */
public class PutBidCommand implements PutCommand {
    private Bid bid;

    /**
     * Constructor for the PutBidCommand. Requires the parameter bid in order to be
     * initialized.
     *
     * @param bid the bid object to be stored in the database
     */
    public PutBidCommand(Bid bid) {
        this.bid = bid;
    }

    /**
     * execute is a function that once called, will try to store the given bid object into our
     * database
     */
    @Override
    public void execute(){
        try {
            DataManager dm = DataManager.getInstance();
            Task task = dm.getTask(bid.getTaskID());
            if (task.getMinBid() != null) {
                if (task.getMinBid() > bid.getValue()) {
                    task.setMinBid(bid.getValue());
                    dm.putTask(task);
                }
            } else {
                task.setMinBid(bid.getValue());
                dm.putTask(task);
            }
        } catch (NoInternetException e){
            Log.i("Add Bid Command", "Failed to update min Bid in the task due to lost " +
                    "connection");
        }
        AddBidTask addBidTask = new AddBidTask();
        addBidTask.execute(bid);
        try{
            bid.setBidID(addBidTask.get());
        } catch (Exception e){
            Log.i("Error", "Failed to obtain the user ID from the async object");
        }
    }

    @Override
    public void undo() {
        // TODO: Implement delete
    }

    /**
     * A function that returns whether a PutBidCommand is undoable.
     *
     * @return false as a PutBidCommand is not undoable.
     */
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * An extension of the AsyncTask<Bid, void, String> class. This class is utilized to
     * perform the storing of the Bid object into the database.
     */
    public static class AddBidTask extends AsyncTask<Bid, Void, String> {
        /**
         * doInBackground is called immediately when AddBidTask.execute() is called and the
         * execution is done in the background of the program. Stores the bid object in the
         * database.
         *
         * @param bids the bids to be stored in the database
         * @return the bidId of the bid object that was stored in the database
         */
        @Override
        protected String doInBackground(Bid... bids){
            JestWrapper.verifySettings();

            for(Bid bid : bids){
                Index index = new Index.Builder(bid).index(JestWrapper.getIndex()).type("bid")
                        .build();

                try{
                    DocumentResult result = JestWrapper.getClient().execute(index);
                    if(result.isSucceeded()){
                        return result.getId();
                    }
                } catch (IOException e){
                    Log.i("Error", "The application failed to build and send the bids");
                }
            }
            return null;
        }
    }
}
