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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by spack on 2018-03-14.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private LayoutInflater inflater;
    private TaskList tasks;

    public TaskListAdapter(Context context, TaskList dmtasks){
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
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);

        holder.taskTitle.setText(currentTask.getTaskName());
        holder.taskDescription.setText(currentTask.getDescription());
        //Needs more information then I currently have/ know how to implement.
        //holder.taskBid.setText();
        //holder.taskPhoto.setImageResource();

    }

    @Override
    public int getItemCount(){
        return tasks.getSize();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        TextView taskBid;
        TextView taskDescription;
        //ImageView taskPhoto;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskTitle = (TextView) itemView.findViewById(R.id.searchTaskTitle);
            taskBid = (TextView) itemView.findViewById(R.id.searchTaskLowBid);
            taskDescription = (TextView) itemView.findViewById(R.id.searchTaskDescription);
            //taskPhoto = (ImageView) itemView.findViewById(R.id.taskPhoto);
        }
    }

}
