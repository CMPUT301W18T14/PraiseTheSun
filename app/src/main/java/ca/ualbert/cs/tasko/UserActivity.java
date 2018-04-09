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
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * UserActivity shows the user's information. Accessed from the menu
 *
 * @author imtihan
 * @see RootActivity, User
 */
public class UserActivity extends RootActivity {
    private TextView username;
    private TextView email;
    private TextView phone;
    private ImageView star;
    private Button myTasks;
    private Button myAssignments;
    private Button editProfile;
    private UserActivity activity = this;
    private static final String FILENAME = "nfile.sav";
    User cUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_user, null, false);
        drawerLayout.addView(contentView, 0);

        try {
            User temp = CurrentUser.getInstance().getCurrentUser();
            cUser = DataManager.getInstance().getUserById(temp.getId());
            CurrentUser.getInstance().setCurrentUser(cUser);
            try {
                FileOutputStream fos = openFileOutput(FILENAME,
                        Context.MODE_PRIVATE);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
                Gson gson = new Gson();
                gson.toJson(cUser, out);
                out.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NoInternetException e) {
            e.printStackTrace();
        }



        username = (TextView)findViewById(R.id.UserActivityUsername);
        email = (TextView) findViewById(R.id.UserActivityUserEmail);
        phone = (TextView) findViewById(R.id.UserActivityPhone);
        star = (ImageView) findViewById(R.id.userActivityStar);

        username.setText(cUser.getName());
        email.setText(cUser.getEmail());
        phone.setText(cUser.getPhoneNumber());



        myTasks = (Button) findViewById(R.id.UserActivityMyTasksButton);
        myAssignments = (Button) findViewById(R.id.UserActivityMyAssignmentButton);
        editProfile = (Button) findViewById(R.id.UserActivityEditProfileButton);
        if(cUser.isPrefered()){
            star.setImageResource(R.drawable.ic_star);
            Log.i("here ", "is a star " + cUser.isPrefered());
        }
        Log.i("here ", "is a star " + cUser.isPrefered());
        myTasksButton();
        myAssignmentsButton();
        editProfileButton();
    }

    @Override
    public void onStart() {

        super.onStart();

        if(cUser.isPrefered()){
            star.setImageResource(R.drawable.ic_star);
            Log.i("here ", "is a star " + cUser.isPrefered());
        }
        Log.i("here ", "is a star " + cUser.isPrefered());
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
        myAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(activity, ViewTasksAssignedActivity.class);
                startActivity(intent);
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
