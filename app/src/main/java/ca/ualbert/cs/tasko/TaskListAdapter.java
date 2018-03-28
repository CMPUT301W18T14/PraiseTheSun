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

//Todo: Make the adapter more modular, can be reused but needs slight modifications each time

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

    /**
     * Constructor for the Adapter, Takes in the context which designates the activity that will use
     * the adpater and a TaskList which represents the Tasks that will be displayed.
     * @param context The context for the activity using the adapter.
     * @param dmtasks The TaskList represnting the Tasks to be displayed, from the DataManager.
     */
    public TaskListAdapter(Context context, TaskList dmtasks){
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        tasks = dmtasks;
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
        View view = inflater.inflate(R.layout.task_view, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view);

        return holder;
    }

    /**
     * Binds The appropriate Data to the ViewHolder.
     * @param holder The ViewHolder data will be bound too.
     * @param position The position within the RecyclerView.
     */
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);

        holder.taskTitle.setText(currentTask.getTaskName());
        holder.taskDescription.setText(currentTask.getDescription());
        holder.taskStatus.setText("Status: " + currentTask.getStatus());
        //Needs more information then I currently have/ dont know how to implement.
        //holder.taskPhoto.setImageResource();

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
        TextView taskStatus;
        TextView taskDescription;
        ImageView taskPhoto;

        public TaskViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            taskTitle = (TextView) itemView.findViewById(R.id.searchTaskTitle);
            taskStatus = (TextView) itemView.findViewById(R.id.searchTaskStatus);
            taskDescription = (TextView) itemView.findViewById(R.id.searchTaskDescription);
            //taskPhoto = (ImageView) itemView.findViewById(R.id.searchTaskPhoto);
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            if (thiscontext instanceof ViewMyTasksActivity) {
                intent = new Intent(thiscontext, ViewTaskDetailsActivity.class);
            }
            else {
                intent = new Intent(thiscontext, ViewSearchedTaskDetailsActivity.class);
            }
            intent.putExtra("TaskID", tasks.get(getAdapterPosition()).getId());
            thiscontext.startActivity(intent);
        }
    }

}
