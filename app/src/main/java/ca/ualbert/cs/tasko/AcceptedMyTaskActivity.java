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
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button and TextView definitions

        completedButton = (Button) findViewById(R.id.taskCompleteButton);
        repostButton = (Button) findViewById(R.id.repostButton);
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
                assignedCurrentTask.setStatus(Status.DONE);
                finish();
            }
        });
    }

    private void setupRepostButton() {
        repostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
                //Remove currently set bid and repost the task
                //Unhide the previous bids that were on the task and notify the
            }
        });
    }
}
