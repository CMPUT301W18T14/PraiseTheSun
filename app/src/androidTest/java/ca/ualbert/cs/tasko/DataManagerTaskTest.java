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

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Testing class for putting, retrieving and deleting tasks from the database
 *
 * @author tlafranc
 * @see ca.ualbert.cs.tasko.Commands.DataCommands.PutTaskCommand
 * @see ca.ualbert.cs.tasko.Commands.DataCommands.GetUserTasksCommand
 * @see ca.ualbert.cs.tasko.Commands.DataCommands.SearchTasksCommand
 * @see ca.ualbert.cs.tasko.Commands.DataCommands.DeleteTaskCommand
 */
public class DataManagerTaskTest extends ActivityInstrumentationTestCase2 {

    private User requester;
    private User requester2;
    private Task task1;
    private Task task2;
    private Task task3;
    private DataManager dm;

    public DataManagerTaskTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp(){
        dm = DataManager.getInstance();
        requester = new User("requester", "Ima Requester", "4567891234", "gimmieRequests@example" +
                ".com");
        requester.setId("MyIDisAwesome");

        requester2 = new User("Requester2", "Ima reqster 2", "2222222222", "Gimmie2@example.com");
        requester2.setId("Number2ftw");

        task1 = new Task(requester.getId(), "Task for lazy people", "Description");
        task2 = new Task(requester.getId(), "Different a task", "Explination");
        task3 = new Task(requester2.getId(), "Number 2 Task", "Description");

    }

    public void testPutGetDeleteTask(){
        boolean isConnected = true;
        try {
            dm.putTask(task1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        assertTrue(task1.getId() != null);
        Task retTask = null;
        try {
            retTask = dm.getTask(task1.getId());
        }catch (NoInternetException e){
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(isConnected);
        assertNotNull(retTask);
        assertEquals(task1.getId(), retTask.getId());
        try{
            dm.deleteTask(task1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retTask = dm.getTask(task1.getId());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        assertNull(retTask);
    }

    public void testSearchTasks(){
        boolean isConnected = true;
        try{
            dm.putTask(task1);
            dm.putTask(task2);
            dm.putTask(task3);
            task3.setStatus(TaskStatus.DONE);
            dm.putTask(task3);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        TaskList results = null;
        try{
            results = dm.searchTasks("Description");
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        assertTrue(!results.getTasks().isEmpty());
        assertTrue(results.getTasks().contains(task1));
        assertFalse(results.getTasks().contains(task2));

        //TODO: Make assert False to test that your own tasks are not returned
        assertTrue(results.getTasks().contains(task3));
    }

    public void testGetUserTasks(){
        boolean isConnected = true;
        try{
            dm.putTask(task1);
            dm.putTask(task3);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        TaskList results = null;
        try{
            results = dm.getUserTasks(task1.getTaskRequesterID());
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        assertTrue(results.getTasks().contains(task1));
        assertFalse(results.getTasks().contains(task3));
    }
}
