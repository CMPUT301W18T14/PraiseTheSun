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
import android.widget.TextView;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;
    private RecyclerView.LayoutManager searchLayoutManager;
    private DataManager dm = DataManager.getInstance();
    private SearchResultsActivity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchRecyclerView = (RecyclerView) findViewById(R.id.search_task_recycler_view);

        searchLayoutManager = new LinearLayoutManager(activity);
        searchRecyclerView.setLayoutManager(searchLayoutManager);

        // searchAdapter = new MyTaskRecyclerViewAdapter()

        String keywords;

        Bundle extras = getIntent().getExtras();

        keywords = extras.getString("INeedIt");

        TaskList foundtasks = new TaskList();

        try {
            foundtasks = dm.searchTasks(keywords, activity);
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

    }

}
