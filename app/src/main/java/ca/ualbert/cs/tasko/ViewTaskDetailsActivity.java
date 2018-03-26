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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * ViewMyTaskDetailsActivity takes the task information from the task selected from the recyclerview
 * in ViewMyTasksActivity and displays that information and gives the user the option to select bid
 *
 * @author ryandromano
 */
public class ViewTaskDetailsActivity extends AppCompatActivity {
    private TextView taskDescription;
    private TextView taskName;
    private TextView taskStatus;
    private Task currentTask;
    private DataManager dm = DataManager.getInstance();

    /**
     * On creation of the activity, fills the relevant displays with information selected from the
     * task selected on ViewMyTasksActivity
     *
     * @param savedInstanceState the instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button and TextView definitions
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button editButton = (Button) findViewById(R.id.editButton);
        Button viewBidsButton = (Button) findViewById(R.id.placeBidButton);
        taskName = (TextView) findViewById(R.id.taskName);
        taskDescription = (TextView) findViewById(R.id.taskDescription);
        taskStatus = (TextView) findViewById(R.id.taskStatus);

        Bundle extras = getIntent().getExtras();

        try {
            String taskID = extras.getString("TaskID");
            currentTask = dm.getTask(taskID, this);
            fillInformation();
        } catch (NullPointerException e) {
            Log.i("Error", "TaskID not properly passed");
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        //Dialog for choosing to make a bid on the task
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Confirm deletion and return to main page
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTaskDetailsActivity.this);
                final View deleteView = getLayoutInflater().inflate(R.layout.delete_my_task_dialog, null);
                Button confirmButton = (Button) deleteView.findViewById(R.id.confirmButton);
                Button cancelButton = (Button) deleteView.findViewById(R.id.cancelButton);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //CONFIRMATION DELETION OF TASK
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Probably a better way to do this but it works for now
                        finish();
                        startActivity(new Intent(ViewTaskDetailsActivity.this, ViewTaskDetailsActivity.class));
                    }
                });

                builder.setView(deleteView);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        /*
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //This should go to a pre-filled in version of the AddTaskActivity
            }
        });
        */

        viewBidsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ViewTaskDetailsActivity.this, ViewBidsOnTaskActivity.class));
            }
        });

    }

    private void fillInformation() {
        taskName.setText(currentTask.getTaskName());
        taskDescription.setText(currentTask.getDescription());
        taskStatus.setText(currentTask.getStatus().toString());
    }

    public void onEditClick(View view) {
        Intent editTask = new Intent(this, AddTaskActivity.class);
        editTask.putExtra("task", currentTask);
        startActivity(editTask);
        try {
            currentTask = DataManager.getInstance().getTask(currentTask.getId(), this);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fillInformation();
        } catch (NoInternetException e) {
            e.printStackTrace();
        }
    }

}
