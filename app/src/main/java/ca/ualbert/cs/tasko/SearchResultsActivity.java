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
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * SearchResultsActivity works with TaskListAdapter to generate a recyclerview of tasks from the
 * database that match the users keyword search provided in mainactivity.
 * @see TaskListAdapter
 *
 * @author spack
 */
public class SearchResultsActivity extends RootActivity {

    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;
    private RecyclerView.LayoutManager searchLayoutManager;
    private DataManager dm = DataManager.getInstance();
    private ProgressBar loadingCircle;
    private SearchResultsActivity context = this;
    private TaskList foundtasks;
    private ArrayList<String> foundtasksUsers;

    /**
     * Creates the Activity which includes initializing the RecyclerView with the appropriate
     * adapter and using the adapter to fill the recyclerView with tasks returned by querying the
     * datamanager.
     * @param savedInstanceState Get the saved state form the current device.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_view_tasks_bidded_on, null, false);
        drawerLayout.addView(contentView, 0);

        searchRecyclerView = (RecyclerView) findViewById(R.id.generic_recyclerview);

        searchLayoutManager = new LinearLayoutManager(context);
        searchRecyclerView.setLayoutManager(searchLayoutManager);

        searchForTasks();

        //Initialize the Adapter and RecyclerView
        searchAdapter = new TaskListAdapter(context, foundtasks, getTaskUsers());
        searchRecyclerView.setAdapter(searchAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchForTasks();
        searchAdapter = new TaskListAdapter(context, foundtasks, getTaskUsers());
        searchRecyclerView.setAdapter(searchAdapter);
    }

    private void searchForTasks() {
        String keywords;

        Bundle extras = getIntent().getExtras();

        //Initializes the keywords for the search, obtained from MainActivity.
        if (extras != null) {
            keywords = extras.getString("SearchKeywords");
        } else {
            keywords = null;
        }

        foundtasks = new TaskList();

        //Try to conduct the search
        try {
            foundtasks = dm.searchTasks(keywords);
        } catch (NoInternetException e) {
            Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private Map<String, User> getTaskUsers(){

        int i;

        foundtasksUsers = new ArrayList<>();

        for(i = 0; i < foundtasks.getSize(); i++){
            foundtasksUsers.add(foundtasks.get(i).getTaskRequesterID());
        }
        try {
            Map<String, User> results = dm.getUserMap(foundtasksUsers);
            return results;
        } catch (NoInternetException e){
            Log.i("Error", "Could not get preferred users. no internet");
        }
        return new HashMap<String, User>();
    }
}


