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

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * An extension of the GetCommand class. When a GetUserTasksCommand is
 * executed, it will attempt to query our database using Elasticsearch in
 * order to retrieve all tasks associated with the given UserId.
 *
 * @author Chase Buhler
 * @see GetCommand
 */
public class GetUserTasksCommand extends GetCommand<TaskList> {
    private String userID;

    /**
     * GetUserTasksCommand Constructor. Requires a userid to be initialized
     *
     * @param userID userid of the user who's tasks should be retrieved.
     */
    public GetUserTasksCommand(String userID){
        this.userID = userID;
    }

    /**
     * execute is a function that once called, will try to query our
     * database using Elasticsearch in order to retrieve the tasks associated
     * with userID then it will set this commands result to be those tasks.
     */
    @Override
    public void execute() {
        String query = "{\"size\": 1000, \"query\":{\"term\":{\"taskRequesterID\":\"" + userID +
                "\" } } }";
        GetUserTasksTask getUserTasks = new GetUserTasksTask();
        getUserTasks.execute(query);
        try {
            TaskList tasks = getUserTasks.get();
            setResult(tasks);
        } catch (Exception e){
            Log.i("Error", "Failed to get user tasks from the async object");
        }
    }

    /**
     * An extension of AsyncTask. This class builds a query and executes it
     * on a separate thread to retrieve a Task from elastic search.
     */
    private static class GetUserTasksTask extends AsyncTask<String, Void, TaskList> {

        /**
         * doInBackground is the main part of the AsyncTask running on the
         * separate thread. It will build a query, execute the query and get
         * the results.
         *
         * @param userIDs the userid that is associated with the tasks that are
         *               to be retrieved.
         * @return A TaskList with the found Tasks or an empty TaskList if no
         * tasks are found.
         */
        @Override
        protected TaskList doInBackground(String... userIDs){
            TaskList tasks = new TaskList();

            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(userIDs[0]).addIndex(JestWrapper.getIndex())
                    .addType("task").build();

            try{
                SearchResult result = JestWrapper.getClient().execute(search);
                if(result.isSucceeded()){
                    List<Task> results = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(results);
                }
                else {
                    Log.i("Error", "Search result did not succeed");
                }
            } catch (Exception e){
                Log.i("Error", "Search result threw an exception");
            }

            return tasks;
        }
    }
}
