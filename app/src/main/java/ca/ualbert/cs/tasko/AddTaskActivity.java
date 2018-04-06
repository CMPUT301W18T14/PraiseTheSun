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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Activity that adds a task. Is called either from the MainActivity when the user selects "Post
 * a Task" or from the menu bar.
 *
 * @author tlafranc
 */
public class AddTaskActivity extends AppCompatActivity {
    private EditText taskNameText;
    private EditText descriptionText;
    private String taskName;
    private String description;
    private User taskRequester;
    private LatLng geoLocation = null;
    private ArrayList<String> photos;
    private ArrayList<Bitmap> images;
    private ImageSwitcher switcher;
    private ImageView imageView;
    private TextView textView;
    private int numImages;
    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    /**
     * Called when the activity is started. Initializes the taskNameText and descriptionText.
     * Uses the CurrentUser singleton class in order to determine the current user who is posting
     * a task.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskNameText = (EditText) findViewById(R.id.addTaskName);
        descriptionText = (EditText) findViewById(R.id.addTaskDescription);
        taskRequester = CurrentUser.getInstance().getCurrentUser();
        images = new ArrayList<Bitmap>();
        imageView = (ImageView) findViewById(R.id.addTaskImageView);
        switcher = (ImageSwitcher) findViewById(R.id.addTaskImageSwitcher);
        textView = (TextView) findViewById(R.id.addTaskTextView);

        /*
         * https://stackoverflow.com/questions/15799839/motionevent-action-up-not-called
         * http://codetheory.in/android-ontouchevent-ontouchlistener-motionevent-to-detect-common-gestures/
         * https://developer.android.com/training/gestures/detector.html
         *
         */
        switcher.setOnTouchListener(new View.OnTouchListener() {
            private float initialX;
            private int position = 0;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //Log.i("Not Error", Integer.toString(numImages));
                if (numImages > 0) {
                    float finalX;
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = event.getX();
                            return true;
                        case MotionEvent.ACTION_UP:
                            finalX = event.getX();
                            if (initialX > finalX) {
                                if (position < (numImages - 1)) {
                                    position++;
                                    imageView.setImageBitmap(images.get(position));
                                    switcher.showNext();
                                    textView.setText("Swipe to view other photos.\n Viewing photo" +
                                            " " + Integer.toString(position + 1) + "/" + Integer
                                            .toString(numImages));
                                } else {
                                    Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                if (position > 0) {
                                    position--;
                                    imageView.setImageBitmap(images.get(position));
                                    switcher.showNext();
                                    switcher.showPrevious();
                                    textView.setText("Swipe to view other photos.\n Viewing photo" +
                                            " " + Integer.toString(position + 1) + "/" + Integer
                                            .toString(numImages));
                                } else {
                                    Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                            break;
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
        });
    }

    /**
     * Called when the user clicks on the "Add Photo" button in this activity. Calls the
     * AddPhotoActivity.
     *
     */
    public void onAddPhotoClick(View view){
        // Create an Intent to AddPhotoActivity
        Intent addPhotoIntent = new Intent(this, AddPhotoActivity.class);
        final int result = 19;
        addPhotoIntent.putExtra("photos", photos);
        startActivityForResult(addPhotoIntent, result);
    }

    /**
     * Called when the user clicks on the "Add Location" button in this activity. Calls the
     * AddLocationActivity.
     *
     */
    public void onAddLocationClick(View view){
        // Create an Intent to AddLocationActivity
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        Intent addLocationIntent = new Intent(this, SelectLocationActivity.class);
        final int result = 2;
        startActivityForResult(addLocationIntent, result);
        onActivityResult(result, result, addLocationIntent);
        } else {
            Toast.makeText(this, "Location services disabled", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called upon completion of AddPhotoActivity or AddLocationActivity. If the result is coming
     * from AddPhotoActivity, retrieve the bytes sent by the AddPhotoActivity and turn them into
     * a bitmap object and append them to the local photos instance variable. If the result is
     * coming from AddLocationActivity then ... (not implemented yet)
     *
     * @param requestCode Code created when creating intent for activity
     * @param resultCode Code sent from called activity indicating if task was completed
     * @param data Information sent back from activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 19:
                    photos = data.getStringArrayListExtra("photos");
                    images = new ArrayList<Bitmap>();
                    if (photos.size() > 0) {
                        numImages = photos.size();
                        for (int i = 0; i < numImages; i++) {
                            byte[] byteArray = Base64.decode(photos.get(i), Base64.DEFAULT);
                            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            images.add(image);
                        }
                        imageView.setImageBitmap(images.get(0));
                        textView.setText("Swipe to view other photos.\n Viewing photo 1" + "/" + Integer
                                .toString(numImages));
                    }
                    break;
                case 2:
                    // Handle add location Intent result

                    double lat = data.getDoubleExtra("lat", 0.00);
                    double lng = data.getDoubleExtra("lng", 0.00);
                    geoLocation = new LatLng(lat, lng);
                    Log.i("LocationAdded", "lat, lng " + Double.toString(lat) + ", " + Double.toString(lng) );
                    break;
            }
        }
    }

    /**
     * Called when the user clicks on the "Post your task!" button in this activity. Creates a
     * task object from the fields taskNameText and descriptionText and then tries to store this
     * information in the database.
     *
     */
    public void onAddTaskClick(View view){
        boolean valid = checkFieldsForEmptyValues();
        if (valid){
            if (!CurrentUser.getInstance().loggedIn()) {
                Toast.makeText(this.getApplicationContext(), "No user is currently logged " +
                        "in, please log out and sign in again.", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
            Task newTask = new Task(taskRequester.getId(), taskName, description, photos, geoLocation);
            newTask.setTaskRequesterUsername(taskRequester.getUsername());
            try {
                DataManager.getInstance().putTask(newTask, this.getApplicationContext());
                Log.i("Task location: ", "lat, lng " + Double.toString(newTask.getGeolocation().latitude) + ", " + Double.toString(newTask.getGeolocation().longitude) );
                finish();
            } catch (NoInternetException e) {
                Log.i("Error", "No internet connection in CreateAccountActivity");
                Toast.makeText(this.getApplicationContext(), "No Internet Connection!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Function that determines if there are any blank fields for name or description as both of
     * these fields must be provided.
     *
     * @return returns a boolean. Returns false if there is at either name or description are
     * blank and true otherwise.
     */
    private boolean checkFieldsForEmptyValues(){
        taskName = taskNameText.getText().toString();
        description = descriptionText.getText().toString();
        boolean validInputs = true;

        if (taskName.trim().equals("")){
            taskNameText.setError("Task name cannot be left blank");
            validInputs = false;
        }
        if (description.trim().equals("")){
            descriptionText.setError("Description cannot be left blank");
            validInputs = false;
        }

        return validInputs;
    }
}
