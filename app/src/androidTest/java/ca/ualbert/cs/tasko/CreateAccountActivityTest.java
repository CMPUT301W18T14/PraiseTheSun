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
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by Thomas on 2018-03-04.
 * Testing class for the activity CreateAccountActivity
 *
 * @see CreateAccountActivity
 * @author tlafranc
 */

public class CreateAccountActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CreateAccountActivityTest() {
        super(CreateAccountActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAccount() {
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.enterText((EditText) solo.getView(R.id.createAccountUsername), "tlafranc");
        solo.enterText((EditText) solo.getView(R.id.createAccountName), "Thomas Lafrance");
        solo.enterText((EditText) solo.getView(R.id.createAccountEmail), "tlafranc@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.createAccountPhone), "780-111-1111");
        solo.clickOnButton("Create");
        try {
            assertTrue(DataManager.getInstance().getUserByUsername("tlafranc", getActivity()
                    .getApplicationContext()).getUsername() != null);
        } catch (NoInternetException e) {

        }
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
