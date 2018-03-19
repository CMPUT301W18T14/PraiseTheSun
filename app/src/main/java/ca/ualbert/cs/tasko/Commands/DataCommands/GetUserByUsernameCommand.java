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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * An extension of the GetCommand class. When a GetUserByUsername Command is
 * executed, it will attempt to query our database using Elasticsearch in
 * order to retrieve the user associated with the given Username.
 *
 * @author Chase Buhler
 * @see GetCommand
 */
public class GetUserByUsernameCommand extends GetCommand<User> {
    private String username;

    /**
     * The constructor for the GetUserByUsernameCommand. Receives a username
     *
     * @param username username of the user that should be retrieved.
     */
    public GetUserByUsernameCommand(String username){
        this.username = username;
    }

    /**
     * execute is a function that once called, will try to query our
     * database using Elasticsearch in order to retrieve the user associated
     * with username then it will set this commands result to be that user.
     */
    @Override
    public void execute() {
        String query = "{\"query\":{\"term\":{\"username\":\"" + username + "\" } } }";
        GetUserByUsernameTask getUserTask = new GetUserByUsernameTask();
        getUserTask.execute(query);
        try{
            User user = getUserTask.get();
            setResult(user);
        } catch (Exception e){
            Log.i("Error", "Failed to get the user by username from the async object");
        }
    }

    /**
     * An extension of AsyncTask. This class builds a query and executes it
     * on a separate thread to retrieve a User from elastic search.
     */
    private static class GetUserByUsernameTask extends AsyncTask<String, Void, User> {

        /**
         * doInBackground is the main part of the AsyncTask running on the
         * separate thread. It will build a query, execute the query and get
         * the results.
         *
         * @param usernames the username that is associated with the user
         *                  that is to be retrieved.
         * @return The found user or an empty user if no user is found.
         */
        @Override
        protected User doInBackground(String... usernames){
            User user = new User();
            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(usernames[0]).addIndex(JestWrapper.getIndex())
                    .addType("user").build();
            try{
                SearchResult sr = JestWrapper.getClient().execute(search);
                if(sr.isSucceeded() && sr.getTotal() > 0){
                    /*
                    Example used from https://www.programcreek.com/java-api-examples/?api=io.searchbox.core.SearchResult
                    Specifically example 5
                    Retrieved March 18, 2018
                     */
                    JsonArray results = sr.getJsonObject().get("hits").getAsJsonObject().get
                            ("hits").getAsJsonArray();
                    SearchResult.Hit hit = sr.getFirstHit(User.class);
                    user = (User)hit.source;
                    String id = results.get(0).getAsJsonObject().get("_id").getAsString();
                    user.setId(id);
                    return user;

                }

            } catch (IOException e){
                Log.i("Error", e.getMessage());
            }

            return user;
        }
    }
}