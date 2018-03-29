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
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.ualbert.cs.tasko.data.DataManager;

/**
 * Shows user information in an editable format
 * @author imtihan
 * @see User, UserActivity, RootActivity
 */
public class UserProfileActivity extends RootActivity {

    EditText username;
    EditText phoneNumber;
    EditText emailAddress;

    Button confirmButton;
    Button cancelButton;
    private Activity activity = this;
    /**
     * Extends the menu into this activity
     * Checks if a user is logged in
     * If they are, it will show their information on the screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        setContentView(R.layout.activity_user_profile);

        username = (EditText)findViewById(R.id.UserProfileActivityUsername);
        phoneNumber = (EditText)findViewById(R.id.UserProfileActivityPhoneNumber);
        emailAddress = (EditText)findViewById(R.id.UserProfileActivityEmail);

        confirmButton = (Button) findViewById(R.id.UserProfileConfirmButton);
        cancelButton = (Button) findViewById(R.id.UserProfileCancelButton);
        User user = CurrentUser.getInstance().getCurrentUser();

        if(CurrentUser.getInstance().loggedIn()){

            emailAddress.setText(user.getEmail());
            username.setText(user.getName());
            phoneNumber.setText(user.getPhoneNumber());
            Log.i("User logged in", emailAddress.getText().toString() );
        }

        ConfirmButtonClick();
        CancelButtonClick();
    }

    public void ConfirmButtonClick(){
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_OK);
                CurrentUser.getInstance().getCurrentUser().setName(username.getText().toString());
                CurrentUser.getInstance().getCurrentUser().setEmail(emailAddress.getText().toString());
                CurrentUser.getInstance().getCurrentUser().setPhoneNumber(phoneNumber.getText().toString());
                //TODO edit in database
                Intent intent = new Intent(activity, UserActivity.class);
                startActivity(intent);
            }
        });
    }

    public void CancelButtonClick(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(activity, UserActivity.class);
                startActivity(intent);
            }
        });
    }
}
