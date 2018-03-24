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

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.ualbert.cs.tasko.R;
import ca.ualbert.cs.tasko.ViewSearchedTaskDetailsActivity;

/**
 * A Notification Adpater.
 *
 * @author spack
 */
class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private LayoutInflater inflater;
    private NotificationList notifications;
    private Context thiscontext;

    public NotificationListAdapter(Context context, NotificationList nl) {
        thiscontext = context;
        notifications = nl;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_cardview, parent, false);
        NotificationViewHolder holder = new NotificationViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notifications.getSize();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView notificationMessage;

        public NotificationViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(this);

            notificationMessage = (TextView) itemView.findViewById(R.id.notificationBody);

        }

        @Override
        public void onClick(View view) {
            Intent intent;
            Notification clickedNotificatoin = notifications.getNotification(getAdapterPosition());
            intent = new Intent(thiscontext, ViewSearchedTaskDetailsActivity.class);
            intent.putExtra("TaskID", clickedNotificatoin.getMessage());
            thiscontext.startActivity(intent);

        }
    }
}
