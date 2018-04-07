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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.BidList;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * An extension of the GetCommand class. When a GetUserBidsCommand is executed, it will
 * attempt to query our database using Elasticsearch in order to retrieve all bids associated
 * with a given userId.
 *
 * @see GetCommand
 * @author tlafranc
 */
public class GetUserBidsCommand extends GetCommand<BidList, String> {
    private String userId;

    /**
     * Constructor for the GetTaskBidsCommand. Requires the parameter userId in order to be
     * initialized.
     *
     * @param userId the userId associated to the bids in the returned BidList
     */
    public GetUserBidsCommand(String userId){
        super(userId, "GetUserBidsCommand");
        this.userId = userId;
    }

    /**
     * execute is a function that once called, will try to query to query our database using
     * Elasticsearch in order to retrieve all bids associated with userId.
     */
    @Override
    public void execute() {
        String query = "{\"size\": 1000, \"query\":{\"term\":{\"UserID\":\"" + userId + "\" } } }";
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
     * perform the searching and retrieval of a database using Elasticsearch.
     */
    public static class GetBidListTask extends AsyncTask<String, Void, BidList> {
        /**
         * doInBackground is called immediately when GetBidListTask.execute() is called and the
         * execution is done in the background of the program. Builds a query, searches for
         * matches to this query, and returns all matches in the form a BidList.
         *
         * @param userIds the userIds associated to the bids that are supposed to be retrieved from
         *                the database
         * @return a BidList associated to the userId given as a parameter
         */
        @Override
        protected BidList doInBackground(String... userIds){
            BidList bidList = new BidList();

            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(userIds[0]).addIndex(JestWrapper.getIndex())
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