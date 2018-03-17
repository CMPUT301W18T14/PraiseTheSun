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

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;

/**
 * An extension of the GetCommand class. When a GetTaskCommand is executed, it
 * will attempt to query our database using Elasticsearch in order to retrieve
 * the task associated with the given TaskId.
 *
 * @author Chase Buhler
 * @see GetCommand
 */
public class GetTaskCommand extends GetCommand<Task> {
    private String taskid;

    /**
     * GetTaskCommand Constructor. Requires a taskid to be initialized
     *
     * @param taskid taskid of the task to be searched for
     */
    public GetTaskCommand(String taskid){
        this.taskid = taskid;
    }

    /**
     * execute is a function that once called, will try to query our
     * database using Elasticsearch in order to retrieve the task associated
     * with taskId then it will set this commands result to be that task.
     */
    @Override
    public void execute() {
        GetTask getTask = new GetTask();
        getTask.execute(taskid);

        try {
            Task task = getTask.get();
            setResult(task);
        } catch (Exception e){
            Log.i("Error", "Failed to get user from the async object");
        }
    }

    /**
     * An extension of AsyncTask. This class builds a query and executes it
     * on a separate thread to retrieve a Task from elastic search.
     */
    private static class GetTask extends AsyncTask<String, Void, Task> {

        /**
         * doInBackground is the main part of the AsyncTask running on the
         * separate thread. It will build a query, execute the query and get
         * the results.
         *
         * @param ids the taskid that is associated with the task that is to
         *            be retrieved.
         * @return The found task or null if no task is found.
         */
        @Override
        protected Task doInBackground(String... ids) {
            Task task = null;
            JestWrapper.verifySettings();

            //Build the get query
            Get get = new Get.Builder(JestWrapper.getIndex(), ids[0]).build();

            //Get the results
            try{
                DocumentResult result = JestWrapper.getClient().execute(get);
                if(result.isSucceeded()){
                    task = result.getSourceAsObject(Task.class);
                    return task;
                }

            } catch (IOException e){
                Log.i("Error", "Failed to get user by ID from ElasticSearch");
            }

            return task;
        }
    }
}
