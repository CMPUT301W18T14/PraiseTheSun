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

/**
 * Created by imtih on 2018-03-16.
 *
 * @author imtihan
 */

public class RootActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public RootActivityTest(){super(MainActivity.class);}

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());

        //here so the activities requiring a non-null CurrentUser have one
        User testUser = DataManager.getInstance().getUserByUsername("imtihan",
                getActivity().getApplicationContext());
        CurrentUser.getInstance().setCurrentUser(testUser);
    }

    public void testGoToMain(){
        swipeToRight();
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.setNavigationDrawer(Solo.OPENED);
        //solo.clickOnActionBarItem(R.id.get_home);
        solo.clickOnText("Home");
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
    }

    public void testGoToAddNewTask(){
        swipeToRight();
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.setNavigationDrawer(Solo.OPENED);
        //solo.clickOnActionBarItem(R.id.add_new_task);
        solo.clickOnText("Add new task");
        solo.assertCurrentActivity("wrong activity", AddTaskActivity.class);
    }

    public void testGoToMyTasks(){
        swipeToRight();
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.setNavigationDrawer(Solo.OPENED);
        solo.clickOnActionBarItem(R.id.my_tasks);
        solo.clickOnText("My tasks");
        solo.assertCurrentActivity("wrong activity", ViewMyTasksActivity.class);
    }

    public void testGoToViewMyBids(){
        swipeToRight();
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.setNavigationDrawer(Solo.OPENED);
        //solo.clickOnActionBarItem(R.id.my_bids);
        solo.clickOnText("My bids");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        solo.assertCurrentActivity("wrong activity", ViewTasksBiddedOnActivity.class);
    }

    public void testGoToViewUserProfile(){
        swipeToRight();
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.setNavigationDrawer(Solo.OPENED);
        //solo.clickOnActionBarItem(R.id.view_profile);
        solo.clickOnText("View profile");
        solo.assertCurrentActivity("wrong activity", UserProfileActivity.class);
    }

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
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
}
