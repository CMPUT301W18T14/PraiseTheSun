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
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ualbert.cs.tasko.CurrentUser;
import ca.ualbert.cs.tasko.LoginActivity;
import ca.ualbert.cs.tasko.R;
import ca.ualbert.cs.tasko.RootActivity;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.ConnectivityState;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * The activity that displays Notifications in a recyclerview. Because the app can start with this
 * activity if a user clicks on an Android notification, we have ti handle starting the app briefly
 * before displaying the notifications.
 * @see Notification
 * @see NotificationListAdapter
 *
 * @author spack
 */
public class ViewNotificationActivity extends RootActivity {

    private ViewNotificationActivity context = this;
    private DataManager dm = DataManager.getInstance();
    private CurrentUser cu = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate
                (R.layout.activity_view_notification, null, false);
        drawerLayout.addView(contentView, 0);

        RecyclerView notificationsRecyclerView = (RecyclerView)
                findViewById(R.id.generic_recyclerview);
        RecyclerView.LayoutManager notificationsLayoutManager = new LinearLayoutManager(context);
        notificationsRecyclerView.setLayoutManager(notificationsLayoutManager);

        NotificationList myNotifications = new NotificationList();
        try {
            myNotifications.addAll(
                    dm.getNotifications(cu.getCurrentUser().getId()).getNotifications());
        } catch (NoInternetException e) {
            Toast.makeText(this.getApplicationContext(), "No Connection", Toast.LENGTH_SHORT)
                    .show();
        }

        RecyclerView.Adapter notificationsAdapter = new
                NotificationListAdapter(context, myNotifications);
        notificationsRecyclerView.setAdapter(notificationsAdapter);

    }

}


