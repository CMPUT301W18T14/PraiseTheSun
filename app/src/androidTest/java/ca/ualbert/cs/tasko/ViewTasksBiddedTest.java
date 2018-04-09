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

/**
 * Tests the functionality of the View My Tasks I have bidded on activity
 * @see ViewTasksIBiddedOnActivity
 *
 * @author spack
 *
 */
public class ViewTasksBiddedTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private Task task1;
    private Bid bid;
    private User dmuser;

    public ViewTasksBiddedTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

        dmuser = MockDataManager.getInstance().getUser();

        try {
            dm.putUser(dmuser);
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dmuser = dm.getUserByUsername(dmuser.getUsername());
        CurrentUser.getInstance().setCurrentUser(dmuser);

        task1= new Task(dmuser.getId(), "TestTask", "Help me test code");
        dm.putTask(task1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bid = new Bid(dmuser.getId(), 11, task1.getId());
        dm.addBid(bid);
    }

    /**
     * Ensures the onClick in viewholder is accurate, directs the user to the proper activity which
     * in this case is ViewSerchedTaskDetails.
     */
    public void testClick(){
        swipeToRight();
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.setNavigationDrawer(Solo.OPENED);
        solo.clickOnText("My bids");
        solo.assertCurrentActivity("wrong activity", ViewTasksIBiddedOnActivity.class);
        solo.clickInRecyclerView(0);
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
