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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
    private Button deleteButton;
    private CardView buttonCardView;
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
        deleteButton = (Button) findViewById(R.id.taskDeleteButton);
        assignedTaskName = (TextView) findViewById(R.id.assignedTaskName);
        assignedTaskDescription = (TextView) findViewById(R.id.assignedTaskDescription);
        assignedTaskStatus = (TextView) findViewById(R.id.assignedTaskStatusAndProvider);
        buttonCardView = (CardView) findViewById(R.id.myAssignedTaskButtonsCard);

        Bundle extras = getIntent().getExtras();

        try {
            String taskID = extras.getString("TaskID");
            assignedCurrentTask = dm.getTask(taskID);
            fillInformation();
        } catch (NullPointerException e) {
            Log.i("Error", "TaskID not properly passed");
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        ImageView imageView = (ImageView) findViewById(R.id.myAssignedTasksImageView);
        if (assignedCurrentTask.hasPhoto()) {
            imageView.setImageBitmap(assignedCurrentTask.getCoverPhoto());
        }

        if(assignedCurrentTask.getTaskRequesterID().equals(CurrentUser.getInstance().getCurrentUser().getId())) {
            buttonCardView.setVisibility(View.VISIBLE);
            setupCompleteButton();
            setupRepostButton();
            setupDeleteButton();
        }
        else {
            buttonCardView.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void fillInformation() {
        //Taken From https://stackoverflow.com/questions/2538787/
        //how-to-display-an-output-of-float-data-with-2-decimal-places-in-java
        //2018-03-26
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        assignedTaskName.setText(assignedCurrentTask.getTaskName());
        assignedTaskDescription.setText(assignedCurrentTask.getDescription());
        //Get bids that were on this task
        BidList taskBids = new BidList();
        try {
            taskBids = dm.getTaskBids(assignedCurrentTask.getId());
        } catch (NoInternetException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < taskBids.getSize(); i++) {
            if (taskBids.get(i).getStatus() == BidStatus.ACCEPTED) {
                String acceptedBidAmount = df.format(taskBids.get(i).getValue());
                try {
                    String acceptedBidUser = dm.getUserById(taskBids.get(i).getUserID()).getUsername();
                    assignedTaskStatus.setText(assignedCurrentTask.getStatus().toString() + ": bid of $" +
                            acceptedBidAmount + " by " + acceptedBidUser);
                }
                catch (NoInternetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onPhotoClick(View view) {
        Intent viewPhotosIntent = new Intent(this, ViewPhotoActivity.class);
        viewPhotosIntent.putExtra("photos", assignedCurrentTask);
        startActivity(viewPhotosIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 19) {
            assignedCurrentTask = (Task) data.getSerializableExtra("task");
            fillInformation();
            ImageView imageView = (ImageView) findViewById(R.id.myTasksImageView);
            if (assignedCurrentTask.hasPhoto()) {
                imageView.setImageBitmap(assignedCurrentTask.getCoverPhoto());
            }
            else {
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable
                        .ic_menu_gallery);
                imageView.setImageBitmap(image);
            }
        }
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
                                    dm.putTask(assignedCurrentTask);
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
                                    taskBids = dm.getTaskBids(assignedCurrentTask.getId());
                                } catch (NoInternetException e) {
                                    e.printStackTrace();
                                }

                                //Set this task's status to REQUESTED if there were not other bids
                                //on this task prior to the assignment

                                if (taskBids.getSize() == 1) {
                                    //Change Task Status
                                    assignedCurrentTask.setStatus(TaskStatus.REQUESTED);

                                    NotificationHandler nh = new NotificationHandler(context);
                                    try {
                                        nh.newNotification(assignedCurrentTask.getId(), NotificationType.TASK_REQUESTER_REPOSTED_TASK);
                                    } catch (NoInternetException e) {
                                        e.printStackTrace();
                                    }

                                    //Remove this rejected bid
                                    taskBids.removeBid(taskBids.get(0));
                                    assignedCurrentTask.setMinBidNull();
                                }

                                //Set this task's status to BIDDED if there were other bids on this
                                //task prior to the assignment
                                else {
                                    //Change Task Status
                                    assignedCurrentTask.setStatus(TaskStatus.BIDDED);
                                    //Change Bid Status'
                                    //Make all other bids rejected
                                    assignedCurrentTask.getMinBid();
                                    for(int i = 0; i < taskBids.getSize(); i++){
                                        //Change hidden bids to pending again
                                        if (taskBids.get(i).getStatus() != BidStatus.ACCEPTED) {
                                            taskBids.get(i).setStatus(BidStatus.PENDING);
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
                                    dm.putTask(assignedCurrentTask);
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
            }
        });
    }

    private void setupDeleteButton() {
        //Dialog for choosing to make a bid on the task
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Confirm deletion and return to main page
                AlertDialog.Builder builder = new AlertDialog.Builder(AcceptedMyTaskActivity.this);
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
                                        try {
                                            NotificationHandler nh = new NotificationHandler(context);
                                            nh.newNotification(assignedCurrentTask.getId(), NotificationType
                                                    .TASK_DELETED);
                                            dm.deleteTask(assignedCurrentTask);

                                            finish();
                                            Toast.makeText(getApplicationContext(),"Your task has successfully been deleted.", Toast.LENGTH_SHORT).show();
                                        }
                                        catch (NoInternetException e) {
                                            Log.i("Error", "No internet connection in " +
                                                    "ViewTaskDetailsActivity");
                                            Toast.makeText(context, "No Internet Connection!",
                                                    Toast.LENGTH_LONG).show();
                                        }
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
}
