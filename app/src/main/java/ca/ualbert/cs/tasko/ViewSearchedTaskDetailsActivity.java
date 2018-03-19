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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class ViewSearchedTaskDetailsActivity extends RootActivity {

    private TextView taskDescription;
    private TextView taskName;
    private TextView lowestBid;
    private float lowbid = -1;
    private TextView requesterName;
    private Button placeBidButton;
    //private Button geolocationButton;
    private DataManager dm = DataManager.getInstance();
    private Task currentTask;
    private final Context context = this;
    private User requesterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searched_task_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Button and text boxes definitions
        requesterName = (TextView) findViewById(R.id.taskRequesterName);
        placeBidButton = (Button) findViewById(R.id.placeBidButton);
        //geolocationButton = (Button) findViewById(R.id.geolocationButton);
        taskDescription = (TextView) findViewById(R.id.taskDescriptionView);
        taskName = (TextView) findViewById(R.id.taskName);
        lowestBid = (TextView) findViewById(R.id.lowestBid);

        Bundle extras = getIntent().getExtras();

        try {
            String taskID = extras.getString("TaskID");
            currentTask = dm.getTask(taskID, this);
            requesterUser = dm.getUserById(currentTask.getTaskRequesterID(),
                    getApplicationContext());
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
        /*
        try {
            requesterName.setText(dm.getUserById(currentTask.getTaskRequesterID(),
                    getApplicationContext()).getUsername());
        } catch (NoInternetException e){
            requesterName.setText("unknown user");
        }/**/

        requesterName.setText(requesterUser.getUsername());
        Bid bid;
        try{
            BidList bids = dm.getTaskBids(currentTask.getId(), getApplicationContext());
            bid = bids.getMinBid();
            if(bid != null){
                lowbid = bid.getValue();
                lowestBid.setText(getString(R.string.search_lowest_bid, lowbid));
            }
        } catch (NoInternetException e){
            Toast t = new Toast(this);
            t.setText("No Connection");
            t.show();
        }
    }

    //Dialog for choosing to make a bid on the task
    private void setupPlaceBidButton() {
        placeBidButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Confirm deletion and return to main page
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewSearchedTaskDetailsActivity.this);
                final View bidView = getLayoutInflater().inflate(R.layout.place_bid_dialog, null);
                final EditText bidAmount = (EditText) bidView.findViewById(R.id.bidAmount);

                builder.setView(bidView).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*
                        Taken March 16, 2018
                        https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
                        Response from user: Mahesh
                         */
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Are you sure you want to make a bid?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(bidAmount.getText().length() <= 0){
                                            bidAmount.setError("Must have a non-empty Bid!");
                                        } else {
                                            placeBid(Float.valueOf(bidAmount.getText().toString()), currentTask);
                                            dialog.cancel();
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
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void placeBid(float value, Task task){
        if(CurrentUser.getInstance().loggedIn()) {
            Bid bid = new Bid(CurrentUser.getInstance()
                    .getCurrentUser().getId(), value, currentTask.getId());
            try{
                dm.addBid(bid, getApplicationContext());
                if(value < lowbid || lowbid == -1){
                    lowbid = value;
                    lowestBid.setText(getString(R.string.search_lowest_bid, lowbid));
                }
            } catch (NoInternetException e){
                Toast t = new Toast(getApplicationContext());
                t.setText("No connection");
                t.show();
            }
        } else {
            Toast t = new Toast(getApplicationContext());
            t.setText("Not Logged In");
            t.show();
        }
    }
}
