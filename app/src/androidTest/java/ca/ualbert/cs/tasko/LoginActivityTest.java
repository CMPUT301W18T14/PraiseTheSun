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
 * Tests The Login Activity.
 * @see LoginActivity
 *
 * @author spack
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private User testUser = new User("username1", "John Doe", "123-456-9999",
            "jdoe@example.com");

    public LoginActivityTest() {super (LoginActivity.class);}

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        dm.init(getActivity().getApplicationContext());
        // Taken from https://stackoverflow.com/questions/28960898/getting-context-in-androidtestcase-or-instrumentationtestcase-in-android-studio/29063736#29063736
        // 2018-03-06
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    /**
     * VALID credentials means that the user name that the user inputs is already in the DataBase,
     * if Valid should switch to MainActivity.
     */
    public void testLoginVALIDCredentials() throws NoInternetException {
        try {
            dm.putUser(testUser);
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameEditText), "username1");
        solo.clickOnButton("LOGIN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * INVALID credentials means that the user name that the user inputs is not already in the
     * DataBase if invalid it should remain in LoginActivity.
     */
    public void testLoginINVALIDCredentials() throws InterruptedException {
        try {
            dm.putUser(testUser);
        } catch(NoInternetException e){
            e.printStackTrace();
        }
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnButton("LOGIN");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }

    public void testCreateAccountButton(){

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);


    }
}
