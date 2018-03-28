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

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by spack on 2018-03-27.
 */

public class AndroidNotificationCreator extends ContextWrapper {

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int notificationID;
    private Context context;
    private String CHANNEL_ID;

    public AndroidNotificationCreator(Context context){
        super(context);
        this.context = context;
        setup();
    }

    private void setup(){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationID = (int) System.currentTimeMillis();
        CHANNEL_ID = getPackageName();

        if (CHANNEL_ID == null)
            setupChannel();
    }

    /**
     * Setups the Channel we will use to send notifications... If performance is an issue, I will
     * store the channel creation in a diffrent class so it does not need to be done everytime!
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
    public void createAndriodNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0);

        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_info_outline_black_24dp)
                .setContentTitle("Tasko")
                .setContentText("You have New Notifications in Tasko!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(notificationID, notificationBuilder.build());

    }

}
