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

package ca.ualbert.cs.tasko.Commands;

/**
 * Represents a command object with a basic execute and undo functionality.
 *
 * @author Chase Buhler
 */
public abstract class Command<E> {
    protected String type;
    E arg;
    public Command(E arg, String type){
        this.arg = arg;
        this.type = type;
    }
    public abstract void execute();
    public abstract void undo();
    public abstract boolean canUndo();
}
