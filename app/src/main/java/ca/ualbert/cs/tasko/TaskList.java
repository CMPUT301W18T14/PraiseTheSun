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
 * Represents a TaskList object that contains an array of tasks. Includes the ability to
 * add/ remove tasks as well as getting tasks at specific indices or returning the entire
 * ArrayList.
 * @see Task
 *
 * @author tlafranc
 */
public class TaskList {

    /**
     * Initializes The TaskList as an ArrayList of Task objects.
     */
    private ArrayList<Task> tasks = new ArrayList<Task>();

    /**
     * Default Constructor.
     */
    public TaskList(){}

    /**
     * Adds a Task to the TaskList.
     * @param task a Task object.
     */
    public void addTask(Task task){
        tasks.add(task);
    }

    /**
     * Removes a specific Task from the TaskList
     * @param task the Task that is requested to be removed.
     */
    public void removeTask(Task task){
        tasks.remove(task);
    }

    /**
     * Get all the Tasks from a TaskList.
     * @return An ArrayList containing all Task objects stored in the TaskList.
     */
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    /**
     * Utilizes the Collections.addAll() method to add multiple tasks that are all contained
     * inside a collection to this taskList
     *
     * @param c a collection of tasks
     */
    public void addAll(Collection<? extends Task> c){
        tasks.addAll(c);
    }

    /**
     * Returns the size of the TaskList (How many tasks it contains) Needed for RecyclerView Adapter
     * @return An integer representing the size of the TaskList
     */
    public int getSize(){
        return tasks.size();
    }

    /**
     * Returns the Task found at a specific position in the TaskList.
     * @param position The interger position of the Task we wish to return.
     * @return a Task object.
     */
    public Task get(int position){
        return tasks.get(position);
    }

}
