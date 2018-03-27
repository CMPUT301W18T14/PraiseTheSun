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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * SearchResultsActivity works with TaskListAdapter to generate a recyclerview of tasks from the
 * database that match the users keyword search provided in mainactivy.
 * @see TaskListAdapter
 *
 * @author spack
 */
public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;
    private RecyclerView.LayoutManager searchLayoutManager;
    private DataManager dm = DataManager.getInstance();
    private SearchResultsActivity context = this;
    private TaskList foundtasks;

    /**
     * Creates the Activity which includes initializing the RecyclerView with the appropriate
     * adapter and using the adapter to fill tasks returned by querying the datamanager.
     *
     * @param savedInstanceState Get the saved state form the current device.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchRecyclerView = (RecyclerView) findViewById(R.id.search_task_recycler_view);
        searchLayoutManager = new LinearLayoutManager(context);
        searchRecyclerView.setLayoutManager(searchLayoutManager);

        searchForTasks();

        //Initialize the Adapter and RecyclerView
        searchAdapter = new TaskListAdapter(context, foundtasks);
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
            foundtasks = dm.searchTasks(keywords, context);
        } catch (NoInternetException e) {
            Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
        }

    }
}


