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

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationFactory;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationList;
import ca.ualbert.cs.tasko.NotificationArtifacts.RatingNotification;
import ca.ualbert.cs.tasko.NotificationArtifacts.RatingNotificationFactory;
import ca.ualbert.cs.tasko.NotificationArtifacts.SimpleNotification;

/**
 * Created by spack on 2018-03-23.
 */

public class NotificationListTest extends ActivityInstrumentationTestCase2 {
    public NotificationListTest() {super(MainActivity.class);}

    private NotificationList nl;
    private NotificationFactory nf;
    private RatingNotificationFactory rnf;
    private NotificationHandler nh;
    private User provider;
    private User requestor;
    private Task task;

    public void setUp() {
        nl = new NotificationList();
        nf = new NotificationFactory();
        rnf = new RatingNotificationFactory();
        nh = new NotificationHandler(nf, rnf);
        requestor = new User("StevieP", "Steve", "780-450-1000",
                "spacker@ualberta.ca");
        provider = new User("Stevoo", "Stephen", "780-454-1054",
                "stevooo@ualberta.ca");
        task = new Task("requestorID", "TestTask1",
                "Help me with the factory pattern ahhhhhhh");
    }

    public void testAdd(){
        ArrayList<RatingNotification> notifications;


        SimpleNotification notification = nh.newSimpleNotification(task.getStatus(), task.getTaskName(),
                requestor, provider);

        notifications = nh.newRatingNotification(task.getTaskName(), requestor,
                provider);

        nl.addNotification(notification);
        nl.addNotification(notifications.get(0));
    }
}
