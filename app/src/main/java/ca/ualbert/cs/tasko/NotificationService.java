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

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import ca.ualbert.cs.tasko.CurrentUser;
import ca.ualbert.cs.tasko.NotificationArtifacts.AndroidNotificationCreator;
import ca.ualbert.cs.tasko.NotificationArtifacts.CreateAndroidNotificationsActivity;
import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationList;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * @author Chase Buhler
 */

public class NotificationService extends Service {

    public NotificationService(){
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
            Log.i("Notification Poll", "No Internet Connection!");
        }
        if(alert){
            AndroidNotificationCreator anc =
                    new AndroidNotificationCreator(getApplicationContext());
            anc.createAndriodNotification();
        }
        return START_NOT_STICKY;
    }
}

/*
ADD THIS TO START POLL:
Intent i = new Intent(context, NotificationService.class);
context.startService(i);
 */