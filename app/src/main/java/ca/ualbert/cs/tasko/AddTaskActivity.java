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
import android.location.Location;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskNameText;
    private EditText descriptionText;
    private String taskName;
    private String description;
    private User taskRequester;
    private ArrayList<Image> photos = null;
    private Location geoLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskNameText = (EditText) findViewById(R.id.addTaskName);
        descriptionText = (EditText) findViewById(R.id.addTaskDescription);
    }

    public void onAddPhotoClick(View view){
        // Create an Intent to AddPhotoActivity
        /*
        Intent addPhotoIntent = new Intent(this, AddPhotoActivity.class);
        final int result = 1;
        startActivityForResult(addPhotoIntent, result);
         */
    }

    public void onAddLocationClick(View view){
        // Create an Intent to AddLocationActivity
        /*
        Intent addLocationIntent = new Intent(this, AddLocationActivity.class);
        final int result = 1;
        startActivityForResult(addLocationIntent, result);
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            switch (requestCode) {
                case 1:
                    // Handle add photo Intent result
                    break;
                case 2:
                    // Handle add location Intent result
                    break;
            }
        }
    }

    public void onAddTaskClick(View view){
        boolean valid = checkFieldsForEmptyValues();
        if (valid){
            Task newTask = new Task(taskRequester, taskName, description, photos, geoLocation);
            // This relies on Chases DataManager class. Subject to change in future.
            // DataManager.getInstance().putTask(newTask);
        }
    }

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
