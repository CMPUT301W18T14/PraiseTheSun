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
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;


/**
 * Created by imtihan on 2018-03-19.
 * Unit tests for the UserProfileActivity Class
 */

public class UserProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private EditText usrname;
    private EditText phone;
    private EditText email;
    private Activity myActivity;
    private User testUser = new User("idontexist", "ordoi", "whoknowsImight", "ori@might.not");

    public UserProfileActivityTest(){
        super(UserProfileActivity.class);

    }

    @Override
    public void setUp() throws Exception{
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 solo = new Solo(getInstrumentation(), getActivity());
        super.setUp();

        myActivity = this.getActivity();
        CurrentUser.getInstance().setCurrentUser(testUser);
        myActivity.finish();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myActivity = this.getActivity();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        usrname = (EditText)myActivity.findViewById(R.id.UserProfileActivityUsername);
        email = (EditText)myActivity.findViewById(R.id.UserProfileActivityEmail);
        phone = (EditText)myActivity.findViewById(R.id.UserProfileActivityPhoneNumber);
    }

    public void testName(){


        Log.i("CurrentUserName", CurrentUser.getInstance().getCurrentUser().getUsername());
        Log.i("usrnameOnScreen", usrname.toString());
        //assertEquals(CurrentUser.getInstance().getCurrentUser().getUsername(), usrname.getText().toString());
    }

    public void testEmail(){


        Log.i("CurrentUserEmail", CurrentUser.getInstance().getCurrentUser().getEmail());
        Log.i("emailOnScreen", email.getText().toString());
        //assertEquals(CurrentUser.getInstance().getCurrentUser().getEmail(), email.getText().toString());
    }

    public void testPhone(){


        Log.i("CurrentUserPhone", CurrentUser.getInstance().getCurrentUser().getPhoneNumber());
        Log.i("phoneOnScreen", phone.toString());
        //assertEquals(CurrentUser.getInstance().getCurrentUser().getPhoneNumber(), phone.getText().toString());
    }


}
