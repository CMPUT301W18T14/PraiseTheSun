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

import java.util.List;

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.BidList;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * An extension of the GetCommand class. When a GetTasksBidsCommand is executed, it will
 * attempt to query our database using Elasticsearch in order to retrieve all bids associated
 * with a given TaskId.
 *
 * @see GetCommand
 * @author tlafranc
 */
public class GetTaskBidsCommand extends GetCommand<BidList> {
    private String taskId;

    /**
     * Constructor for the GetTaskBidsCommand. Requires the parameter taskId in order to be
     * initialized.
     *
     * @param taskId the taskId associated to the bids in the returned BidList
     */
    public GetTaskBidsCommand(String taskId) {
        this.taskId = taskId;
    }

    /**
     * execute is a function that once called, will try to query to query our database using
     * Elasticsearch in order to retrieve all bids associated with taskId.
     */
    @Override
    public void execute() {
        String query = "{\"size\": 1000, \"query\":{\"term\":{\"TaskID\":\"" + taskId + "\" } } }";
        GetBidListTask getBidListTask = new GetBidListTask();
        getBidListTask.execute(query);
        try {
            BidList bidList = getBidListTask.get();
            setResult(bidList);
        } catch (Exception e){
            Log.i("Error", "Failed to get user from the async object");
        }
    }

    public void undo() {
        // TODO: Implement delete
    }

    /**
     * An extension of the AsyncTask<String, void, BidList> class. This class is utilized to
     * perform the searching and retrieval of a database using Elasticsearch for the execute
     * method in GetTaskBidsCommand.
     */
    public static class GetBidListTask extends AsyncTask<String, Void, BidList> {
        /**
         * doInBackground is called immediately when GetBidListTask.execute() is called and the
         * code is done in the background of the program. Builds a query, searches for matches to
         * this query, and returns all matches in the form a BidList.
         *
         * @param taskIds the taskIds that would like to be searched for in the database
         * @return a BidList associated to the taskId given as a parameter
         */
        @Override
        protected BidList doInBackground(String... taskIds){
            BidList bidList = new BidList();

            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(taskIds[0]).addIndex(JestWrapper.getIndex())
                    .addType("bid").build();

            try{
                SearchResult result = JestWrapper.getClient().execute(search);
                if(result.isSucceeded()){
                    List<Bid> foundBids = result.getSourceAsObjectList(Bid.class);
                    bidList.addAll(foundBids);
                }
                else {
                    Log.i("Error", "Search result did not succeed");
                }
            } catch (Exception e){
                Log.i("Error", "Search result threw an exception");
            }

            return bidList;
        }
    }
}
