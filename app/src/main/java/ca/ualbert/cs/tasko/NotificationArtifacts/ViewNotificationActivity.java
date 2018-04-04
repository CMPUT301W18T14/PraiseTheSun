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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import ca.ualbert.cs.tasko.CurrentUser;
import ca.ualbert.cs.tasko.R;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class ViewNotificationActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private RecyclerView.Adapter notificationsAdapter;
    private RecyclerView.LayoutManager notificationsLayoutManager;
    private ViewNotificationActivity context = this;
    private DataManager dm = DataManager.getInstance();
    private CurrentUser cu = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);

        notificationsRecyclerView =  (RecyclerView) findViewById(R.id.generic_recyclerview);
        notificationsLayoutManager = new LinearLayoutManager(context);
        notificationsRecyclerView.setLayoutManager(notificationsLayoutManager);

        NotificationList myNotifications = new NotificationList();
        try {
            myNotifications.addAll(
                    dm.getNotifications(cu.getCurrentUser().getId(), context).getNotifications());
        } catch (NoInternetException e){
            Toast.makeText(this.getApplicationContext(), "No Connection", Toast.LENGTH_SHORT);
        }

        notificationsAdapter = new NotificationListAdapter(context, myNotifications);
        notificationsRecyclerView.setAdapter(notificationsAdapter);

    }
}
