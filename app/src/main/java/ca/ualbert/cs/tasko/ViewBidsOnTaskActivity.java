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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by Aldentan1997 on 2018-03-12.
 * Activity to view a list of bids on a particular task when the button "view bids" is
 * clicked on the "ViewTaskDetailsActivity"
 *
 * @author Alden Tan
 * @see ViewMyTasksActivity
 */

public class ViewBidsOnTaskActivity extends AppCompatActivity {
    public RecyclerView myBidList;
    private BidList bidsOnTask;
    private DataManager dm = DataManager.getInstance();
    private RecyclerView.Adapter searchAdapter;
    private RecyclerView.LayoutManager searchLayoutManager;
    private ViewBidsOnTaskActivity activity = this;

    /**
     * Creates the activity by setting the recyclerview.
     * @param savedInstanceState Get the saved state form the current device.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids_on_a_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myBidList = (RecyclerView) findViewById(R.id.bidListRecyclerView);

        searchLayoutManager = new LinearLayoutManager(activity);
        myBidList.setLayoutManager(searchLayoutManager);

        Bundle extras = getIntent().getExtras();

        bidsOnTask = new BidList();

        //gets bidlist for the current task that was selected
        try {
            String taskID = extras.getString("TaskID");
            bidsOnTask = dm.getTaskBids(taskID);
        } catch (NullPointerException e) {
            Log.i("Error", "Failed to get bid list properly");
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        searchAdapter = new ViewBidsAdapter(activity, bidsOnTask);
        myBidList.setAdapter(searchAdapter);
        
    }

}