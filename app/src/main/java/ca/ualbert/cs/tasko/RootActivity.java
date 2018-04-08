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
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ualbert.cs.tasko.NotificationArtifacts.ViewNotificationActivity;
import ca.ualbert.cs.tasko.data.ConnectivityReceiver;
import ca.ualbert.cs.tasko.data.ConnectivityState;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 *
 * This activity extends every activity in the app that requires a menu.
 * Each activity must extend from this activity and inflate from it, thus allowing
 * the menu to actually be visible on the screen.
 * @author imtihan
 * @version 1.0
 *
 * references:
 * https://stackoverflow.com/questions/19451715/same-navigation-drawer-in-different-activities
 * Accessed 2018-03-10
 * http://shockingandroid.blogspot.ca/2017/04/navigation-drawer-in-every-activity.html
 * Accessed 2018-03-10
 */
public class RootActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    TextView username;
    User user;
    LocationManager lm;
    private static final String FILENAME = "nfile.sav";
    private CurrentUser cu = CurrentUser.getInstance();
    private User loggedInUser;
    NavigationView navigationView;
    Activity activity = this;
    boolean logCheck = false;
    /**
     * Takes a navigation_view for the formatting of the navigator menu,
     * creates a toolbar object so that the items can be clicked,
     * and places all the elements on a drawer to be shown in the navigation
     * menu.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        toolbar = (Toolbar) findViewById(R.id.navbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.get_home:
                                i = new Intent(getApplicationContext(), MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.add_new_task:
                                i = new Intent(getApplicationContext(), AddTaskActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_tasks:
                                i = new Intent(getApplicationContext(), ViewMyTasksActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_bids:
                                i = new Intent(getApplicationContext(), ViewTasksIBiddedOnActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_assignments:
                                i = new Intent(getApplicationContext(), ViewTasksAssignedActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.find_nearby_tasks:
                                boolean validGPS = false;
                                boolean validLocation = false;
                                try{
                                    validGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                }
                                catch (Exception e){}
                                try{
                                    validLocation = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                                }
                                catch (Exception e){}
                                if(validGPS || validLocation) {
                                    i = new Intent(getApplicationContext(), NearbyTasksActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(i);
                                    drawerLayout.closeDrawers();
                                } else {
                                    Toast.makeText(getApplicationContext() , "Location services disabled", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case R.id.view_profile:
                                i = new Intent(getApplicationContext(), UserActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.notifications:
                                i = new Intent(getApplicationContext(), ViewNotificationActivity
                                        .class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.logout:
                                i = new Intent(getApplicationContext(), LogOutActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;
                        }

                        return false;
                    }
                }
        );


    }
    @Override
    public void onStart(){
        super.onStart();

        if (!cu.loggedIn()) {
            if (!checkForUser()) {
                finish();
                return;
            }
        }
        user = CurrentUser.getInstance().getCurrentUser();
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.MenuUsername);
        Log.i("User stuff:", user.getUsername() + " \\ " + user.getEmail()  );
        username.setText(user.getUsername());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void finish(){
        super.finish();

    }

    @Override
    public void startActivity(Intent intent){
        super.startActivity(intent);
    }

    /**
     * checkForUser is called when the app starts, it looks in a local file to see if a username
     * string exists and send the user to the appropriate activity depending on if the string
     * exsits or not. If it does exists it sets that user as the currently logged in user and
     * starts the app in MainActivity, otherwise the app starts in Login Activity.
     * @throws NoInternetException Throws an exception if no Internet Connection is found.
     */
    private boolean checkForUser(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            loggedInUser = gson.fromJson(in, User.class);
        } catch (IOException e) {
            loggedInUser = null;
            e.printStackTrace();
        } catch(IllegalStateException | JsonSyntaxException e){
            loggedInUser = null;
        }

        if(loggedInUser == null){
            Log.d("LOGIN", "Current logged is NULL COnnectivity: " + ConnectivityState.getConnected());
            if(!ConnectivityState.getConnected()){
                //TODO: SEND TO NO INTERNET PLEASE TRY AGAIN THING
            }
            int result = 10;
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, result);
            return false;
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

            //Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);
            return true;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode ==10){
            logCheck = data.getBooleanExtra("check", false);

        }
    }
}
