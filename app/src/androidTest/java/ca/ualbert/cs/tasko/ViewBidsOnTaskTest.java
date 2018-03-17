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
    /*
    private DataManager dm = DataManager.getInstance();
    private String userID1 = "AWIEMxQnTFjKf1vhacZH";
    private Bid bid1 = new Bid(userID1, 10, "TestID");
    */
    /*
    private String userID2 = "NewId";
    private Bid bid2 = new Bid(userID2, 20, "NewTask");
    private Task task = new Task("requestorID", "TestTask4",
            "Help me with recyclerview adapters ahhhhhhh," +
                    "Help me find NullPointerErrors... :(");
                    */



    public ViewBidsOnTaskTest() {
        super(ViewBidsOnTaskActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

        /*
        try {
            dm.addBid(bid1, getActivity().getApplicationContext());
        } catch(NoInternetException e) {
            Log.i("Error", "No internet connection");
        }
        */

    }



    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    /*

    public void testRecyclerView() {
        solo.assertCurrentActivity("Wrong Activity", ViewBidsOnTaskActivity.class);
    }
    */
}
