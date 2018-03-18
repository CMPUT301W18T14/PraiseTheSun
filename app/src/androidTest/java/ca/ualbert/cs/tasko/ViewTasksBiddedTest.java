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
import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * The tests will return a null pointer error if a use ris not hardcoded into the ViewBiddedTask
 * Activity, this is because the currentusersingelton is not set when I directly start the activity
 * Will work in the normal workflow. So if test are failing comment out the cu.getuser() and
 * hard code in a user i.e rromano
 */
public class ViewTasksBiddedTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private Task task1;
    private Task task2;
    private User user;
    private Bid bid1;
    private Bid bid2;
    private User dmuser;

    public ViewTasksBiddedTest() {
        super(ViewTasksBiddedOnActivity.class);
    }

    // Fully loaded setup, need to make a user, multiple task and multiple bids and put everything
    // In the database.
    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        //dm.putUser(user, getActivity().getApplicationContext());
        dmuser = dm.getUserByUsername("rromano", getActivity().getApplicationContext());
        task1 = new Task("test", "TestTask2", "Help me test code");
        task2 = new Task("test", "TestTask3", "Help me test code");
        dm.putTask(task1, getActivity().getApplicationContext());
        dm.putTask(task2, getActivity().getApplicationContext());
        bid1 = new Bid(dmuser.getId(), 10, task1.getId());
        bid2 = new Bid(dmuser.getId(), 10, task2.getId());
        dm.addBid(bid1, getActivity().getApplicationContext());
        dm.addBid(bid2, getActivity().getApplicationContext());

    }

    /**
     * Tests the process that adds Tasks to a tasklist by using a users posted bids.
     * @throws NoInternetException
     */
    public void testPlacingBids() throws NoInternetException {
        BidList userBids = dm.getUserBids(dmuser.getId(), getActivity().getApplicationContext());
        TaskList biddedTasks = new TaskList();
        for (int i = 0; i < userBids.getSize(); i++)
            try {
                biddedTasks.addTask(dm.getTask(userBids.get(i).getTaskID(), getActivity().getApplicationContext()));
            } catch (NoInternetException e) {
                e.printStackTrace();
            }
        assertFalse(biddedTasks.getSize() == 0);

    }

    /**
     * Ensures the onClick in viewholder is accurate, directs the user to the proper activity.
     */
    public void testClick(){
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Click did not register", ViewSearchedTaskDetailsActivity.class);
    }

}
