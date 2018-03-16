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
 * Class is used to model a get
 */
public class GetTaskCommand extends GetCommand<Task> {

    private String taskid;

    public GetTaskCommand(String taskid){
        this.taskid = taskid;
    }

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

    private static class GetTask extends AsyncTask<String, Void, Task> {

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
