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
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.ualbert.cs.tasko.R;

public class TestCreateNotificationsActivity extends AppCompatActivity {

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
        setupButton();
    }

    private void setupButton(){
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                createAndriodNotification();
            }
        });
    }

    private void setupChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "MyChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createAndriodNotification(){
        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Test Title")
                .setContentText("You have New Notifications in Tasko")
                .setAutoCancel(true);

        notificationManager.notify(notificationID, notificationBuilder.build());

    }
}
