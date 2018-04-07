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

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationType;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class AcceptedMyTaskActivity extends AppCompatActivity {
    private TextView assignedTaskDescription;
    private TextView assignedTaskName;
    private TextView assignedTaskStatus;
    private Task assignedCurrentTask;
    private Button repostButton;
    private Button completedButton;
    private final Context context = this;
    private DataManager dm = DataManager.getInstance();
    private NotificationHandler nh = new NotificationHandler(context);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button and TextView definitions

        completedButton = (Button) findViewById(R.id.taskCompleteButton);
        repostButton = (Button) findViewById(R.id.taskRepostButton);
        assignedTaskName = (TextView) findViewById(R.id.assignedTaskName);
        assignedTaskDescription = (TextView) findViewById(R.id.assignedTaskDescription);
        assignedTaskStatus = (TextView) findViewById(R.id.assignedTaskStatusAndProvider);

        Bundle extras = getIntent().getExtras();

        try {
            String taskID = extras.getString("TaskID");
            assignedCurrentTask = dm.getTask(taskID, this);
            fillInformation();
        } catch (NullPointerException e) {
            Log.i("Error", "TaskID not properly passed");
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        ImageView imageView = (ImageView) findViewById(R.id.myTasksImageView);
        if (assignedCurrentTask.hasPhoto()) {
            imageView.setImageBitmap(assignedCurrentTask.getCoverPhoto());
        }

        setupCompleteButton();

        setupRepostButton();
    }

    @SuppressLint("SetTextI18n")
    private void fillInformation() {
        assignedTaskName.setText(assignedCurrentTask.getTaskName());
        assignedTaskDescription.setText(assignedCurrentTask.getDescription());
        assignedTaskStatus.setText(assignedCurrentTask.getStatus().toString() + ": bid of $" +
                assignedCurrentTask.getMinBid().toString() + "by " + assignedCurrentTask.getMinBid());
    }

    public void onPhotoClick(View view) {
        Intent viewPhotosIntent = new Intent(this, ViewPhotoActivity.class);
        viewPhotosIntent.putExtra("photos", assignedCurrentTask);
        startActivity(viewPhotosIntent);
    }

    private void setupCompleteButton() {
        completedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //confirmButton.setOnClickListener(new View.OnClickListener() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Was this task successfully completed?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Set this task's status to DONE
                                assignedCurrentTask.setStatus(TaskStatus.DONE);
                                finish();
                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                try {
                    nh.newNotification(assignedCurrentTask.getId(), NotificationType.RATING);
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    private void setupRepostButton() {
        repostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("This will repost your task, declining your currently accepted" +
                        " bid. Would you like to continue?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Set this task's status to REQUESTED if there were not other bids
                                //on this task prior to the assignment
                                NotificationHandler nh = new NotificationHandler(context);
                                try {
                                    nh.newNotification(assignedCurrentTask.getId(), NotificationType.TASK_REQUESTER_REPOSTED_TASK);
                                } catch (NoInternetException e) {
                                    e.printStackTrace();
                                }
                                assignedCurrentTask.setStatus(TaskStatus.REQUESTED);
                                finish();

                                //Set this task's status to BIDDED if there were other bids on this
                                //task prior to the assignment
                                //assignedCurrentTask.setStatus(Status.BIDDED);
                                //finish();
                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
