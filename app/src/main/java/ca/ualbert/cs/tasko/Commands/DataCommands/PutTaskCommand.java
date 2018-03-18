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

import java.security.spec.ECField;

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * An extension of PutCommand, PutTaskCommand will take a task and attempt to
 * add it to our elastic search database.
 *
 * @author Chase Buhler
 * @see PutCommand
 * @see ca.ualbert.cs.tasko.Commands.Command
 */
public class PutTaskCommand implements PutCommand {
    Task task;

    /**
     * Constructor for the PutTaskCommand. Requires the parameter task in order
     * to be initialized.
     *
     * @param task the task object to be stored in the database
     */
    public PutTaskCommand(Task task){
        this.task = task;
    }

    /**
     * Once execute is called. The method first builds a put request and then
     * attempts to add task to the elastic search Database.
     */
    @Override
    public void execute() {
        PutTaskTask put = new PutTaskTask();
        put.execute(task);
        try{
            task.setId(put.get());
        } catch (Exception e){
            Log.i("Error", "Things went wrong in putTask AsyncObject");
        }
    }

    //TODO
    @Override
    public void undo() {
        //TODO: Implement delete from database
    }

    /**
     * Return true because we can undo this command by deleting the task that
     * we added.
     * @return true
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    private class PutTaskTask extends AsyncTask<Task, Void, String>{

        @Override
        protected String doInBackground(Task... tasks) {
            JestWrapper.verifySettings();

            Index index = new Index.Builder(tasks[0]).index(JestWrapper.getIndex()).type("task")
                    .build();

            try{
                DocumentResult result = JestWrapper.getClient().execute(index);
                System.out.println(result.getJsonObject());
                if(result.isSucceeded()){
                    return result.getId();
                }
            } catch (Exception e){
                Log.i("Error", "The application failed to build and send the task");
            }

            return null;
        }
    }


}
