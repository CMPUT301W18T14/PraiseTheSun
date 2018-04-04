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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * The class represents a Adapter that is specifically designed to display search results in an
 * AdapterView. Clicking on an element in the view will send the user to that tasks details where
 * they can place a Bid if they wish. All other adapters are based on the template of this adapter
 * so refer to this for Adapter Documentation.
 *
 * @author spack
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private LayoutInflater inflater;
    private TaskList tasks;
    private Context thiscontext;
    private BidList myBids;

    /**
     * Constructor for the Adapter, Takes in the context which designates the activity that will use
     * the adpater and a TaskList which represents the Tasks that will be displayed.
     * @param context The context for the activity using the adapter.
     * @param dmTasks The TaskList represnting the Tasks to be displayed, from the DataManager.
     */
    public TaskListAdapter(Context context, TaskList dmTasks){
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        tasks = dmTasks;
    }

    /**
     * Alternate Constructor for the Adapter, Takes in the context which designates the activity
     * that will use the adpater and a TaskList which represents the Tasks that will be displayed.
     * This alternate Adapter includes a bidlist which represents a users bids, will be included
     * when the ViewTasksBiddedOnActivity is called.
     * @param context The context for the activity using the adapter.
     * @param dmTasks The TaskList representing all Tasks a user has bid on, from the DataManager.
     * @param dmMyBids A BidList which represents all bids a user has made on the include TaskList.
     */
    public TaskListAdapter(Context context, TaskList dmTasks, BidList dmMyBids){
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        tasks = dmTasks;
        myBids = dmMyBids;
    }

    /**
     * Creates the ViewHolder object that will display the Objects, in this case Tasks matching a
     * search query.
     * @param parent parent means the View can contain other views
     * @param viewType an enumeration that tracks the type of views, in this case since we only have
     *                one view should be a constant value.
     * @return The ViewHolder object that will be used.
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_cardview_layout, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view);

        return holder;
    }

    /**
     * Binds The appropriate Data to the ViewHolder, this includes a TaskTitle, Description, Status,
     * Minimum Bid, and potentially, a users Bid on a task and a Photo of the Task.
     * @param holder The ViewHolder data will be bound too.
     * @param position The position within the RecyclerView.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);

        holder.taskTitle.setText(currentTask.getTaskName());
        holder.taskDescription.setText(currentTask.getDescription());
        holder.taskRequestorUsername.setText(currentTask.getTaskRequesterUsername());

        //Taken From https://stackoverflow.com/questions/2538787/
        //how-to-display-an-output-of-float-data-with-2-decimal-places-in-java
        //2018-03-26
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);

        // Tries to get the minimum bid on each task if it exists
        if (currentTask.getMinBid() != null){
            String lowbidValue = df.format(currentTask.getMinBid());
            holder.taskLowestBid.setText("Lowest Bid: " + lowbidValue);
        }else{
            holder.taskLowestBid.setText("Make the First Bid!");
        }

        // Checks see if to get the users bid on the Task if it exists
        if (myBids != null){
            holder.taskMyBid.setText("My Bid: " + df.format(myBids.get(position).getValue()));
        } else{
            holder.taskMyBid.setText("");
        }

        // Show the status of a task using an Icon instead of text, saves space and looks better??
        Status status = currentTask.getStatus();
        switch (status) {
            case REQUESTED:
                holder.taskStatusIcon.setImageResource(R.drawable.requested);
                break;
            case BIDDED:
                holder.taskStatusIcon.setImageResource(R.drawable.bidded);
                break;
            case ASSIGNED:
                holder.taskStatusIcon.setImageResource(R.drawable.assigned);
                break;
            case DONE:
                holder.taskStatusIcon.setImageResource(R.drawable.done);
                break;
        }

        holder.taskPhoto.setImageBitmap(currentTask.getCoverPhoto());

    }

    /**
     * Used to determine the number of ViewHolders the recyclerview will need to display.
     * @return An int representing the number of objects to be Displayed.
     */
    @Override
    public int getItemCount(){
        return tasks.getSize();
    }

    /**
     * The clickabale ViewHolder that will get displayed in the recylcerview which displays
     * relevant information about a task including name description and status. Clikcing on a Task
     * will send users to an Activity that will display all details about a task.
     *
     * @see ViewSearchedTaskDetailsActivity
     */
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView taskTitle;
        ImageView taskStatusIcon;
        TextView taskDescription;
        TextView taskLowestBid;
        TextView taskMyBid;
        TextView taskRequestorUsername;
        ImageView taskPhoto;

        public TaskViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            taskTitle = (TextView) itemView.findViewById(R.id.searchTaskTitle);
            taskRequestorUsername = (TextView) itemView.findViewById(R.id.searchedTaskUsername);
            taskStatusIcon = (ImageView) itemView.findViewById(R.id.searchedTaskStatusIcon);
            taskDescription = (TextView) itemView.findViewById(R.id.searchTaskDescription);
            taskLowestBid = (TextView) itemView.findViewById(R.id.searchTaskLowestBid);
            taskMyBid = (TextView) itemView.findViewById(R.id.searchedTasksMyBidOnTask);
            taskPhoto = (ImageView) itemView.findViewById(R.id.searchTaskPhoto);

            setupPhotoClick();
            setupUserNameClick();

        }

        private void setupPhotoClick(){
            taskPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(thiscontext, ViewPhotoActivity.class);
                    intent.putExtra("photos", tasks.get(getAdapterPosition()));
                    thiscontext.startActivity(intent);
                }
            });
        }

        private void setupUserNameClick(){
            taskPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(thiscontext, OtherUsersProfileActivity.class);
                    intent.putExtra("id", tasks.get(getAdapterPosition()).getTaskRequesterID());
                    thiscontext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            if (thiscontext instanceof ViewMyTasksActivity) {
                if (tasks.get(getAdapterPosition()).getStatus() == Status.ASSIGNED) {
                    intent = new Intent(thiscontext, AcceptedMyTaskActivity.class);
                }
                else {
                    intent = new Intent(thiscontext, ViewTaskDetailsActivity.class);
                }
        }
            else {
                intent = new Intent(thiscontext, ViewSearchedTaskDetailsActivity.class);
            }
            intent.putExtra("TaskID", tasks.get(getAdapterPosition()).getId());
            thiscontext.startActivity(intent);
        }
    }

}
