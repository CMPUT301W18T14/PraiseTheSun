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
import android.widget.TextView;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * Created by ryandromano
 * Testing class for the activity ViewTaskDetailsActivity
 *
 * @see ViewTaskDetailsActivity
 * @author ryandromano
 */
public class ViewTaskDetailsTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private User newUser;
    private Task task;
    private User dmUser;

    public ViewTaskDetailsTest(){
        super(ViewTaskDetailsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        newUser = new User("rye-guy", "Ryan", "780-780-7800", "rye-guy@hotmail.com");
        //dm.putUser(newUser, getActivity().getApplicationContext());
        dmUser = dm.getUserByUsername("rye-guy", getActivity().getApplicationContext());
        CurrentUser.getInstance().setCurrentUser(dmUser);
        task = new Task(dmUser.getId(), "Test Task", "Help me test this project");
        dm.putTask(task, getActivity().getApplicationContext());
        dm.getTask(task.getId(), getActivity().getApplicationContext());
    }

    /**
     * Tests the ViewBids button
     *
     * @throws NoInternetException
     */
    public void testViewBids() throws NoInternetException {
        solo.assertCurrentActivity("Wrong Activity", ViewTaskDetailsActivity.class);
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);
    }

    /*TODO: Test Case after editTask is implemented*/
    //public void testEditTask() throws NoInternetException {
    //
    //}

    /*TODO: Test Case after deleteTask is implemented*/
    //public void testDelete() throws NoInternetException {
    //
    //}

}

