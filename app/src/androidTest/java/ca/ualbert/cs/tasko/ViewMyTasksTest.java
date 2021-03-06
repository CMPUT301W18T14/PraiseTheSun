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

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.MockDataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by ryand on 2018-03-19.
 * Testing class for the activity ViewMyTasksActivity
 *
 * @see ViewTaskDetailsActivity
 * @author ryandromano
 */

public class ViewMyTasksTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private User newUser;
    private Task task;
    private User dmUser;

    public ViewMyTasksTest(){
        super(ViewMyTasksActivity.class);
    }

    @Override
    public void setUp() throws Exception {
//       solo = new Solo(getInstrumentation(), getActivity());
//       dm.init(getActivity().getApplicationContext());
//        dmUser = MockDataManager.getInstance().getUser();
//        CurrentUser.getInstance().setCurrentUser(dmUser);
//        task = new Task(dmUser.getId(), "Test Task", "Help me test this project");
//        dm.putTask(task);
    }

    /**
     * Tests to see if the spinner option selected displays the proper values based on the
     * tasks status
     * @throws NoInternetException
     */
    public void testSpinner() throws NoInternetException {
//        TaskList myTasks = new TaskList();
//        myTasks.addTask(task);

//        solo.assertCurrentActivity("Wrong Activity", ViewMyTasksActivity.class);
//        assertTrue(myTasks.getSize() > 1);
//        solo.clickOnView(solo.getView(R.id.filter_spinner));
//        solo.clickOnButton("Bidded");
//        assertEquals(myTasks.getSize(), 0);
//        solo.assertCurrentActivity("Wrong Activity", ViewTaskDetailsActivity.class);

    }

    /**
     * Ensures that the recyclerview goes to the appropriate activity when clicked.
     * @throws NoInternetException
     */
    public void testRecyclerViewOnClick() throws NoInternetException {
//        solo.assertCurrentActivity("Wrong Activity", ViewMyTasksActivity.class);
//        solo.clickInRecyclerView(0);
//        solo.assertCurrentActivity("Wrong Activity", ViewTaskDetailsActivity.class);
    }

}