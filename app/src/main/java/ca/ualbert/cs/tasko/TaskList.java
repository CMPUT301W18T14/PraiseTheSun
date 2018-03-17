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

package ca.ualbert.cs.tasko;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thomas on 2018-02-23.
 * Represents a TaskList object that contains an array of tasks.
 *
 * @author tlafranc
 */
public class TaskList {

    private ArrayList<Task> tasks = new ArrayList<Task>();

    public TaskList(){}

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeTask(Task task){
        tasks.remove(task);
    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public void addAll(Collection<? extends Task> c){
        tasks.addAll(c);
    }

    public int getSize(){
        return tasks.size();
    }

    public Task get(int position){
        return tasks.get(position);
    }

}
