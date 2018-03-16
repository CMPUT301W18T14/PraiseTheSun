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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewSearchedTaskDetailsActivity extends AppCompatActivity {
    private TextView taskDescription;
    private TextView taskName;
    private TextView lowestBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searched_task_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Button and text boxes definitions
        Button placeBidButton = (Button)findViewById(R.id.placeBidButton);
        Button geolocationButton = (Button)findViewById(R.id.geolocationButton);
        taskDescription = (TextView) findViewById(R.id.taskDescription);
        taskName = (TextView) findViewById(R.id.taskName);
        lowestBid = (TextView) findViewById(R.id.lowestBid);

        //Dialog for choosing to make a bid on the task
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


