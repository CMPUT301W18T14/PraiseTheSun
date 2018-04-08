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

/**
 * Testing class for the activity ViewSearchedTaskDetailsActivity
 *
 * @see ViewSearchedTaskDetailsActivity
 * @author Chase Buhler
 */
public class ViewSearchedTaskDetailsActivityTest extends ActivityInstrumentationTestCase2 {

    private EditText bidAmount;
    private Solo solo;

    public ViewSearchedTaskDetailsActivityTest(){
        super(ViewSearchedTaskDetailsActivity.class);
    }

    @Override
    public void setUp(){

        Intent i = new Intent();
        i.putExtra("TaskID", "AWI9HCBnAJsZenWtuKst");
        setActivityIntent(i);
        solo = new Solo(getInstrumentation(), getActivity());

    }

    public void testPlaceValidBid(){
        solo.assertCurrentActivity("Wrong Activity", ViewSearchedTaskDetailsActivity.class);
        solo.clickOnButton("Place Bid");
        bidAmount = (EditText) solo.getView(R.id.bidAmount);
        solo.enterText(bidAmount, "4.12");
        solo.clickOnText("Confirm");
        solo.clickOnText("Yes");
    }

    public void testPlaceEmptyBid(){
        solo.assertCurrentActivity("Wrong Activity", ViewSearchedTaskDetailsActivity.class);
        solo.clickOnButton("Place Bid");
        solo.clickOnText("Confirm");
        solo.clickOnText("Yes");
    }
}
