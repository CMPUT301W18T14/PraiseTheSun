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

import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationList;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by chase on 3/27/2018.
 */

public class GetNotificationsCommand extends GetCommand<NotificationList> {
    private String userId;

    /**
     * Constructor for the GetNotificationsCommand. Requires the parameter userId in order to be
     * initialized.
     *
     * @param userId the userId associated to the notifications in the returned NotificationList
     */
    public GetNotificationsCommand(String userId){
        this.userId = userId;
    }

    /**
     * execute is a function that once called, will try to query to query our database using
     * Elasticsearch in order to retrieve all notifications associated with userId.
     */
    @Override
    public void execute() {
        String query = "{\"size\": 1000, \"query\":{\"term\":{\"recipientID\":\"" + userId + "\" } } }";
        GetNotificationsTask gnt = new GetNotificationsTask();
        gnt.execute(query);
        try {
            NotificationList nl = gnt.get();
            setResult(nl);
        } catch (Exception e){
            Log.i("Error", "Failed to get user from the async object");
        }
    }

    public void undo() {
        // TODO: Implement delete
    }

    /**
     * An extension of the AsyncTask<String, void, NotificationList> class. This class is
     * utilized to perform the searching and retrieval of a database using Elasticsearch.
     */
    public static class GetNotificationsTask extends AsyncTask<String, Void, NotificationList> {

        @Override
        protected NotificationList doInBackground(String... userIds){
            NotificationList nl = new NotificationList();

            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(userIds[0]).addIndex(JestWrapper.getIndex())
                    .addType("notification").build();

            try{
                SearchResult result = JestWrapper.getClient().execute(search);
                if(result.isSucceeded()){
                    List<Notification> foundNotifications =
                            result.getSourceAsObjectList(Notification.class);
                    nl.addAll(foundNotifications);
                }
                else {
                    Log.i("Error", "Search result did not succeed");
                }
            } catch (Exception e){
                Log.i("Error", "Search result threw an exception");
            }

            return nl;
        }
    }
}
