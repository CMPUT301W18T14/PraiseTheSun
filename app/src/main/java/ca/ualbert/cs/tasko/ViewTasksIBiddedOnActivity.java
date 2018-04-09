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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.Toast;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * This activity allows the user to view all of the tasks they have bidded on, Which will show up
 * as a RecyclerView of tasks that includes all relevant information include your bid on the task.
 *
 * @author spack
 */
public class ViewTasksIBiddedOnActivity extends RootActivity {

    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter tasksBiddedAdapter;
    private RecyclerView.LayoutManager searchLayoutManager;
    private DataManager dm = DataManager.getInstance();
    private ViewTasksIBiddedOnActivity context = this;
    private ProgressBar loadingCircle;
    private User User;
    private BidList userBids;
    private TaskList biddedTasks;

    /**
     * Creates the activity by setting the recyclerview.
     * @param savedInstanceState Get the saved state form the current device.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User = CurrentUser.getInstance().getCurrentUser();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_view_tasks_bidded_on, null, false);
        drawerLayout.addView(contentView, 0);

        searchRecyclerView = (RecyclerView) findViewById(R.id.generic_recyclerview);
        searchLayoutManager = new LinearLayoutManager(context);
        loadingCircle = (ProgressBar) findViewById(R.id.taskIBiddedOnProgressBar);
        final ViewStub emptyListMessage = (ViewStub) findViewById(R.id.emptyListMessage);
        emptyListMessage.setLayoutResource(R.layout.empty_task_list);

        loadingCircle.setVisibility(View.VISIBLE);

        searchRecyclerView.setLayoutManager(searchLayoutManager);


        getTasks();

        if (userBids.getSize() == 0) {
            emptyListMessage.setVisibility(View.VISIBLE);
        }
        else {
            emptyListMessage.setVisibility(View.GONE);
        }

        tasksBiddedAdapter = new TaskListAdapter(context, biddedTasks, userBids);
        searchRecyclerView.setAdapter(tasksBiddedAdapter);

        loadingCircle.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getTasks();
        tasksBiddedAdapter = new TaskListAdapter(context, biddedTasks, userBids);
        searchRecyclerView.setAdapter(tasksBiddedAdapter);
    }

    /**
     * Gets all tasks a user has bidded on, which will be displayed in the RecyclerView. Does
     * Not include Tasks in which your Bid has been
     */
    private void getTasks(){

        try {
            userBids = dm.getUserBids(User.getId());
            biddedTasks = new TaskList();
            for (int i = 0; i < userBids.getSize(); i++)
                if (userBids.get(i).getStatus()!= BidStatus.REJECTED) {
                    Task task = dm.getTask(userBids.get(i).getTaskID());
                    if(task == null)
                        continue;
                    biddedTasks.addTask(task);
                }
        } catch (NoInternetException e) {
            Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();

        }
    }

}
