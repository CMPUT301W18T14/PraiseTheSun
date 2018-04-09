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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.MultiGet;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * GetUserMapCommand takes in a list of userIDs and queries elastic search to get a List
 * of user Objects based on those IDS. Then a Map is created from userID to the User object
 * that has that ID.
 *
 * @author Chase Buhler
 * @see GetCommand;
 */
public class GetUserMapCommand extends GetCommand<Map<String, User>, ArrayList<String>> {
    ArrayList<String> ids;
    public GetUserMapCommand(ArrayList<String> ids){
        super(ids, "GetUserMapCommand");
        this.ids = ids;
    }

    @Override
    public void execute() {
        if(ids.size() < 1){
            setResult(new HashMap<String, User>());
            return;
        }
        String query = "{\"query\": { " +
        "    \"ids\" : { " +
        "        \"values\" : [\""+ ids.get(0) + "\"";
        for(int i = 1; i < ids.size(); i++){
            query += ", \"" + ids.get(i) + "\"";
        }
        query += "] " +
        "    }" +
        "  }" +
        "}";

        Log.i("QUERY", query);
        GetUserMapTask task = new GetUserMapTask();
        task.execute(query);
        try {
            ArrayList<User> results = task.get();
            Map<String, User> userMap = new HashMap<String, User>();

            for(int j = 0; j < results.size(); j++){
                userMap.put(results.get(j).getId(), results.get(j));
            }
            setResult(userMap);
        } catch (Exception e){
            Log.i("Error", "Could not get users in GetUserMap AsyncTask");
        }
    }

    private static class GetUserMapTask extends AsyncTask<String, Void, ArrayList<User>>{

        @Override
        protected ArrayList<User> doInBackground(String... strings) {
            ArrayList<User> results =  new ArrayList<>();
            //Build the search query
            Search search = new Search.Builder(strings[0]).addIndex(JestWrapper.getIndex())
                    .addType("user").build();
            try{
                SearchResult sr = JestWrapper.getClient().execute(search);
                if(sr.isSucceeded() && sr.getTotal() > 0){
                    results.addAll(sr.getSourceAsObjectList(User.class));
                }

            } catch (IOException e){
                Log.i("Error", e.getMessage());
            }
            return results;
        }
    }
}
