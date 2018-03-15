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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldentan1997 on 2018-03-12.
 */

    //TODO: MAKE TEST FOR THIS AND TEST FOR APPROPRIATE XML FILES!

public class ViewBidsOnTaskActivity extends AppCompatActivity {
    public ListView myBidList;

    //TODO: get bidlists involved in code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids_on_a_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myBidList = (ListView) findViewById(R.id.bidListView);

        //Populating za listview with some stuffzzzzz

        // Instanciating an array list. For testing purposes, obviously will have a bidlist
        final List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");

        // This is the array adapter.
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        //setting the adapter
        myBidList.setAdapter(arrayAdapter);

        //end of listview population codezzzzz

        //start of codez for being able to select a bid and either accept it or reject it

        //OnItemClickListener
        myBidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //builds the dialog box when you click on a bid
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewBidsOnTaskActivity.this);
                final View acceptOrRejectView = getLayoutInflater().inflate(R.layout.accept_or_reject_bid_dialog, null);

                //buttons on the dialog box
                Button acceptButton = (Button) acceptOrRejectView.findViewById(R.id.acceptButton);
                Button rejectButton = (Button) acceptOrRejectView.findViewById(R.id.rejectButton);

                //reject button clicked
                //TODO: implement the actual rejecting of a bid
                rejectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //just for testing, this will all be different obviously
                        your_array_list.add("rejected");
                        arrayAdapter.notifyDataSetChanged();
                        finish();
                        startActivity(new Intent(ViewBidsOnTaskActivity.this, ViewBidsOnTaskActivity.class));
                    }
                });

                //accept button clicked
                //TODO: implement the actual rejecting of a bid
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //just for testing, this will all be different obviously
                        your_array_list.add("accepted");
                        arrayAdapter.notifyDataSetChanged();
                        finish();
                        startActivity(new Intent(ViewBidsOnTaskActivity.this, ViewBidsOnTaskActivity.class));
                    }
                });

                //build and show popup
                builder.setView(acceptOrRejectView);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //end of accept/reject bid codezzzzassss

        //Not sure what this is for - apparently no use, just comes with the default xml layout
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    //Taken from ViewMyTasksActivity
    //Find out what they do and whether this activity needs it
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}