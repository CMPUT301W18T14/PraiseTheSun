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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button deleteButton;
    private Button editButton;
    private Button viewBidsButton;
    private final Context context = this;
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
        deleteButton = (Button) findViewById(R.id.deleteButton);
        editButton = (Button) findViewById(R.id.editButton);
        viewBidsButton = (Button) findViewById(R.id.placeBidButton);
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

        ImageView imageView = (ImageView) findViewById(R.id.myTasksImageView);
        if (currentTask.hasPhoto()) {
            imageView.setImageBitmap(currentTask.getCoverPhoto());
        }

        setupDeleteButton();

        setupEditButton();

        setupViewBidsButton();
    }

    private void setupDeleteButton() {
        //Dialog for choosing to make a bid on the task
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Confirm deletion and return to main page
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTaskDetailsActivity.this);
                final View deleteView = getLayoutInflater().inflate(R.layout.delete_my_task_dialog, null);

                //confirmButton.setOnClickListener(new View.OnClickListener() {
                builder.setView(deleteView).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Are you positive you want to delete this task?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Here's where the task deletion code goes
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                });

                builder.setView(deleteView);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    private void setupEditButton() {
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(currentTask.getStatus() != Status.REQUESTED) {
                    Toast.makeText(getApplicationContext(),"This task already has bids on it. This task can no longer be edited.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //This should go to a pre-filled in version of the AddTaskActivity
                }
            }
        });
    }

    private void setupViewBidsButton() {
        viewBidsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context thiscontext = getApplicationContext();
                Intent intent;
                intent = new Intent(thiscontext, ViewBidsOnTaskActivity.class);
                intent.putExtra("TaskID", currentTask.getId());
                thiscontext.startActivity(intent);
            }
        });

    }

    private void fillInformation() {
        taskName.setText(currentTask.getTaskName());
        taskDescription.setText(currentTask.getDescription());
        if (currentTask.getStatus() == Status.BIDDED) {
            taskStatus.setText(currentTask.getStatus().toString() + ": Lowest bid of $" + currentTask.getMinBid().toString());
        }
        else {
            taskStatus.setText(currentTask.getStatus().toString());
        }
    }

    public void onPhotoClick(View view) {
        Intent viewPhotosIntent = new Intent(this, ViewPhotoActivity.class);
        viewPhotosIntent.putExtra("photos", currentTask);
        startActivity(viewPhotosIntent);
    }
}
