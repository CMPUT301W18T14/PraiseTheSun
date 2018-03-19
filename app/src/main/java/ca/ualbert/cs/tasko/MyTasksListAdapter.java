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
 * Created by ryand on 2018-03-18.
 */

public class MyTasksListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private LayoutInflater inflater;
    private TaskList tasks;
    private Context thiscontext;

    public MyTasksListAdapter(Context context, TaskList dmtasks){
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        tasks = dmtasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_view, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);

        holder.taskTitle.setText(currentTask.getTaskName());
        holder.taskDescription.setText(currentTask.getDescription());
        holder.taskStatus.setText("Status: " + currentTask.getStatus());
        //Needs more information then I currently have/ dont know how to implement.
        //holder.taskPhoto.setImageResource();

    }

    @Override
    public int getItemCount(){
        return tasks.getSize();
    }

    /**
     * The clickabale view holder that will get displayed in the recylcerview which displays
     * relevant information about a task including name description and status.
     */
    class MyTasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView taskTitle;
        TextView taskStatus;
        TextView taskDescription;
        ImageView taskPhoto;

        public MyTasksViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            taskTitle = (TextView) itemView.findViewById(R.id.searchTaskTitle);
            taskStatus = (TextView) itemView.findViewById(R.id.searchTaskStatus);
            taskDescription = (TextView) itemView.findViewById(R.id.searchTaskDescription);
            taskPhoto = (ImageView) itemView.findViewById(R.id.taskPhoto);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(thiscontext, ViewTaskDetailsActivity.class);
            intent.putExtra("TaskID", tasks.get(getAdapterPosition()).getId());
            thiscontext.startActivity(intent);

        }
    }

}
