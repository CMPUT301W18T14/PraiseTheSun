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

package ca.ualbert.cs.tasko.data;

import java.util.LinkedList;

import ca.ualbert.cs.tasko.Commands.Command;

/**
 * Created by chase on 3/7/2018.
 */

public class DataCommandManager {
    private LinkedList<Command> historyList;
    private static DataCommandManager instance = new DataCommandManager();

    private DataCommandManager(){
        historyList = new LinkedList<>();
    }

    public static DataCommandManager getInstance(){
        return instance;
    }

    public void invokeCommand(Command command){
        command.execute();

        if(command.canUndo()){
            historyList.add(command);
        } else {
            historyList.clear();
        }
    }
}
