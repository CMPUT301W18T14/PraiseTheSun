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
    private String userID1 = "AWIEMxQnTFjKf1vhacZH";
    private Bid bid1 = new Bid(userID1, 10, "TestID");
    private String userID2 = "NewId";
    private Bid bid2 = new Bid(userID2, 20, "NewTask");

    public ViewBidsOnTaskTest() {
        super(ViewBidsOnTaskActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testPutBid() {
        boolean isConnected = true;
        BidList returnedBids = null;
        try {
            dm.addBid(bid1, getActivity().getApplicationContext());
        } catch(NoInternetException e) {
            Log.i("Error", "No internet connection");
            isConnected = false;
        }
        assertTrue(isConnected);
        try {
            returnedBids = dm.getUserBids(bid1.getUserID(), getActivity()
                    .getApplicationContext());
        } catch (NoInternetException e) {
            Log.i("Error", "The phone has no internet so this test will fail");
        }
        assertFalse(returnedBids == null);
        assertEquals(returnedBids.getBid(userID1).getValue(), bid1.getValue());
        assertEquals(returnedBids.getBid(userID1).getTaskID(), bid1.getTaskID());
    }

    public void testGetBidsByTask() {
        boolean isConnected = true;
        BidList returnedBids = null;
        try {
            dm.addBid(bid2, getActivity().getApplicationContext());
        } catch(NoInternetException e) {
            Log.i("Error", "No internet connection");
            isConnected = false;
        }
        assertTrue(isConnected);
        try {
            returnedBids = dm.getTaskBids(bid2.getTaskID(), getActivity()
                    .getApplicationContext());
        } catch (NoInternetException e) {
            Log.i("Error", "The phone has no internet so this test will fail");
        }
        assertFalse(returnedBids == null);
        Log.i("Not Error", returnedBids.getBids().toString());
        assertEquals(returnedBids.getBid(userID2).getValue(), bid2.getValue());
        assertEquals(returnedBids.getBid(userID2).getTaskID(), bid2.getTaskID());
    }

    public void testRecyclerView() throws NoInternetException {

        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);
    }
}
