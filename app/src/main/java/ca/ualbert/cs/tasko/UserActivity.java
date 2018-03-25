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

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * UserActivity shows the user's information. Accessed from the menu
 *
 * @author imtihan
 * @see RootActivity, User
 */
public class UserActivity extends RootActivity {
    TextView username;
    TextView email;
    TextView phone;
    Button myTasks;
    Button myAssignments;
    Button editProfile;
    private UserActivity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        setContentView(R.layout.activity_user);

        username = (TextView)findViewById(R.id.UserActivityUsername);
        email = (TextView) findViewById(R.id.UserActivityUserEmail);
        phone = (TextView) findViewById(R.id.UserActivityPhone);

        username.setText(CurrentUser.getInstance().getCurrentUser().getUsername());
        email.setText(CurrentUser.getInstance().getCurrentUser().getEmail());
        phone.setText(CurrentUser.getInstance().getCurrentUser().getPhoneNumber());

        myTasks = (Button) findViewById(R.id.UserActivityMyTasksButton);
        myAssignments = (Button) findViewById(R.id.UserActivityMyAssignmentButton);
        editProfile = (Button) findViewById(R.id.UserActivityEditProfileButton);

        myTasksButton();
    }

    public void myTasksButton(){
        myTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(activity, ViewMyTasksActivity.class);
                startActivity(intent);
            }
        });
    }

    public void myAssignmentsButton(){
        //TODO when the activity is done
        myAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                //Intent intent = new Intent(activity, ViewMyAssignme)
            }
        });
    }

    public void editProfileButton(){
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(activity,UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}
