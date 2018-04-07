/*
 * GetCommand
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

package ca.ualbert.cs.tasko.Commands.DataCommands;

import ca.ualbert.cs.tasko.Commands.Command;

/**
 * Abstract class representing a command that will return a result from
 * elastic search
 *
 * @author Chase Buhler
 * @version 1
 * @see Command
 */
public abstract class GetCommand<T, E> extends Command<E> {
    private T result;

    public GetCommand(E arg, String type) {
        super(arg, type);
    }

    /**
     * return the result of the command
     * @return the data resulting from the command
     */
    public T getResult(){
        return result;
    }

    /**
     * Used by subclasses to set the result of their commands
     * @param result the Data to be returned as the result
     */
    protected void setResult(T result){
        this.result = result;
    }


    /**
     * Does not make sense to undo anything but also does not make sense to
     * have the ability to undo be false. so we do nothing.
     */
    @Override
    public void undo(){

    }

    /**
     * Return true always because a get command can be undone always
     * @return true
     */
    @Override
    public boolean canUndo(){
        return true;
    }
}
