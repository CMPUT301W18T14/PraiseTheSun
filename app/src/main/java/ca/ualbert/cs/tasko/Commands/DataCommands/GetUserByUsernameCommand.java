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

import java.util.ArrayList;

import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.ElasticSearchUserController;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by chase on 3/7/2018.
 */

public class GetUserByUsernameCommand extends GetCommand<User> {

    private String username;

    public GetUserByUsernameCommand(String username){
        this.username = username;
    }

    @Override
    public void execute() {

        String query = "{\"query\":{\"term\":{\"username\":\"" + username + "\" } } }";
        GetUserByUsernameTask getUserTask = new GetUserByUsernameTask();
        getUserTask.execute(query);
        try{
            setResult(getUserTask.get());
        } catch (Exception e){
            Log.i("Error", "Failed to get the user by username from the async object");
        }

    }

    private static class GetUserByUsernameTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... usernames){
            User user = null;
            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(usernames[0]).addIndex(JestWrapper.getIndex())
                    .addType("user").build();
            try{
                SearchResult sr = JestWrapper.getClient().execute(search);
                if(sr.isSucceeded()){
                    user = sr.getSourceAsObject(User.class);
                }

            } catch (Exception e){
                Log.i("Error", "Could not build and execute getUserByUsernameTask");
            }

            return user;
        }
    }
}
