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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class ViewMyTasksActivity extends RootActivity {
    private RecyclerView myTasksRecyclerView;
    private RecyclerView.Adapter myTasksAdapter;
    private RecyclerView.LayoutManager myTasksLayoutManager;
    private DataManager dm = DataManager.getInstance();
    private ViewMyTasksActivity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_tasks);

        myTasksRecyclerView = (RecyclerView) findViewById(R.id.my_tasks_recycler_view);
        myTasksLayoutManager = new LinearLayoutManager(activity);
        myTasksRecyclerView.setLayoutManager(myTasksLayoutManager);

        Spinner filterSpinner = (Spinner) findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        final ViewStub emptyListMessage = (ViewStub) findViewById(R.id.emptyListMessage);
        emptyListMessage.setLayoutResource(R.layout.empty_task_list);


        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                TaskList myTasks = new TaskList();
                try {
                    myTasks = dm.getUserTasks(CurrentUser.getInstance().getCurrentUser().getId(),
                                              activity);
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }

                if (parent.getItemAtPosition(pos).equals("Requested")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (!myTasks.get(i).getStatus().equals(Status.REQUESTED)) {
                            myTasks.removeTask(myTasks.get(i));
                        }
                    }
                }
                else if (parent.getItemAtPosition(pos).equals("Bidded")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (!myTasks.get(i).getStatus().equals(Status.BIDDED)) {
                            myTasks.removeTask(myTasks.get(i));
                        }
                    }
                }
                else if (parent.getItemAtPosition(pos).equals("Assigned")) {
                    for (int i = 0; i < myTasks.getSize(); i++) {
                        if (!myTasks.get(i).getStatus().equals(Status.ASSIGNED)) {
                            myTasks.removeTask(myTasks.get(i));
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
                myTasksRecyclerView.setAdapter(myTasksAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}