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
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.ualbert.cs.tasko.MainActivity;
import ca.ualbert.cs.tasko.R;

/**
 * This activity will be called when the DataManger finds a New Notification message for a user.
 * When this Activity is created, It creates an Android Notification that when clicked will send the
 * user to the ViewNotifications Activity where all their Notifications can be seen. his notifys
 * the User that something has happened in Tasko, without this, A user outside of the app would never
 * know if something has occured without physically navigating to the ViewMyNotifications Activity.
 *@see Notification
 *@see ViewNotificationActivity
 *
 * @author spack
 */
public class CreateAndroidNotificationsActivity extends AppCompatActivity {

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int notificationID;
    private Context context;
    private String CHANNEL_ID;

    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_create_notifications);

        testButton = (Button) findViewById(R.id.notificationCreateButton);

        context = this;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationID = (int) System.currentTimeMillis();
        CHANNEL_ID = getPackageName();

        setupChannel();
        createAndriodNotification();
        setupButton();

        finish();
    }

    //Just used for testing, when actually implemented their will be no associated layout with
    //the activity
    private void setupButton(){
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                createAndriodNotification();
            }
        });
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
    private void createAndriodNotification(){
        Intent intent = new Intent(this, ViewNotificationActivity.class);
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
