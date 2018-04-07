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
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualbert.cs.tasko.NotificationArtifacts.ViewNotificationActivity;

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
    Activity activity = this;
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        toolbar = (Toolbar) findViewById(R.id.navbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        user = CurrentUser.getInstance().getCurrentUser();
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.MenuUsername);
        Log.i("User stuff:", user.getUsername() + " \\ " + user.getEmail()  );
        username.setText(user.getUsername());
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.get_home:
                                i = new Intent(getApplicationContext(), MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.add_new_task:
                                i = new Intent(getApplicationContext(), AddTaskActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_tasks:
                                i = new Intent(getApplicationContext(), ViewMyTasksActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_bids:
                                i = new Intent(getApplicationContext(), ViewTasksIBiddedOnActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_assignments:
                                i = new Intent(getApplicationContext(), ViewTasksAssignedActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
                                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(i);
                                    drawerLayout.closeDrawers();
                                } else {
                                    Toast.makeText(getApplicationContext() , "Location services disabled", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case R.id.view_profile:
                                i = new Intent(getApplicationContext(), UserActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                drawerLayout.closeDrawers();
            }
        });

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
}
