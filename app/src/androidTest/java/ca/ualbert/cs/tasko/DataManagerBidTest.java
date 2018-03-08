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
import android.util.Log;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by Thomas on 2018-03-07.
 */

public class DataManagerBidTest extends ActivityInstrumentationTestCase2 {

    private Bid bid1;
    private DataManager dm;
    private String userID;

    public DataManagerBidTest() {
        super(MainActivity.class);
    }

    public void setUp() {
        userID = "AWIEMxQnTFjKf1vhacZH";
        bid1 = new Bid(userID, 10, "Thomas");
        dm = DataManager.getInstance();
    }

    public void testPutBid() {
        BidList returnedBids = null;
        try {
            dm.addBid(bid1, getActivity().getApplicationContext());
        } catch(NoInternetException e) {
            Log.i("Error", "No internet connection");
        }
        try {
            returnedBids = dm.getUserBids(bid1.getBidID(), getActivity()
                    .getApplicationContext());
        } catch (NoInternetException e){
            Log.i("Error", "The phone has no internet so this test will fail");
        }
        assertFalse(returnedBids == null);
        assertEquals(returnedBids.getBid(userID), bid1.getBidID());
    }
}
