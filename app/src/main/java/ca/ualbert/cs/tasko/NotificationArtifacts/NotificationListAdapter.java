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

package ca.ualbert.cs.tasko.NotificationArtifacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualbert.cs.tasko.CurrentUser;
import ca.ualbert.cs.tasko.R;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.ViewSearchedTaskDetailsActivity;
import ca.ualbert.cs.tasko.ViewTaskDetailsActivity;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * A Notification Adpater.
 *
 * @author spack
 */
class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private LayoutInflater inflater;
    private NotificationList notifications;
    private User currentUser;
    private DataManager dm;
    private Context thiscontext;

    public NotificationListAdapter(Context context) {
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        currentUser = CurrentUser.getInstance().getCurrentUser();
        dm = DataManager.getInstance();
        this.update();
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_cardview, parent, false);
        NotificationViewHolder holder = new NotificationViewHolder(view, this);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification currentNotification = notifications.getNotification(position);

        holder.notificationMessage.setText(currentNotification.getMessage());

        //Set the Title depending on the type of Notification
        NotificationType type = currentNotification.getType();
        switch (type){
            case TASK_REQUESTOR_RECIEVED_BID_ON_TASK:
                holder.notificationTitle.setText("New Bid Received!");
                break;
            case TASK_PROVIDER_BID_ACCEPTED:
                holder.notificationTitle.setText("You Have been Assigned a new Task!");
                break;
            case RATING:
                holder.notificationTitle.setText("Please Provide a Rating!");
                break;
            case TASK_PROVIDER_BID_DECLINED:
                holder.notificationTitle.setText("One of your bids has been declined");
                break;
            case TASK_DELETED:
                holder.notificationTitle.setText("A Task you have bidded in has been Deleted!");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notifications.getSize();
    }

    public void update() {
        notifications = new NotificationList();
        try {
            notifications.addAll(dm.getNotifications(currentUser.getId(), thiscontext)
                    .getNotifications());
        } catch (NoInternetException e){
            Toast.makeText(thiscontext.getApplicationContext(), "No Connection", Toast.LENGTH_SHORT);
        }
        this.notifyDataSetChanged();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notificationMessage;
        TextView notificationTitle;
        ImageView Delete;
        NotificationListAdapter adapter;

        public NotificationViewHolder(View itemView, NotificationListAdapter adapter){
            super(itemView);

            itemView.setOnClickListener(this);

            this.adapter = adapter;
            notificationMessage = (TextView) itemView.findViewById(R.id.notificationBody);
            notificationTitle = (TextView) itemView.findViewById(R.id.notificationTitle);
            Delete = (ImageView) itemView.findViewById(R.id.notificationDeleteOption);

            setupDelete();
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            Notification clickedNotificatoin = notifications.getNotification(getAdapterPosition());
            NotificationType Type = clickedNotificatoin.getType();

            switch (Type){
                case TASK_REQUESTOR_RECIEVED_BID_ON_TASK:
                    intent = new Intent(thiscontext, ViewTaskDetailsActivity.class);
                    intent.putExtra("TaskID", clickedNotificatoin.getTaskID());
                    thiscontext.startActivity(intent);
                    break;
                case TASK_PROVIDER_BID_ACCEPTED:
                    //TODO REDIRECT TO TASKS ASSIGNED ACTIVITY
                    break;
                case RATING:
                    //TODO IMPLEMENT RATING ACTIVITY
                    break;
                case TASK_PROVIDER_BID_DECLINED:
                    intent = new Intent(thiscontext, ViewSearchedTaskDetailsActivity.class);
                    intent.putExtra("TaskID", clickedNotificatoin.getTaskID());
                    thiscontext.startActivity(intent);
                    break;
                    }
            }

            private void setupDelete(){
                Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            DataManager.getInstance().deleteNotification(notifications
                                     .getNotification(getAdapterPosition()).getId(), thiscontext);
                            /*
                             * NOTE: may need to thread.sleep as we are deleting then retrieving
                             * right away so there is a lag
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            */
                            adapter.update();
                        } catch (NoInternetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

