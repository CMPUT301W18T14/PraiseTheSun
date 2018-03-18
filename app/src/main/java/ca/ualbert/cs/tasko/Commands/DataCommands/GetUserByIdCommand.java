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

import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;

/**
 * An extension of the GetCommand class. When a GetUserByIdCommand is executed,
 * it will attempt to query our database using Elasticsearch in order to
 * retrieve the user associated with the given UserId.
 *
 * @author Chase Buhler
 * @see GetCommand
 */
public class GetUserByIdCommand extends GetCommand<User> {
    private String id;

    /**
     * The constructor for the GetUSerByIdCommand. Receives a userID
     * @param id userId of the user that should be retrieved.
     */
    public GetUserByIdCommand(String id){
        this.id = id;
    }

    /**
     * execute is a function that once called, will try to query our
     * database using Elasticsearch in order to retrieve the user associated
     * with id then it will set this commands result to be that user.
     */
    @Override
    public void execute() {
        GetUserByIdTask getUserTask = new GetUserByIdTask();
        getUserTask.execute(id);

        try {
            User user = getUserTask.get();
            setResult(user);
        } catch (Exception e){
            Log.i("Error", "Failed to get user from the async object");
        }
    }

    /**
     * An extension of AsyncTask. This class builds a query and executes it
     * on a separate thread to retrieve a User from elastic search.
     */
    private static class GetUserByIdTask extends AsyncTask<String, Void, User> {

        /**
         * doInBackground is the main part of the AsyncTask running on the
         * separate thread. It will build a query, execute the query and get
         * the results.
         *
         * @param ids the id that is associated with the user that is to
         *            be retrieved.
         * @return The found user or an empty user if no user is found.
         */
        @Override
        protected User doInBackground(String... ids) {
            User user = new User();
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

            return user;
        }
    }
}
