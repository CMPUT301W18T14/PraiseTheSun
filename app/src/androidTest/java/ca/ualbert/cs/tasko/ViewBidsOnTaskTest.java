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
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by op49o_000 on 2018-03-17.
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
        dmuser = dm.getUserByUsername("rromano", getActivity().getApplicationContext());
        task1 = new Task("testidA", "TestTaskA", "Help me test");
        dm.putTask(task1, getActivity().getApplicationContext());
        bid1 = new Bid(dmuser.getId(), 10, task1.getId());
        bid2 = new Bid(dmuser.getId(), 10, task1.getId());
        dm.addBid(bid1, getActivity().getApplicationContext());
        dm.addBid(bid2, getActivity().getApplicationContext());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testPlacingBids() throws NoInternetException {
        TaskList biddedTasks = new TaskList();
            try {
                biddedTasks.addTask(dm.getTask(task1.getId(),getActivity().getApplicationContext()));
            } catch (NoInternetException e) {
                e.printStackTrace();
            }
        assertFalse(biddedTasks.getSize() == 0);
    }

    public void testGetBidsOnTask() throws NoInternetException {
        BidList bids = new BidList();
        Task tasktest = dm.getTask(task1.getId(),getActivity().getApplicationContext());

            try {
                bids = dm.getTaskBids(tasktest.getId(), getActivity().getApplicationContext());
            } catch (NullPointerException e) {
                Log.i("Error", "Failed to get bid list properly");
            } catch (NoInternetException e) {
                e.printStackTrace();
            }
        assertFalse(bids.getSize() == 0);
        //assertEquals("Not correct bids", bids.get(0), bid1 );
    }
}
