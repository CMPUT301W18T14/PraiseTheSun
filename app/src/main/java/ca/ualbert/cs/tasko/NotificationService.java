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

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ualbert.cs.tasko.NotificationArtifacts.AndroidNotificationCreator;
import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationList;
import ca.ualbert.cs.tasko.data.ConnectivityState;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * @author Chase Buhler
 */

public class NotificationService extends JobService {

    private static final String FILENAME = "nfile.sav"; //User save file

    @Override
    public boolean onStartJob(JobParameters params) {
        new Thread(new Poll(params)).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params){
        return false;
    }

    private class Poll implements Runnable{

        JobParameters jp;

        public Poll(JobParameters params){
            jp = params;
        }

        @Override
        public void run() {
            User user = CurrentUser.getInstance().getCurrentUser();
            if(user == null){
                try {
                    FileInputStream fis = openFileInput(FILENAME);
                    BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                    Gson gson = new Gson();
                    user = gson.fromJson(in, User.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(user == null){
                Log.i("Error on poll", "Could not get the current user");
            } else {
                DataManager dm = DataManager.getInstance();
                Boolean alert = false;
                ConnectivityState.setConnected(isOnline(getApplicationContext()));
                Log.d("Notification Poll", "We are polling");
                try {
                    NotificationList nl = dm.getNotifications(user.getId());
                    for (Notification n : nl.getNotifications()) {
                        if (!n.getHasSeen()) {
                            n.hasSeen();
                            dm.putNotification(n);
                            alert = true;
                        }
                    }
                } catch (NoInternetException e) {
                    Log.i("Notification Poll", "No Internet Connection!");
                }
                if (alert) {
                    AndroidNotificationCreator anc =
                            new AndroidNotificationCreator(getApplicationContext());
                    anc.createAndriodNotification();
                }
                //Begin Notification Alarm
                JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context
                        .JOB_SCHEDULER_SERVICE);
                JobInfo.Builder infoBuilder = new JobInfo.Builder(1, new ComponentName
                        (getPackageName(), NotificationService.class.getName()));
                infoBuilder.setMinimumLatency(5000); //Every 5 secods
                mJobScheduler.schedule(infoBuilder.build());
                //End notification alarm
                jobFinished(jp, false);
            }
        }
    }
    private boolean isOnline(Context context){
        /*
        Retrieved on 04-03-2018
        https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
         */
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            return activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET
                    || activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.getType() == ConnectivityManager
                    .TYPE_MOBILE;
        }
        return false;
    }

}
