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

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ualbert.cs.tasko.data.ConnectivityReceiver;
import ca.ualbert.cs.tasko.data.ConnectivityState;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;



/**
 * OpeningActivity is the first activity our app will open too, it checks in a local file if a
 * username string exists and if it does it starts the app at the MainActivity and sets the set
 * the User object associated with username string in the CurrentUser singleton. Otherwise, a user
 * is directed to the LoginActivity.
 * @see User
 * @see LoginActivity
 * @see MainActivity
 *
 * @author spack
 */

public class OpeningActivity extends AppCompatActivity {

    private CurrentUser cu = CurrentUser.getInstance();
    private DataManager dm = DataManager.getInstance();
    private static final String FILENAME = "nfile.sav";
    private User loggedInUser;

    /**
     * Standard OnCreate Method, note there is no layout associated with this activity.
     * @param savedInstanceState Get the saved state form the current device.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.getInstance().init(getApplicationContext());
        BroadcastReceiver conReceiver = new ConnectivityReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.getApplicationContext().registerReceiver(conReceiver, filter);
    }

    /**
     * If the current user is already set and we navigate to this activity, then exit the app and
     * set the current user to null, otherwise the app has just started and we go and check
     * for user in the local file.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!cu.loggedIn())
            checkForUser();
        else
            this.finishAffinity();
    }

    /**
     * checkForUser is called when the app starts, it looks in a local file to see if a username
     * string exists and send the user to the appropriate activity depending on if the string
     * exsits or not. If it does exists it sets that user as the currently logged in user and
     * starts the app in MainActivity, otherwise the app starts in Login Activity.
     * @throws NoInternetException Throws an exception if no Internet Connection is found.
     */
    private void checkForUser(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            loggedInUser = gson.fromJson(in, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(loggedInUser == null){
            if(ConnectivityState.getConnected()){
                //TODO: SEND TO NO INTERNET PLEASE TRY AGAIN THING
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            cu.setCurrentUser(loggedInUser);

            //Begin Notification Alarm
            JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context
                    .JOB_SCHEDULER_SERVICE);
            JobInfo.Builder infoBuilder = new JobInfo.Builder(1, new ComponentName
                    (getPackageName(), NotificationService.class.getName()));
            infoBuilder.setMinimumLatency(5000); //Every 5 secods
            mJobScheduler.schedule(infoBuilder.build());
            //End notification alarm

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }

}
