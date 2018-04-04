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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Shows the user profile of a user clicked in a any of the relevant fields
 * Requires Intent to be passed with a string field called "id"
 *
 * @author imtihan
 * @see DataManager
 *
 *
 */
public class OtherUsersProfileActivity extends RootActivity {

    TextView name;
    TextView email;
    TextView phone;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_other_users_profile, null, false);
        drawerLayout.addView(contentView, 0);

        name = (TextView) findViewById(R.id.OtherUserProfileName);
        email = (TextView) findViewById(R.id.OtherUsersProfileEmail);
        phone = (TextView) findViewById(R.id.OtherUsersProfilePhone);

        Intent intent = getIntent();
        try {
            user = DataManager.getInstance().getUserById(intent.getStringExtra("id"), OtherUsersProfileActivity.this);
        } catch (NoInternetException e) {
            e.printStackTrace();
        }
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());
    }
}