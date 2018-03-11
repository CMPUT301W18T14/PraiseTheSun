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
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by Thomas on 2018-03-07.
 */

public class PutBidCommand implements PutCommand {
    private Bid bid;

    public PutBidCommand(Bid bid) {
        this.bid = bid;
    }

    @Override
    public void execute(){
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

    @Override
    public boolean canUndo() {
        return false;
    }

    public static class AddBidTask extends AsyncTask<Bid, Void, String> {
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
