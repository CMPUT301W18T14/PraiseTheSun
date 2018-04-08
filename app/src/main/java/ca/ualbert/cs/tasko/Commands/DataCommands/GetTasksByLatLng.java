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

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * An extension of the GetCommand class. When a GetTasksByLatLng is executed,
 * it will attempt to query our database using Elasticsearch in order to
 * retrieve all tasks who's location are within 1 degree of both the latitude
 * and longitude of a given latitude and longitude.
 *
 * @see GetCommand
 * @author tlafranc
 */

public class GetTasksByLatLng extends GetCommand<TaskList, LatLng> {
    private Double lat;
    private Double lng;

    /**
     * Constructor for the GetTasksByLatLng. Requires the parameter lat and lng
     * in order to be initialized.
     *
     * @param lat A double representing the latitude of a task
     * @param lng A double representing the longitude of a task
     */
    public GetTasksByLatLng(LatLng latLng) {
        super(latLng, "GetTasksByLatLng");
        this.lat = latLng.latitude;
        this.lng = latLng.longitude;
    }

    /**
     * execute is a function that once called, will try to query our
     * database using Elasticsearch in order to retrieve all tasks within 1 degree
     * of both the latitude and longitude of a given latitude and longitude.
     */
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
                "                  \"lat\" : { " +
                "                      \"gte\": " + Double.toString(lat - 1) + ", " +
                "                      \"lte\": " + Double.toString(lat + 1) +
                "                      }" +
                "                  }" +
                "              }," +
                "              {\"range\": { " +
                "                  \"lng\" : { " +
                "                      \"gte\": " + Double.toString(lng - 1) + ", " +
                "                      \"lte\": " + Double.toString(lng + 1) +
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

    /**
     * An extension of the AsyncTask class. This class is utilized to
     * perform the searching and retrieval of a database using Elasticsearch.
     */
    private static class GetTaskListTask extends AsyncTask<String, Void, TaskList> {

        /**
         * doInBackground is the main part of the AsyncTask running on the
         * separate thread. It will build a query, execute the query and get
         * the results.
         *
         * @param searchTerms the query to be executed
         * @return A TaskList with all tasks within 1 degree
         * of both the latitude and longitude of the latitude and
         * longitude of a given task. Or an empty TaskList if there
         * are no such tasks
         */
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
