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

import android.util.Log;

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by chase on 3/9/2018.
 */

public class PutTaskCommand implements PutCommand {

    Task task;

    public PutTaskCommand(Task task){
        this.task = task;
    }

    @Override
    public void execute() {
        JestWrapper.verifySettings();

        Index index = new Index.Builder(task).index(JestWrapper.getIndex()).type("task")
                .build();

        try{
            DocumentResult result = JestWrapper.getClient().execute(index);
            System.out.println(result.getJsonObject());
            if(result.isSucceeded()){
                task.setId(result.getId());
            }
        } catch (Exception e){
            Log.i("Error", "The application failed to build and send the task");
        }
    }

    @Override
    public void undo() {
        //TODO: Implement delete from database
    }

    @Override
    public boolean canUndo() {
        return true;
    }


}
