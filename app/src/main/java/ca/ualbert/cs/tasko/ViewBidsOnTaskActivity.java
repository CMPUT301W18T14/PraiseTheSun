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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by Aldentan1997 on 2018-03-12.
 */

    //TODO: MAKE TEST FOR THIS AND TEST FOR APPROPRIATE XML FILES!

public class ViewBidsOnTaskActivity extends AppCompatActivity {
    public RecyclerView myBidList;
    private BidList bidsOnTask;
    private Task currentTask;
    private DataManager dm = DataManager.getInstance();
    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;
    private RecyclerView.LayoutManager searchLayoutManager;
    private ViewBidsOnTaskActivity activity = this;

    //TODO: get bidlists involved in code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids_on_a_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myBidList = (RecyclerView) findViewById(R.id.bidListRecyclerView);

        searchLayoutManager = new LinearLayoutManager(activity);
        myBidList.setLayoutManager(searchLayoutManager);

        //Populating za listview with some stuffzzzzz

        // Instanciating an array list. For testing purposes, obviously will have a bidlist
        //final List<String> your_array_list = new ArrayList<String>();
        //your_array_list.add("foo");
        //your_array_list.add("bar");

        //test datazzzzz
        //Bid testBid = new Bid("testid", 5.7f, "testtaskid");

        //bidsOnTask.addBid(testBid);



        //end of test data

        Bundle extras = getIntent().getExtras();



        /*
        //gets bidlist for the current task that was selected
        try {
            String taskID = extras.getString("TaskID");
            bidsOnTask = dm.getTaskBids(taskID, this);
        }catch(NullPointerException e){
            Log.i("Error", "TaskID from TaskListAdapter not properly passed");
        } catch (NoInternetException e) {
            e.printStackTrace();
        } */


        bidsOnTask = new BidList();

        bidsOnTask.addBid(new Bid("testid", 5.7f, "testtaskid"));
        bidsOnTask.addBid(new Bid("testid2", 7.8f, "testtaskid2"));


        //gets the bidlist from task
        //bidsOnTask = currentTask.getBids();

        searchAdapter = new ViewBidsAdapter(activity, bidsOnTask);
        myBidList.setAdapter(searchAdapter);
        //searchRecyclerView.setAdapter(searchAdapter);

        //final List<BidList> bidsOnTask = new ArrayList<BidList>();

        /*
        // This is the array adapter.
        final ArrayAdapter<BidList> arrayAdapter = new ArrayAdapter<BidList>(
                this,
                android.R.layout.simple_list_item_1,
                (List<BidList>) bidsOnTask);



        //setting the adapter
        myBidList.setAdapter(arrayAdapter);

        //end of listview population codezzzzz

        */


        /*
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
                        //your_array_list.add("rejected");
                        //bidsOnTask.add("rejected");
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
                        //your_array_list.add("accepted");
                        //bidsOnTask.add("accepted");
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

        */

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