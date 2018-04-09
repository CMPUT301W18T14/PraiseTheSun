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
import android.view.Display;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.MockDataManager;
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
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        dm.init(getActivity().getApplicationContext());
        dmUser = MockDataManager.getInstance().getUser();
        CurrentUser.getInstance().setCurrentUser(dmUser);
        task = new Task(dmUser.getId(), "Test Task", "Help me test this project");
        dm.putTask(task);
        dm.getTask(task.getId());
    }

    /**
     * Tests the ViewBids button
     *
     * @throws NoInternetException
     */
    public void testViewBids() throws NoInternetException {
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        swipeToRight();
        solo.setNavigationDrawer(Solo.OPENED);
        solo.clickOnText("My tasks");
        solo.assertCurrentActivity("wrong activity", ViewMyTasksActivity.class);
        solo.clickInRecyclerView(1);
        solo.assertCurrentActivity("Wrong Activity", ViewTaskDetailsActivity.class);
        solo.clickOnButton("View Bids");
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);
    }

    private void swipeToRight() {
        // Refer: https://stackoverflow.com/questions/26118480/how-to-open-navigation-drawer-menu-in-robotium-automation-script-in-android/29645959
        // Viewed on: March 18, 2018
        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        float xStart = 0 ;
        float xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 1);
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

