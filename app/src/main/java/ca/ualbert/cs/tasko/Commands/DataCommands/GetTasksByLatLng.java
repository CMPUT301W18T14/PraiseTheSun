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
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.List;

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Thomas on 2018-04-03.
 */

public class GetTasksByLatLng extends GetCommand<TaskList> {
    private Double lat;
    private Double lng;

    public GetTasksByLatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void execute() {
        /*
         * https://stackoverflow.com/questions/20610999/query-in-elasticsearch-with-multiple-ranges-on-multiple-dates
         * taken on 2018-04-03
         */
        String query = "{\"size\": 1000," +
                "\"query\" : { " +
                "      \"bool\": { " +
                "          \"must\": [ " +
                "              {\"range\": { " +
                "                  \"x\" : { " +
                "                      \"gte\": " + Double.toString(lat - 10000) + ", " +
                "                      \"lte\": " + Double.toString(lat + 10000) +
                "                      }" +
                "                  }" +
                "              }," +
                "              {\"range\": { " +
                "                  \"y\" : { " +
                "                      \"gte\": " + Double.toString(lng - 10000) + ", " +
                "                      \"lte\": " + Double.toString(lng + 10000) +
                "                      }" +
                "                  }" +
                "              }" +
                "           ]" +
                "       }" +
                "   }" +
                "}";

        GetTaskListTask getTaskListTask = new GetTaskListTask();
        getTaskListTask.execute(query);
        try{
            TaskList tasks = getTaskListTask.get();
            setResult(tasks);
        } catch (Exception e){
            Log.i("Error", "Failed to get the Tasks by Lat and Long from the async object");
        }
    }

    private static class GetTaskListTask extends AsyncTask<String, Void, TaskList> {

        @Override
        protected TaskList doInBackground(String... searchTerms) {
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
