/*
 * DataCommandManager
 *
 * March 15, 2018
 *
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

package ca.ualbert.cs.tasko.data;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import ca.ualbert.cs.tasko.Commands.Command;

/**
 * DataCommandManager is a commandManager for the dataCommands. It is a
 * singleton pattern and  will store a history of executed commands that if
 * requested may be undone.
 *
 * @author Chase Buhler
 * @version 1
 * @see ca.ualbert.cs.tasko.Commands.DataCommands
 */
public class DataCommandManager {
    private LinkedList<Command> historyList;
    private static DataCommandManager instance = new DataCommandManager();

    /**
     * Create a new DataCommandManager with an empty history
     */
    private DataCommandManager(){
        historyList = new LinkedList<>();
    }

    /**
     * return the instance of the DataCommandManager
     * @return instance of the command manager
     */
    public static DataCommandManager getInstance(){
        return instance;
    }

    /**
     * Invoke the command and if undoable add to the history
     * @param command command to be executed
     */
    public void invokeCommand(Command command){
        command.execute();

        if(command.canUndo()){
            historyList.add(command);
        } else {
            historyList.clear();
        }
    }
}
