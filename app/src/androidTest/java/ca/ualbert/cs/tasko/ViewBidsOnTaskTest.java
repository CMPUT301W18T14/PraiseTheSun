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

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by op49o_000 on 2018-03-17.
 */

/**
 * This test will ensure that data on a task can be retrieved successfully from the database
 * namely bidlist on a particular task.
 */
public class ViewBidsOnTaskTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private Task task1;
    private User user;
    private Bid bid1;
    private Bid bid2;
    private User dmuser;

    public ViewBidsOnTaskTest() {
        super(ViewBidsOnTaskActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        dmuser = dm.getUserByUsername("rromano");
        task1 = new Task("testidA", "TestTaskA", "Help me test");
        dm.putTask(task1);
        bid1 = new Bid(dmuser.getId(), 10, task1.getId());
        bid2 = new Bid(dmuser.getId(), 10, task1.getId());
        dm.addBid(bid1);
        dm.addBid(bid2);
    }

    /**
     * Test to start the activity
     *
     * @throws Exception
     */
    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    /**
     * Test to retrieve a task from the database and place it in a tasklist
     *
     * @throws NoInternetException
     */
    public void testGettingTask() throws NoInternetException {
        TaskList biddedTasks = new TaskList();
            try {
                biddedTasks.addTask(dm.getTask(task1.getId()));
            } catch (NoInternetException e) {
                e.printStackTrace();
            }
        assertFalse(biddedTasks.getSize() == 0);
    }

    /**
     * Test to get the bidlist of bids on a particular task
     * @throws NoInternetException
     */
    public void testGetBidsOnTask() throws NoInternetException {
        BidList bids = new BidList();
        Task tasktest = dm.getTask(task1.getId());

            try {
                bids = dm.getTaskBids(tasktest.getId());
            } catch (NullPointerException e) {
                Log.i("Error", "Failed to get bid list properly");
            } catch (NoInternetException e) {
                e.printStackTrace();
            }
        assertFalse(bids.getSize() == 0);
    }
}
