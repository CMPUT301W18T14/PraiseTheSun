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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * @author imtihan
 * @version 1.0
 */
public class RootActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

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
                        switch (item.getItemId()){
                            case R.id.get_home:
                                i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.add_new_task:
                                i = new Intent(getApplicationContext(), AddTaskActivity.class);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;
                            /*
                            case R.id.my_tasks:
                                i = new Intent(getApplicationContext(), MyTasksActivity.class);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_bids:
                                i = new Intent(getApplicationContext(), MyBidsActivity.class);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.my_assignments:
                                i = new Intent(getApplicationContext(), MyAssignmentsActivity.class);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.find_nearby_tasks:
                                i = new Intent(getApplicationContext(), FindNearbyTasksActivity.class);
                                startActivity(i);
                                drawerLayout.closeDrawers();
                                break;
                            */
                        }

                        return false;
                    }
                }
        );
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
