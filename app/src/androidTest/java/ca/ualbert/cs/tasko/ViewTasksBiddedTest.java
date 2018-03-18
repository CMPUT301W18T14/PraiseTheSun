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

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by spack on 2018-03-17.
 */

public class ViewTasksBiddedTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private CurrentUser cu = CurrentUser.getInstance();
    private Task task1;
    private Task task2;
    private User user;
    private Bid bid1;
    private Bid bid2;

    public ViewTasksBiddedTest() {
        super(ViewTasksBiddedOnActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        dm.putUser(user, getActivity().getApplicationContext());
        User dmuser = dm.getUserByUsername("rromano", getActivity().getApplicationContext());
        task1 = new Task(dmuser.getId(), "TestTask2", "Help me test code");
        task2 = new Task(dmuser.getId(), "TestTask3", "Help me test code");
        dm.putTask(task1, getActivity().getApplicationContext());
        dm.putTask(task2, getActivity().getApplicationContext());
        cu.setCurrentUser(user);
    }

    public void testPlacingBids() throws NoInternetException {
        BidList userBids = dm.getUserBids(user.getId(), getActivity().getApplicationContext());
        TaskList biddedTasks = new TaskList();
        for (int i = 0; i < userBids.getSize(); i++)
            try {
                biddedTasks.addTask(dm.getTask(userBids.get(i).getTaskID(), getActivity().getApplicationContext()));
            } catch (NoInternetException e) {
                e.printStackTrace();
            }
        assertEquals(0, biddedTasks.getSize());

    }

}
