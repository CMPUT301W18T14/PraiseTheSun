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

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Testing class for the class TaskList.
 *
 * @see TaskList
 * @author tlafranc
 */

public class TaskListTest extends ActivityInstrumentationTestCase2 {
    public TaskListTest(){
        super(MainActivity.class);
    }

    public void testAddTask(){
        TaskList tasks = new TaskList();

        String taskName = "task1";
        String description = "This is a test task";
        Task task = new Task("bobismyID", taskName, description);
        task.setId("Anewid");
        tasks.addTask(task);
        assertTrue(tasks.getTasks().contains(task));
    }

    public void testDeleteTask(){
        TaskList tasks = new TaskList();

        String taskName = "task1";
        String description = "This is a test task";
        Task task = new Task("bobismyID", taskName, description);
        task.setId("Anewid");
        tasks.addTask(task);
        tasks.removeTask(task);
        assertFalse(tasks.getTasks().contains(task));
    }

}