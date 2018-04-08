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
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        adpaterSetup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adpaterSetup();
    }

    private void adpaterSetup() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_view_my_tasks, null, false);
        drawerLayout.addView(contentView, 0);

        myTasksRecyclerView = (RecyclerView) findViewById(R.id.my_tasks_recycler_view);
        myTasksLayoutManager = new LinearLayoutManager(activity);
        myTasksRecyclerView.setLayoutManager(myTasksLayoutManager);
        loadingCircle = (ProgressBar) findViewById(R.id.loadingCircle);

        //Give some time to get updated values from the server
        try {
            Thread.sleep(500);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        filterOptions();
    }

    /**
     * When the filter is selected, the recycle view displays only the users tasks that
     * corresponds to the tasks status
     */
    private void filterOptions() {
        Spinner filterSpinner = (Spinner) findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        final ViewStub emptyListMessage = (ViewStub) findViewById(R.id.emptyListMessage);
        emptyListMessage.setLayoutResource(R.layout.empty_task_list);

        /*
         * When the filter is selected, the recycle view displays only the users tasks that
         * corresponds to the tasks status
         */
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                TaskList myTasks = new TaskList();
                loadingCircle.setVisibility(View.VISIBLE);
                try {
                    myTasks = dm.getUserTasks(CurrentUser.getInstance().getCurrentUser().getId()
                    );
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }

                if (parent.getItemAtPosition(pos).equals("Requested")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (myTasks.get(i).getStatus() != TaskStatus.REQUESTED) {
                            myTasks.removeTask(myTasks.get(i));
                            --i;
                        }
                    }
                } else if (parent.getItemAtPosition(pos).equals("Bidded")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (myTasks.get(i).getStatus() != TaskStatus.BIDDED) {
                            myTasks.removeTask(myTasks.get(i));
                            --i;
                        }
                    }
                } else if (parent.getItemAtPosition(pos).equals("Assigned")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (myTasks.get(i).getStatus() != TaskStatus.ASSIGNED) {
                            myTasks.removeTask(myTasks.get(i));
                            --i;
                        }
                    }
                } else if (parent.getItemAtPosition(pos).equals("Done")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (myTasks.get(i).getStatus() != TaskStatus.DONE) {
                            myTasks.removeTask(myTasks.get(i));
                            --i;
                        }
                    }
                }

                //If taskList is empty, notify the user
                if (myTasks.getSize() == 0) {
                    emptyListMessage.setVisibility(View.VISIBLE);
                } else {
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