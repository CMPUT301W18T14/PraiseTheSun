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
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple modification to the search adapter that displays the task you have bidded on, aswell
 * as the bid you have made on said task. Please refer to TaskListAdapter for full documentation.
 * @see TaskListAdapter
 *
 * @author spack
 */
public class TaskBiddedAdapter extends RecyclerView.Adapter<TaskBiddedAdapter.TaskViewHolder> {

    private LayoutInflater inflater;
    private TaskList tasks;
    private BidList myBids;
    private Context thiscontext;

    public TaskBiddedAdapter(Context context, TaskList dmTasks, BidList dmBids) {
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        tasks = dmTasks;
        myBids = dmBids;
    }

    /**
     * Uses a slightly different layout than regular Task List Adapter, includes a TextView to
     * display the Current Users Bid on a Task.
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bidded_task_layout, parent, false);
        TaskBiddedAdapter.TaskViewHolder holder = new TaskBiddedAdapter.TaskViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(TaskBiddedAdapter.TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);
        Bid myBid = myBids.get(position);

        holder.taskTitle.setText(currentTask.getTaskName());
        holder.taskDescription.setText(currentTask.getDescription());
        holder.taskStatus.setText("Status:" + currentTask.getStatus());
        holder.yourBid.setText("Your Bid:" + myBid.getValue());
        //Needs more information then I currently have/ dont know how to implement.
        //holder.taskPhoto.setImageResource();

    }

    @Override
    public int getItemCount() {
        return tasks.getSize();
    }

    /**
     * The clickabale view holder that will get displayed in the recylcerview which displays
     * relevant information about a task including name description and status. Additionally,
     * the current users bid on the task is displayed.
     */
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView taskTitle;
        TextView taskStatus;
        TextView taskDescription;
        TextView yourBid;
        ImageView taskPhoto;

        public TaskViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            taskTitle = (TextView) itemView.findViewById(R.id.biddedTaskTitle);
            taskStatus = (TextView) itemView.findViewById(R.id.biddedTaskStatus);
            taskDescription = (TextView) itemView.findViewById(R.id.biddedTaskDescription);
            yourBid = (TextView) itemView.findViewById(R.id.biddedTaskYourBid);
            taskPhoto = (ImageView) itemView.findViewById(R.id.biddedtaskPhoto);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(thiscontext, ViewSearchedTaskDetailsActivity.class);
            intent.putExtra("TaskID", tasks.get(getAdapterPosition()).getId());
            thiscontext.startActivity(intent);

        }
    }
}


