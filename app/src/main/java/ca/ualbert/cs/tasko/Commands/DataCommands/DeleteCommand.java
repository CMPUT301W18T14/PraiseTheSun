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

import ca.ualbert.cs.tasko.Commands.Command;

/**
 * Represents a command that is a delete command. Delete commands can be undone by re adding the
 * deleted item to the elastic search
 *
 * @author Chase Buhler
 * @see Command
 */
public abstract class DeleteCommand<E> extends Command<E> {
    public DeleteCommand(E arg, String type) {
        super(arg, type);
    }

    public boolean canUndo(){
        return true;
    }
}
