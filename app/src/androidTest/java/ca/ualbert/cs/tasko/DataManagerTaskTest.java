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
 * Created by chase on 3/9/2018.
 */

public class DataManagerTaskTest extends ActivityInstrumentationTestCase2 {

    private User requester;
    private Task task1;
    private Task task2;
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
        task1 = new Task(requester.getId(), "Task for lazy people", "Description");
        task2 = new Task(requester.getId(), "Different a task", "Explination");

    }

    public void testPutTask(){
        boolean isConnected = true;
        try {
            dm.putTask(task1, getActivity().getApplicationContext());
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        assertTrue(task1.getId() != null);
        Task retTask = null;
        try {
            retTask = dm.getTask(task1.getId(), getActivity().getApplicationContext());
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        assertNotNull(retTask);
        assertEquals(task1.getId(), retTask.getId());
    }

    public void testSearchTasks(){
        boolean isConnected = true;
        try{
            dm.putTask(task1, getActivity().getApplicationContext());
            dm.putTask(task2, getActivity().getApplicationContext());
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        TaskList results = null;
        try{
            results = dm.searchTasks("Description", getActivity().getApplicationContext());
        } catch (NoInternetException e){
            isConnected = false;
        }
        assertTrue(isConnected);
        assertTrue(!results.getTasks().isEmpty());
        assertTrue(results.getTasks().contains(task1));
        assertFalse(results.getTasks().contains(task2));
    }
}
