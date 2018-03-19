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
import android.widget.EditText;
import android.widget.TextView;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class ViewSearchedTaskDetailsActivity extends RootActivity {

    private TextView taskDescription;
    private TextView taskName;
    private TextView lowestBid;
    private Button placeBidButton;
    private Button geolocationButton;
    private DataManager dm = DataManager.getInstance();
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searched_task_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button and text boxes definitions
        placeBidButton = (Button) findViewById(R.id.placeBidButton);
        geolocationButton = (Button) findViewById(R.id.geolocationButton);
        taskDescription = (TextView) findViewById(R.id.taskDescription);
        taskName = (TextView) findViewById(R.id.taskName);
        lowestBid = (TextView) findViewById(R.id.lowestBid);

        //Dialog for choosing to make a bid on the task


        Bundle extras = getIntent().getExtras();

        try {
            String taskID = extras.getString("TaskID");
            currentTask = dm.getTask(taskID, this);
            populateFields();
        }catch(NullPointerException e){
            Log.i("Error", "TaskID from TaskListAdapter not properly passed");
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        setupPlaceBidButton();

    }

    private void populateFields(){
        taskName.setText(currentTask.getTaskName());
        taskDescription.setText(currentTask.getDescription());
    }

    //Dialog for choosing to make a bid on the task
    private void setupPlaceBidButton() {

        placeBidButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Confirm deletion and return to main page
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewSearchedTaskDetailsActivity.this);
                final View bidView = getLayoutInflater().inflate(R.layout.place_bid_dialog, null);
                EditText bidAmount = (EditText) bidView.findViewById(R.id.bidAmount);
                Button confirmButton = (Button) bidView.findViewById(R.id.confirmButton);
                Button cancelButton = (Button) bidView.findViewById(R.id.cancelButton);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //CONFIRMATION OF BID PLACED
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Probably a better way to do this but it works for now
                        finish();
                        startActivity(new Intent(ViewSearchedTaskDetailsActivity.this, ViewSearchedTaskDetailsActivity.class));
                    }
                });

                builder.setView(bidView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

}
