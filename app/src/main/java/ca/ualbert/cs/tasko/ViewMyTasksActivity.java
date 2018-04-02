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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * ViewMyTasksActivity is a class that creates a recyclerview using TaskListAdapter filled with the
 * tasks that the user has created. The user also has the option to filter theses tasks by each of
 * their status
 *
 * @author ryandromano
 */
public class ViewMyTasksActivity extends RootActivity {
    private RecyclerView myTasksRecyclerView;
    private RecyclerView.Adapter myTasksAdapter;
    private RecyclerView.LayoutManager myTasksLayoutManager;
    private ProgressBar loadingCircle;
    private DataManager dm = DataManager.getInstance();
    private ViewMyTasksActivity activity = this;

    /**
     * Called when the activity is created, fills the recyclerview  with the user's tasks they
     * have posted recyclerview
     *
     * @param savedInstanceState the instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_tasks);

        if (CurrentUser.getInstance().getCurrentUser() == null) {
            try {
                CurrentUser.getInstance().setCurrentUser(dm.getUserByUsername("rromano", getApplicationContext()));
            }
            catch (NoInternetException e) {
                e.printStackTrace();
            }
        }

        myTasksRecyclerView = (RecyclerView) findViewById(R.id.my_tasks_recycler_view);
        myTasksLayoutManager = new LinearLayoutManager(activity);
        myTasksRecyclerView.setLayoutManager(myTasksLayoutManager);
        loadingCircle = (ProgressBar) findViewById(R.id.loadingCircle);

        Spinner filterSpinner = (Spinner) findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        final ViewStub emptyListMessage = (ViewStub) findViewById(R.id.emptyListMessage);
        emptyListMessage.setLayoutResource(R.layout.empty_task_list);


        /**
         * When the filter is selected, the recycle view displays only the users tasks that
         * corresponds to the tasks status
         */
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                TaskList myTasks = new TaskList();
                loadingCircle.setVisibility(View.VISIBLE);
                try {
                    myTasks = dm.getUserTasks(CurrentUser.getInstance().getCurrentUser().getId(),
                            activity);
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }

                if (parent.getItemAtPosition(pos).equals("Requested")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (myTasks.get(i).getStatus() != Status.REQUESTED) {
                            myTasks.removeTask(myTasks.get(i));
                            --i;
                        }
                    }
                }
                else if (parent.getItemAtPosition(pos).equals("Bidded")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (myTasks.get(i).getStatus() != Status.BIDDED) {
                            myTasks.removeTask(myTasks.get(i));
                            --i;
                        }
                    }
                }
                else if (parent.getItemAtPosition(pos).equals("Assigned")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (myTasks.get(i).getStatus() != Status.ASSIGNED) {
                            myTasks.removeTask(myTasks.get(i));
                            --i;
                        }
                    }
                }

                //If taskList is empty, notify the user
                if (myTasks.getSize() == 0) {
                    emptyListMessage.setVisibility(View.VISIBLE);
                }
                else {
                    emptyListMessage.setVisibility(View.GONE);
                }

                myTasksAdapter = new TaskListAdapter(activity, myTasks);
                loadingCircle.setVisibility(View.GONE);
                myTasksRecyclerView.setAdapter(myTasksAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}