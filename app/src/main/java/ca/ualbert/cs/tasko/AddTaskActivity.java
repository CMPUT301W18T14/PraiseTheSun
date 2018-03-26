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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    private LinearLayout addTaskImagesLayout;
    private ArrayList<String> photos;
    private Location geoLocation = null;

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

        addTaskImagesLayout = (LinearLayout) findViewById(R.id.addTaskImagesLayout);
        taskNameText = (EditText) findViewById(R.id.addTaskName);
        descriptionText = (EditText) findViewById(R.id.addTaskDescription);
        taskRequester = CurrentUser.getInstance().getCurrentUser();
        photos = new ArrayList<String>();
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
        /*
        Intent addLocationIntent = new Intent(this, AddLocationActivity.class);
        final int result = 1;
        startActivityForResult(addLocationIntent, result);
         */
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
                    for (int i = 0; i < photos.size(); i++) {
                        byte[] byteArray = Base64.decode(photos.get(i), Base64.DEFAULT);
                        Bitmap image = BitmapFactory.decodeByteArray(byteArray,0, byteArray
                                .length);
                        ImageView imageView = new ImageView(this);
                        imageView.requestLayout();
                        // https://stackoverflow.com/questions/36340268/nullpointerexception-while-setting-layoutparams
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup
                                .LayoutParams.WRAP_CONTENT, 200);
                        imageView.setLayoutParams(params);
                        imageView.setImageBitmap(image);
                        addTaskImagesLayout.addView(imageView);
                    }
                    break;
                case 2:
                    // Handle add location Intent result
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
            Task newTask = new Task(taskRequester.getId(), taskName, description, photos);
            try {
                DataManager.getInstance().putTask(newTask, this.getApplicationContext());
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
