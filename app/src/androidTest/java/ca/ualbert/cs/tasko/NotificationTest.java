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

import ca.ualbert.cs.tasko.NotificationArtifacts.RatingNotification;
import ca.ualbert.cs.tasko.NotificationArtifacts.RatingNotificationFactory;
import ca.ualbert.cs.tasko.NotificationArtifacts.SimpleNotification;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationFactory;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;

/**
 * Created by spack on 2018-02-23.
 */

public class NotificationTest extends ActivityInstrumentationTestCase2 {
    public NotificationTest() {
        super(MainActivity.class);
    }

<<<<<<< HEAD
    private NotificationFactory nf;
    private RatingNotificationFactory rnf;
    private NotificationHandler nh;
    private User provider;
    private User requestor;
    private Task task;

    public void setUp() {
        nf = new NotificationFactory();
        rnf = new RatingNotificationFactory();
        nh = new NotificationHandler(nf, rnf);
        requestor = new User("StevieP", "Steve", "780-450-1000",
                "spacker@ualberta.ca");
        provider = new User("Stevoo", "Stephen", "780-454-1054",
                "stevooo@ualberta.ca");
        task = new Task(requestor, "TestTask1",
                "Help me with the factory pattern ahhhhhhh");
    }

    public void testCreateSimpleNotification() {

        SimpleNotification notification = nh.newSimpleNotification(task.getStatus(), task.getTaskName(),
                requestor, provider);

        //Test to see if notification handler is properly communicating with the factory.
        assertEquals("Default Message for Testing", notification.getMessage());

        task.setStatus(Status.BIDDED);

        SimpleNotification notification2 = nh.newSimpleNotification(task.getStatus(), task.getTaskName(),
                requestor, provider);

        //Test to see if notification factory logic is working.
        assertEquals("You have received a new Bid on" + task.getTaskName(),
                notification2.getMessage());

    }

    //Test checks that RatiingNotificationFactory creates rating notifications for both parties
    public void testCreateRatingNotification() {

        ArrayList<RatingNotification> notifications;

        notifications = nh.newRatingNotification(task.getTaskName(), requestor,
                provider);

        assertEquals(notifications.size(), 2);

        assertEquals("Stevoo has completed TestTask1. Please rate their services"
                , notifications.get(0).getMessage());

        assertEquals("You have completed TestTask1. Please rate your experience with StevieP"
                , notifications.get(1).getMessage());




    }



}
