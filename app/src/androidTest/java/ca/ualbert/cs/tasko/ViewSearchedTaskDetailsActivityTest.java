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

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.MockDataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

//TODO needs revision

/**
 * Testing class for the activity ViewSearchedTaskDetailsActivity
 *
 * @see ViewSearchedTaskDetailsActivity
 * @author Chase Buhler
 */
public class ViewSearchedTaskDetailsActivityTest extends ActivityInstrumentationTestCase2 {

    private EditText bidAmount;
    private Solo solo;
    private DataManager dm = DataManager.getInstance();

    public ViewSearchedTaskDetailsActivityTest(){
        super(ViewSearchedTaskDetailsActivity.class);
    }

    @Override
    public void setUp() throws NoInternetException, InterruptedException {

//        Task task = new Task("sjkdhkja","Task Name 1", "Description for test task. Has " +
//                "status bidded. This task is not real and will never ever be used.");
//
//        Intent i = new Intent(getActivity().getApplicationContext(), ViewSearchedTaskDetailsActivity.class);
//        i.putExtra("TaskID", task.getId());
//        setActivityIntent(i);
//
//        solo = new Solo(getInstrumentation(), getActivity());
//
//        DataManager.getInstance().init(getActivity().getApplicationContext());
//        User taskRequester = MockDataManager.getInstance().getTaskRequester();
//        CurrentUser.getInstance().setCurrentUser(taskRequester);

    }

    public void testPlaceValidBid(){
//        solo.assertCurrentActivity("Wrong Activity", ViewSearchedTaskDetailsActivity.class);
//        solo.clickOnButton("Place Bid");
//        bidAmount = (EditText) solo.getView(R.id.bidAmount);
//        solo.enterText(bidAmount, "4.12");
//        solo.clickOnText("Confirm");
//        solo.clickOnText("Yes");
    }

    public void testPlaceEmptyBid(){
//        solo.assertCurrentActivity("Wrong Activity", ViewSearchedTaskDetailsActivity.class);
//        solo.clickOnButton("Place Bid");
//        solo.clickOnText("Confirm");
//        solo.clickOnText("Yes");
    }
}
