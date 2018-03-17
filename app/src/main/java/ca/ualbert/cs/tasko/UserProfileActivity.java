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

import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.widget.EditText;

public class UserProfileActivity extends RootActivity {
    EditText username, phoneNumber, emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        setContentView(R.layout.activity_user_profile);

        username = (EditText)findViewById(R.id.UserProfileActivityUsername);
        phoneNumber = (EditText)findViewById(R.id.UserProfileActivityPhoneNumber);
        emailAddress = (EditText)findViewById(R.id.UserProfileActivityEmail);
        User user = CurrentUser.getInstance().getCurrentUser();

        if(CurrentUser.getInstance().loggedIn()){
            username.setText(user.getUsername());
            phoneNumber.setText(user.getPhoneNumber());
            emailAddress.setText(user.getEmail());
        }

    }
}
