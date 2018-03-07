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

package ca.ualbert.cs.tasko.data;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import ca.ualbert.cs.tasko.User;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by chase on 3/4/2018.
 */
public class ElasticSearchUserController {

    /*
    Create a task that will add a user to our elastic search database
     */
    public static class AddUserTask extends AsyncTask<User, Void, String> {


        @Override
        protected String doInBackground(User... users){
            JestWrapper.verifySettings();

            for(User user : users){
                Index index = new Index.Builder(user).index(JestWrapper.getIndex()).type("user")
                        .build();

                try{
                    DocumentResult result = JestWrapper.getClient().execute(index);
                    if(result.isSucceeded()){
                        return result.getId();
                    }
                } catch (IOException e){
                    Log.i("Error", "The application failed to build and send the users");
                }
            }
            return null;
        }
    }

    /**
     * This Task will request to find a user by the JestID provided and will return the user as a
     * User Object if it is found otherwise it will return null
     */
    public static class GetUserByIdTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... ids){
            User user;
            JestWrapper.verifySettings();

            //Build the get query
            Get get = new Get.Builder(JestWrapper.getIndex(), ids[0]).build();

            //Get the results
            try{
                DocumentResult result = JestWrapper.getClient().execute(get);
                if(result.isSucceeded()){
                    user = result.getSourceAsObject(User.class);
                    return user;
                }

            } catch (IOException e){
                Log.i("Error", "Failed to get user by ID from ElasticSearch");
            }

            return null;

        }
    }

    public static class GetUserByUsernameTask extends AsyncTask<String, Void, User> {

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
