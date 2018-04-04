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
import java.util.List;

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * An extension of the GetCommand class. When a SearchTasksCommand is executed,
 * it will attempt to query our database using Elasticsearch in order to
 * retrieve all tasks who's description contains all terms in the searchTerm
 * string
 *
 * @see GetCommand
 * @author Chase Buhler
 */
public class SearchTasksCommand extends GetCommand<TaskList, String> {
    private String searchTerm;

    /**
     * Constructor for the SearchTasksCommand. Requires the parameter searchTerm
     * in order to be initialized.
     *
     * @param searchTerm the String containing the terms that will be used to
     *                  search the description of the tasks
     */
    public SearchTasksCommand(String searchTerm){
        super(searchTerm, "SearchTasksCommand");
        this.searchTerm = searchTerm;
    }

    /**
     * execute is a function that once called, will try to query to query our
     * database using Elasticsearch in order to retrieve all tasks containing
     * the terms in searchTerm.
     */
    @Override
    public void execute() {
        String query = "{\"size\": 1000," +
                "\"query\" : { " +
                "      \"bool\": { " +
                "           \"should\" :[ " +
                "               {\"match\": {" +
                "                   \"description\": { " +
                "                       \"query\" : \"" + searchTerm + "\"," +
                "                       \"operator\" : \"and\" " +
                "                       }" +
                "                   }" +
                "               }," +
                "               {\"match\": {" +
                "                   \"taskName\": {" +
                "                       \"query\" : \"" + searchTerm + "\"," +
                "                       \"operator\" : \"and\" " +
                "                       }" +
                "                   }" +
                "               }" +
                "           ]," +
                "           \"minimum_should_match\" : 1" +
                "       }" +
                "   }" +
                "}";
        SearchTasks searchTask = new SearchTasks();
        searchTask.execute(query);
        try{
            TaskList tasks = searchTask.get();
            setResult(tasks);
        } catch (Exception e){
            Log.i("Error", "Failed to get the user by username from the async object");
        }
    }

    /**
     * An extension of the AsyncTask class. This class is utilized to
     * perform the searching and retrieval of a database using Elasticsearch.
     */
    private static class SearchTasks extends AsyncTask<String, Void, TaskList> {

        /**
         * doInBackground is the main part of the AsyncTask running on the
         * separate thread. It will build a query, execute the query and get
         * the results.
         *
         * @param searchTerms the string containing the searchTerms that are
         *                    to be used to query the description of the tasks
         * @return A TaskList with all tasks who's descriptions match all the
         * terms in the search term string. Or an empty TaskList if there are
         * no such tasks
         */
        @Override
        protected TaskList doInBackground(String... searchTerms){
            TaskList tasks = new TaskList();
            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(searchTerms[0]).addIndex(JestWrapper.getIndex())
                    .addType("task").build();
            try{
                SearchResult sr = JestWrapper.getClient().execute(search);
                if(sr.isSucceeded() && sr.getTotal() > 0){
                    List<Task> results = sr.getSourceAsObjectList(Task.class);
                    tasks.addAll(results);
                }

            } catch (IOException e){
                Log.i("Error", e.getMessage());
            }

            return tasks;
        }
    }
}
