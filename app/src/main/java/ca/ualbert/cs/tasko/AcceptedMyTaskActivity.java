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
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
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

    private void fillInformation() {
        //Taken From https://stackoverflow.com/questions/2538787/
        //how-to-display-an-output-of-float-data-with-2-decimal-places-in-java
        //2018-03-26
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        assignedTaskName.setText(assignedCurrentTask.getTaskName());
        assignedTaskDescription.setText(assignedCurrentTask.getDescription());
        String lowestBidAmount = df.format(assignedCurrentTask.getMinBid());
        assignedTaskStatus.setText(assignedCurrentTask.getStatus().toString() + ": bid of $" +
                lowestBidAmount + " by ");
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
                                try {
                                    dm.putTask(assignedCurrentTask, context);
                                } catch (NoInternetException e) {
                                    e.printStackTrace();
                                }
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
                                //Get bids that were on this task
                                BidList taskBids = new BidList();
                                try {
                                    taskBids = dm.getTaskBids(assignedCurrentTask.getId(), context);
                                } catch (NoInternetException e) {
                                    e.printStackTrace();
                                }

                                //Set this task's status to REQUESTED if there were not other bids
                                //on this task prior to the assignment
                                if (taskBids.getSize() == 1) {
                                    //Change Task Status
                                    assignedCurrentTask.setStatus(TaskStatus.REQUESTED);
                                    //Remove this rejected bid
                                    taskBids.removeBid(taskBids.get(0));
                                }


                                //Set this task's status to BIDDED if there were other bids on this
                                //task prior to the assignment
                                else {
                                    //Change Task Status
                                    assignedCurrentTask.setStatus(Status.BIDDED);
                                    //Change Bid Status'
                                    //Make all other bids rejected
                                    assignedCurrentTask.getMinBid();
                                    for(int i = 0; i < taskBids.getSize(); i++){
                                        //Change hidden bids to pending again
                                        if (taskBids.get(i).getStatus() != Status.ASSIGNED) {
                                            taskBids.get(i).setStatus(Status.PENDING);
                                            //if (taskBid)
                                        }
                                        //Remove rejected-assigned bid from bidlist
                                        else {
                                            taskBids.removeBid(taskBids.get(i));
                                            --i;
                                        }
                                    }
                                }

                                //Put these updates into the database
                                try {
                                    dm.putTask(assignedCurrentTask, context);
                                } catch (NoInternetException e) {
                                    e.printStackTrace();
                                }
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
