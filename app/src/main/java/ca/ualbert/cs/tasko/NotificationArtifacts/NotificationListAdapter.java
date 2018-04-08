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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ualbert.cs.tasko.R;
import ca.ualbert.cs.tasko.RatingActivity;
import ca.ualbert.cs.tasko.ViewSearchedTaskDetailsActivity;
import ca.ualbert.cs.tasko.ViewTaskDetailsActivity;
import ca.ualbert.cs.tasko.ViewTasksAssignedActivity;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * A Notification Adapter used to displayed and allow for the deletion of notifications in a
 * recyclerview.
 * @see Notification
 *
 * @author spack
 */
class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private LayoutInflater inflater;
    private NotificationList notifications;
    private Context thiscontext;

    public NotificationListAdapter(Context context, NotificationList nl) {
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        notifications = nl;
    }

    /**
     * Creates the ViewHolder for displaying Notification objects
     * @param parent parent means the View can contain other views
     * @param viewType an enumeration that tracks the type of views, in this case since we only have
     *                one view should be a constant value.
     * @return The ViewHolder object that will be used.
     */
    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_cardview, parent, false);
        NotificationViewHolder holder = new NotificationViewHolder(view, this);

        return holder;
    }


    /**
     * Attaches the appropriate data to the view holder.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification currentNotification = notifications.getNotification(position);

        holder.notificationMessage.setText(currentNotification.getMessage());

        //Set the Title depending on the type of Notification
        NotificationType type = currentNotification.getType();
        Log.d("Notification", "" + currentNotification.getType() + " " + currentNotification.getId());
        switch (type){
            case TASK_REQUESTER_RECEIVED_BID_ON_TASK:
                holder.notificationTitle.setText("New Bid Received!");
                break;
            case TASK_PROVIDER_BID_ACCEPTED:
                holder.notificationTitle.setText("You Have been Assigned a New Task!");
                break;
            case RATING:
                holder.notificationTitle.setText("Please Provide a Rating!");
                break;
            case TASK_PROVIDER_BID_DECLINED:
                holder.notificationTitle.setText("One of your Bids has been Declined!");
                break;
            case TASK_DELETED:
                holder.notificationTitle.setText("A Task you have Bid on has been Deleted!");
                break;
            case TASK_REQUESTER_REPOSTED_TASK:
                holder.notificationTitle.setText("A Task you have Bid on has been Reposted!");
                break;

        }
    }

    @Override
    public int getItemCount() {
        return notifications.getSize();
    }

    /**
     * A view holder that handles click actions and the layout of a notification
     */
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

        //Directs the user to different Activities depending on the type of notification clicked
        @Override
        public void onClick(View view) {
            Intent intent = null;
            Notification clickedNotificatoin = notifications.getNotification(getAdapterPosition());
            NotificationType Type = clickedNotificatoin.getType();

            switch (Type) {
                case TASK_REQUESTER_RECEIVED_BID_ON_TASK:
                    intent = new Intent(thiscontext, ViewTaskDetailsActivity.class);
                    break;
                case TASK_PROVIDER_BID_ACCEPTED:
                    intent = new Intent(thiscontext, ViewTasksAssignedActivity.class);
                    break;
                case RATING:
                    intent = new Intent(thiscontext, RatingActivity.class);
                    break;
                case TASK_PROVIDER_BID_DECLINED:
                case TASK_REQUESTER_REPOSTED_TASK:
                    intent = new Intent(thiscontext, ViewSearchedTaskDetailsActivity.class);
                    break;
            }
            if (intent != null) {
                intent.putExtra("TaskID", clickedNotificatoin.getTaskID());
                thiscontext.startActivity(intent);
            }
        }

            //Sets Up the delete button which removes the notification form the recyclerview and the
            //database in one motion.
            private void setupDelete(){
                Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            DataManager.getInstance().deleteNotification(notifications.getNotification
                                    (getAdapterPosition()).getId());
                        } catch (NoInternetException e) {
                            e.printStackTrace();
                        }
                        notifications.delete(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), notifications.getSize());
                    }
                });
            }
        }
    }

