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
import android.support.annotation.Nullable;


/**
 * Created by chase on 3/25/2018.
 */

public class NotificationService extends BroadcastReceiver {
    /*
    Referenced
    https://stackoverflow.com/questions/39169469/creating-an-android-background-service-that-continuously-polls-a-rest-api-for-da
    retrieved on March 25, 2018
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent i = new Intent(context, NotificationPoll.class);
            context.startService(i);

        }
    }

    public class NotificationPoll extends IntentService {

        public static final long NOTIFICATION_POLL_RATE = 10 * 1000;

        private Handler mHandler;

        public NotificationPoll(){
            super("Poll Notifications");
        }

        private Runnable pollRunnable = new Runnable() {
            @Override
            public void run() {
                poll();
                mHandler.postDelayed(pollRunnable, NOTIFICATION_POLL_RATE);
            }
        };

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            mHandler = new Handler();

            mHandler.post(pollRunnable);
        }

        private synchronized void poll(){
            //TODO: ACtually Poll
        }
    }
}
