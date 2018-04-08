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

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import ca.ualbert.cs.tasko.MainActivity;
import ca.ualbert.cs.tasko.R;

//This class was inspired by a tutorial from
// https://code.tutsplus.com/tutorials/android-o-how-to-use-notification-channels--cms-28616

/**
 * This object will be used when the DataManger finds a New Notification message for a user.
 * When this Object is created, It creates an Android Notification that when clicked will send the
 * user to the ViewNotifications Activity where all their Notifications can be seen. This notifies
 * the User that something has happened in Tasko, without this, A user outside of the app would never
 * know if something has occured without physically navigating to the ViewMyNotifications Activity.
 *@see Notification
 *@see ViewNotificationActivity
 *
 * @author spack
 */
public class AndroidNotificationCreator extends ContextWrapper {

    private NotificationManager notificationManager;
    private int notificationID;
    private Context context;
    private String CHANNEL_ID;

    public AndroidNotificationCreator(Context context){
        super(context);
        this.context = context;
        setup();
    }

    /**
     * Sets up the Notification Manager, Id, and checks to see if channels need to be created.
     */
    private void setup(){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationID = (int) System.currentTimeMillis();
        CHANNEL_ID = getPackageName();
        setupChannel();
    }

    /**
     * Setups the Channel we will use to send Android notifications.
     */
    private void setupChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "MyChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Creates the Android Notification that will go to the Users Device.
     */
    public void createAndroidNotification(){
        Intent intent = new Intent(this, ViewNotificationActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification_alert)
                .setContentTitle("Tasko")
                .setContentText("You have New Notifications in Tasko!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(notificationID, notificationBuilder.build());

    }

}
