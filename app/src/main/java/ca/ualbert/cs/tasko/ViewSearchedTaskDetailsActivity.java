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
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;

import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationType;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class ViewSearchedTaskDetailsActivity extends RootActivity {

    private TextView taskDescription;
    private TextView taskName;
    private TextView lowestBid;
    private TextView status;
    private TextView taskAddress;
    private float lowbid = -1;
    private TextView requesterName;
    private Button placeBidButton;
    private Button getDirectionsButton;

    private DataManager dm = DataManager.getInstance();
    private Task currentTask;
    private BidList currentBidList;
    private final Context context = this;
    private User requesterUser;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searched_task_details);

        //Button and text boxes definitions
        requesterName = (TextView) findViewById(R.id.taskRequesterName);
        placeBidButton = (Button) findViewById(R.id.placeBidButton);
        getDirectionsButton = (Button) findViewById(R.id.getDirectionsButton);
        taskDescription = (TextView) findViewById(R.id.taskDescriptionView);
        taskName = (TextView) findViewById(R.id.taskName);
        lowestBid = (TextView) findViewById(R.id.lowestBid);
        status = (TextView) findViewById(R.id.ViewSearchedDetailsStatus);
        taskAddress = (TextView) findViewById(R.id.taskLocationText);

        Bundle extras = getIntent().getExtras();
        Bid bid;
        try {
            String taskID = extras.getString("TaskID");
            currentTask = dm.getTask(taskID);
            currentBidList = dm.getTaskBids(currentTask.getId());
            requesterUser = dm.getUserById(currentTask.getTaskRequesterID()
            );
            bid = currentBidList.getMinBid();
            if(bid != null){
                lowbid = bid.getValue();
            }
            populateFields();
        }catch (NoInternetException e) {
            e.printStackTrace();
        }

        ImageView imageView = (ImageView) findViewById(R.id.searchedTaskImageView);
        if (currentTask.hasPhoto()) {
            imageView.setImageBitmap(currentTask.getCoverPhoto());
        }
        else {
            imageView.setImageResource(R.drawable.ic_menu_gallery);
        }

        setupPlaceBidButton();
        setupGetDirectionsButton();
    }

    private void populateFields(){
        taskName.setText(currentTask.getTaskName());
        taskDescription.setText(currentTask.getDescription());
        status.setText(currentTask.getStatus().toString());
        latLng = currentTask.getGeolocation();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            try {
                taskAddress.setText(addresses.get(0).getAddressLine(0));
            } catch (Exception e){
                taskAddress.setText("No location provided");
            }

        } catch (Exception e) {
            taskAddress.setText("Could not find location");
        }
        if(lowbid == -1){
            lowestBid.setText(R.string.ViewSearchedTaskDetailsNoBids);
        } else {
            lowestBid.setText(getString(R.string.search_lowest_bid, lowbid));
        }
        requesterName.setText(requesterUser.getUsername());
    }

    //Dialog for choosing to make a bid on the task
    private void setupPlaceBidButton() {

        placeBidButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewSearchedTaskDetailsActivity.this);
                final View bidView = getLayoutInflater().inflate(R.layout.place_bid_dialog, null);
                final EditText bidAmount = (EditText) bidView.findViewById(R.id.bidAmount);
                bidAmount.setFilters(new InputFilter[] {new MoneyValueFilter()});

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
                                            placeBid(Float.valueOf(bidAmount.getText().toString()));
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

    private void setupGetDirectionsButton(){
        getDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( Math.abs(latLng.latitude) > 0.0000001  &&  Math.abs(latLng.longitude) > 0.0000001){
                    String uri = "http://maps.google.com/maps?daddr=" + latLng.latitude + "," + latLng.longitude;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"This task has no location", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void placeBid(float value){
        if(CurrentUser.getInstance().loggedIn()) {
            if(currentTask.getStatus() != TaskStatus.BIDDED) {
                currentTask.setStatus(TaskStatus.BIDDED);
            }
            if(value < lowbid || lowbid == -1) {
                lowbid = value;
                currentTask.setMinBid(value);
                populateFields();
                PlaceBidRunnable placeBid = new PlaceBidRunnable(value, currentTask,
                        getApplicationContext());
                new Thread(placeBid).start();
            }
            else if (currentBidList.getBid(CurrentUser.getInstance().getCurrentUser().getId()) != null) {
                //Place/Replace previous bid
                PlaceBidRunnable placeBid = new PlaceBidRunnable(value, currentTask,
                        getApplicationContext());
                //new Thread(placeBid).start();
                placeBid.run();
                BidList taskBids;
                float lowestBidValue;
                //Go through all bids on task and find the lowest bid and set it to the minbid value
                try {
                    taskBids = dm.getTaskBids(currentTask.getId());
                    lowestBidValue = taskBids.get(0).getValue();
                    for (int i = 0; i < taskBids.getSize(); i++) {
                        if (taskBids.get(i).getValue() < lowestBidValue) {
                            lowestBidValue = taskBids.get(i).getValue();
                        }
                    }
                    lowbid = lowestBidValue;
                    currentTask.setMinBid(lowestBidValue);
                    dm.putTask(currentTask);
                    populateFields();
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }
            }
            else {
                PlaceBidRunnable placeBid = new PlaceBidRunnable(value, currentTask,
                        getApplicationContext());
                new Thread(placeBid).start();
            }

        } else {
            Toast.makeText(getApplicationContext(),"Not Logged In", Toast.LENGTH_SHORT).show();
        }
    }

    public void onPhotoClick(View view) {
        Intent viewPhotosIntent = new Intent(this, ViewPhotoActivity.class);
        viewPhotosIntent.putExtra("photos", currentTask.getByteArrays());
        startActivity(viewPhotosIntent);
    }

    public void onNameClick(View view) {
        Intent viewUserDetails = new Intent(this, OtherUsersProfileActivity.class);
        viewUserDetails.putExtra("id", requesterUser.getId());
        startActivity(viewUserDetails);
    }

    class PlaceBidRunnable implements Runnable{
        private User currentUser = CurrentUser.getInstance().getCurrentUser();
        private DataManager dm = DataManager.getInstance();
        private float value;
        private Context appContext;
        private Task currentTask;

        public PlaceBidRunnable(float value, Task currentTask, Context appContext){
            this.value = value;
            this.currentTask = currentTask;
            this.appContext = appContext;
        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            try {
                BidList possibleCurrentBids = dm.getUserBids(
                        CurrentUser.getInstance().getCurrentUser().getId()
                );
                List<Bid> bids = possibleCurrentBids.getBids();
                Bid bid = new Bid(CurrentUser.getInstance()
                        .getCurrentUser().getId(), value, currentTask.getId());
                for (Bid currentBid : bids) {
                    if (currentBid.getTaskID().compareTo(currentTask.getId()) == 0) {
                        bid = currentBid;
                        bid.setValue(value);
                        break;
                    }
                }
                dm.addBid(bid);
                dm.putTask(currentTask);
                NotificationHandler nh = new NotificationHandler(getApplicationContext());
                nh.newNotification(currentTask.getId(), NotificationType.TASK_REQUESTER_RECEIVED_BID_ON_TASK);

            } catch (NoInternetException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(appContext, "No connection.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    /*
    Retrieved March 18, 2018
    https://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext
    solution from Konstantin Weitz
     */
    private class MoneyValueFilter extends DigitsKeyListener {
        public MoneyValueFilter() {
            super(false, true);
        }

        private int digits = 2;

        public void setDigits(int d) {
            digits = d;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            CharSequence out = super.filter(source, start, end, dest, dstart, dend);

            // if changed, replace the source
            if (out != null) {
                source = out;
                start = 0;
                end = out.length();
            }

            int len = end - start;

            // if deleting, source is empty
            // and deleting can't break anything
            if (len == 0) {
                return source;
            }

            int dlen = dest.length();

            // Find the position of the decimal .
            for (int i = 0; i < dstart; i++) {
                if (dest.charAt(i) == '.') {
                    // being here means, that a number has
                    // been inserted after the dot
                    // check if the amount of digits is right
                    return (dlen-(i+1) + len > digits) ?
                            "" :
                            new SpannableStringBuilder(source, start, end);
                }
            }

            for (int i = start; i < end; ++i) {
                if (source.charAt(i) == '.') {
                    // being here means, dot has been inserted
                    // check if the amount of digits is right
                    if ((dlen-dend) + (end-(i + 1)) > digits)
                        return "";
                    else
                        break;  // return new SpannableStringBuilder(source, start, end);
                }
            }

            // if the dot is after the inserted part,
            // nothing can break
            return new SpannableStringBuilder(source, start, end);
        }
    }
}


