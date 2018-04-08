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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class RatingActivity extends AppCompatActivity {

    TextView instructions;
    RatingBar ratingbar;
    Button submitButton;
    DataManager dm = DataManager.getInstance();
    CurrentUser cu = CurrentUser.getInstance();
    Task currentTask;
    User RatingRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        instructions = (TextView) findViewById(R.id.Rating_Activity_Instruction_textview);
        ratingbar = (RatingBar) findViewById(R.id.Rating_Activity_Rating_Bar);
        submitButton = (Button) findViewById(R.id.Rating_Activity_Submit_Button);

        discernRecipient();
        setupSubmitButton();

    }

    @SuppressLint("SetTextI18n")
    private void discernRecipient(){

        Bundle extras = getIntent().getExtras();
        String taskID = extras.getString("TaskID");

        try {
            currentTask = dm.getTask(taskID);
            if (currentTask.getTaskRequesterUsername().equals(cu.getCurrentUser().getUsername())) {
                RatingRecipient = dm.getUserById(currentTask.getTaskProviderID());
            } else {
                RatingRecipient = dm.getUserById(currentTask.getTaskRequesterID());
            }
            instructions.setText("Please rate " + RatingRecipient.getUsername());
        } catch (NoInternetException e) {
            e.printStackTrace();
        }
    }

    private void setupSubmitButton(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingRecipient.addRating(ratingbar.getRating());
                try {
                    dm.putUser(RatingRecipient);
                } catch (NoInternetException e){
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
