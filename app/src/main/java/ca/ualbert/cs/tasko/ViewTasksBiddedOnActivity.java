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

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class ViewTasksBiddedOnActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;
    private RecyclerView.LayoutManager searchLayoutManager;
    private DataManager dm = DataManager.getInstance();
    private ViewTasksBiddedOnActivity activity = this;
    private CurrentUser cu  = CurrentUser.getInstance();
    private User user;
    private BidList userBids;
    private TaskList biddedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks_bidded_on);

        searchRecyclerView = (RecyclerView) findViewById(R.id.search_task_recycler_view);
        searchLayoutManager = new LinearLayoutManager(activity);
        searchRecyclerView.setLayoutManager(searchLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = cu.getCurrentUser();
//        try {
//            user = dm.getUserByUsername("rromano", activity);
//        } catch (NoInternetException e) {
//            e.printStackTrace();
//        }
        userBids = new BidList();

        try {
            userBids = dm.getUserBids(user.getId(), activity);
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        biddedTasks = new TaskList();
        for (int i = 0; i < userBids.getSize(); i++)
            try {
                biddedTasks.addTask(dm.getTask(userBids.get(i).getTaskID(), activity));
            } catch (NoInternetException e) {
                e.printStackTrace();
            }


        searchAdapter = new TaskListAdapter(activity, biddedTasks);
        searchRecyclerView.setAdapter(searchAdapter);
    }
}
