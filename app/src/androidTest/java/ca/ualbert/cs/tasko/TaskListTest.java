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

import java.util.ArrayList;

/**
 * Created by Thomas on 2018-02-23.
 */

public class TaskListTest extends ActivityInstrumentationTestCase2 {
    public TaskListTest(){
        super(MainActivity.class);
    }

    public void testAddTask(){
        TaskList tasks = new TaskList();
        String username = "tlafranc";
        String name = "Thomas";
        String number = "780-444-4444";
        String email = "tlafranc@ualberta.ca";
        User user = new User(username, name, number, email);

        String taskName = "task1";
        String description = "This is a test task";
        Task task = new Task(user, taskName, description);
        tasks.addTask(task);
        assertTrue(tasks.getTasks().contains(task));
    }

    public void testDeleteTask(){
        TaskList tasks = new TaskList();
        String username = "tlafranc";
        String name = "Thomas";
        String number = "780-444-4444";
        String email = "tlafranc@ualberta.ca";
        User user = new User(username, name, number, email);

        String taskName = "task1";
        String description = "This is a test task";
        Task task = new Task(user, taskName, description);
        tasks.addTask(task);
        tasks.removeTask(task);
        assertFalse(tasks.getTasks().contains(task));
    }

    public void testGetTasks(){
        TaskList tasks = new TaskList();
        String username = "tlafranc";
        String name = "Thomas";
        String number = "780-444-4444";
        String email = "tlafranc@ualberta.ca";
        User user = new User(username, name, number, email);

        String taskName = "task1";
        String description = "This is test task 1";
        Task task = new Task(user, taskName, description);
        tasks.addTask(task);

        taskName = "task2";
        description = "This is test task 2";
        Task task2 = new Task(user, taskName, description);
        tasks.addTask(task2);

        ArrayList<Task> returnedTasks = tasks.getTasks();
        assertEquals(returnedTasks.get(0), task);
        assertEquals(returnedTasks.get(0), task2);
    }
}