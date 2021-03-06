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
    private boolean edit;

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
        viewBidsButton = (Button) findViewById(R.id.viewBidsButton);
        taskName = (TextView) findViewById(R.id.taskName);
        taskDescription = (TextView) findViewById(R.id.taskDescription);
        taskStatus = (TextView) findViewById(R.id.taskStatus);

        Bundle extras = getIntent().getExtras();

        try {
            String taskID = extras.getString("TaskID");
            currentTask = dm.getTask(taskID);
            fillInformation();
        } catch (NullPointerException e) {
            Log.i("Error", "TaskID not properly passed");
        } catch (NoInternetException e){
            Log.i("Error", "No Connection in Details");
        }

        ImageView imageView = (ImageView) findViewById(R.id.myTasksImageView);
        if (currentTask.hasPhoto()) {
            imageView.setImageBitmap(currentTask.getCoverPhoto());
        }

        if(currentTask.getStatus() == TaskStatus.DONE) {
            editButton.setVisibility(View.GONE);
            viewBidsButton.setVisibility(View.GONE);
            setupDeleteButton();
        }
        else {
            editButton.setVisibility(View.VISIBLE);
            viewBidsButton.setVisibility(View.VISIBLE);
            setupDeleteButton();
            setUpEditButton();
            setupViewBidsButton();
        }
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
                                        try {
                                            NotificationHandler nh = new NotificationHandler(context);
                                            nh.newNotification(currentTask.getId(), NotificationType.TASK_DELETED);
                                            dm.deleteTask(currentTask);

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

    @Override
    protected void onResume() {
        super.onResume();
        if (!edit) {
            try {
                String taskID = getIntent().getExtras().getString("TaskID");
                currentTask = dm.getTask(taskID);
            } catch (NullPointerException e) {
                Log.i("Error", "TaskID not properly passed");
            } catch (NoInternetException e) {
                e.printStackTrace();
            }

            if (currentTask.getStatus() == TaskStatus.ASSIGNED) {
                finish();
            }
        }
        else {
            edit = false;
        }
    }

    private void setUpEditButton() {
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(currentTask.getStatus() != TaskStatus.REQUESTED) {
                    Toast.makeText(getApplicationContext(),"This task already has bids on it. This task can no longer be edited.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Intent editTask = new Intent(context, AddTaskActivity.class);
                    editTask.putExtra("task", currentTask);
                    edit = true;
                    startActivityForResult(editTask, 19);
                }
            }
        });
    }

    private void setupViewBidsButton() {
        viewBidsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(currentTask.getStatus() == TaskStatus.REQUESTED) {
                    Toast.makeText(getApplicationContext(),"This task is still requested and has no bids on it.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Context thiscontext = getApplicationContext();
                    Intent intent;
                    intent = new Intent(thiscontext, ViewBidsOnTaskActivity.class);
                    intent.putExtra("TaskID", currentTask.getId());
                    thiscontext.startActivity(intent);
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void fillInformation() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        taskName.setText(currentTask.getTaskName());
        taskDescription.setText(currentTask.getDescription());
        if (currentTask.getStatus() == TaskStatus.BIDDED) {
            String minBidAmount = df.format(currentTask.getMinBid());
            String taskStatusString = currentTask.getStatus().toString();
            taskStatus.setText(taskStatusString + ": Lowest bid of $" + minBidAmount);
        }
        else {
            taskStatus.setText(currentTask.getStatus().toString());
        }
        ImageView imageView = (ImageView) findViewById(R.id.myTasksImageView);
        if (currentTask.hasPhoto()) {
            imageView.setImageBitmap(currentTask.getCoverPhoto());
        }
        else {
            imageView.setImageResource(R.drawable.ic_menu_gallery);
        }
    }

    public void onPhotoClick(View view) {
        Intent viewPhotosIntent = new Intent(this, ViewPhotoActivity.class);
        viewPhotosIntent.putExtra("photos", currentTask.getByteArrays());
        startActivity(viewPhotosIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 19) {
            currentTask = (Task) data.getSerializableExtra("task");
            fillInformation();
        }
    }
}
