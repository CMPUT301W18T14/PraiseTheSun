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
    private EditText usernameText;
    private EditText nameText;
    private EditText emailText;
    private EditText phoneText;


    public CreateAccountActivityTest() {
        super(CreateAccountActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        usernameText = (EditText) solo.getView(R.id.createAccountUsername);
        nameText = (EditText) solo.getView(R.id.createAccountName);
        emailText = (EditText) solo.getView(R.id.createAccountEmail);
        phoneText = (EditText) solo.getView(R.id.createAccountPhone);
    }

    public void testAccount() {
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        String username = "jdoe67";
        solo.enterText(usernameText, username);
        solo.enterText(nameText, "John Doe");
        solo.enterText(emailText, "john@ualberta.ca");
        solo.enterText(phoneText, "780-111-2222");
        solo.clickOnButton("Create");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            assertTrue(DataManager.getInstance().getUserByUsername(username).getUsername() != null);
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        Activity activity = solo.getCurrentActivity();
        assertFalse(activity.getClass() == AddTaskActivity.class);
    }

    public void testEmptyUsername() {
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.enterText(nameText, "name");
        solo.enterText(emailText, "email@email.ca");
        solo.enterText(phoneText, "780-000-0000");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
    }

    public void testEmptyName() {
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.enterText(usernameText, "username");
        solo.enterText(emailText, "email@email.ca");
        solo.enterText(phoneText, "780-000-0000");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
    }

    public void testRestrictionsEmail() {
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.enterText(usernameText, "username");
        solo.enterText(nameText, "name");
        solo.enterText(phoneText, "780-000-0000");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);

        solo.enterText(emailText, "Invalid email");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
    }

    public void testRestrictionsNumber() {
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.enterText(usernameText, "username");
        solo.enterText(nameText, "name");
        solo.enterText(emailText, "email@email.ca");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);

        solo.enterText(phoneText, "7800000000");
        solo.clickOnButton("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
