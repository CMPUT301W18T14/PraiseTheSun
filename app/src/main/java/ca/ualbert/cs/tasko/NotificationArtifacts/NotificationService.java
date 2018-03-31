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

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import ca.ualbert.cs.tasko.CurrentUser;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * Created by chase on 3/25/2018.
 */

public class NotificationService extends IntentService {


    public static final long NOTIFICATION_POLL_RATE = 10 * 1000;

    private Handler mHandler;
    private Runnable pollRunnable = new Runnable() {
        @Override
        public void run() {
            poll();
            mHandler.postDelayed(pollRunnable, NOTIFICATION_POLL_RATE);
        }
    };

    public NotificationService(){
        super("Poll Notifications");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mHandler = new Handler();

        mHandler.post(pollRunnable);
    }

    private synchronized void poll(){
        User user = CurrentUser.getInstance().getCurrentUser();
        DataManager dm = DataManager.getInstance();
        Boolean alert = false;
        try {
            NotificationList nl = dm.getNotifications(user.getId(), getApplicationContext());
            for(Notification n: nl.getNotifications()){
                if(!n.getHasSeen()){
                    n.hasSeen();
                    dm.putNotification(n, getApplicationContext());
                    alert = true;
                }
            }
        } catch (NoInternetException e){
            //Do nothing. Just Skip this poll
        }
        if(alert){
            Intent i = new Intent(getApplicationContext(),
                    CreateAndroidNotificationsActivity.class);
            startActivity(i);
        }
    }
}

/*
ADD THIS TO START POLL:
Intent i = new Intent(context, NotificationService.class);
context.startService(i);
 */