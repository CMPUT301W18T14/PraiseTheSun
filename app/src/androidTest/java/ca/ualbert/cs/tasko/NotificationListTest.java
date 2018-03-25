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

import ca.ualbert.cs.tasko.NotificationArtifacts.SimpleNotificationFactory;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationList;
import ca.ualbert.cs.tasko.NotificationArtifacts.RatingNotification;
import ca.ualbert.cs.tasko.NotificationArtifacts.RatingNotificationFactory;
import ca.ualbert.cs.tasko.NotificationArtifacts.SimpleNotification;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by spack on 2018-03-23.
 */

public class NotificationListTest extends ActivityInstrumentationTestCase2 {
    public NotificationListTest() {super(MainActivity.class);}

    private DataManager dm = DataManager.getInstance();

    private NotificationList nl;
    private NotificationHandler nh;
    private String providerID;
    private String requestorID;
    private Task task;

    public void setUp() throws NoInternetException {
        nh = new NotificationHandler(getActivity().getApplicationContext());

        User requestor = new User("StevieP", "Steve", "780-450-1000",
                "spacker@ualberta.ca");
        User provider = new User("Stevoo", "Stephen", "780-454-1054",
                "stevooo@ualberta.ca");

        //Dont keep putting in Users
        if (dm.getUserByUsername("StevieP", getActivity().getApplicationContext()) == null) {
            dm.putUser(requestor, getActivity().getApplicationContext());
            dm.putUser(provider, getActivity().getApplicationContext());
        }

        requestorID = dm.getUserByUsername("StevieP",
                getActivity().getApplicationContext()).getId();

        providerID = dm.getUserByUsername("Stevoo",
                getActivity().getApplicationContext()).getId();

        task = new Task(requestorID, "TestTask for Notifications", "Notifications");
        dm.putTask(task, getActivity().getApplicationContext());
    }

    public void testAddingSimpleNotifications() throws NoInternetException {

        nh.newSimpleNotification(task.getId(), requestorID, providerID);

        nl = dm.getNotifications(requestorID, getActivity().getApplicationContext());

        assertFalse(nl.getSize() == 0);

    }

    public void testAddingRatingNotifications() throws NoInternetException {

        nh.newRatingNotification(task.getId(), requestorID, providerID);

        nl = dm.getNotifications(requestorID, getActivity().getApplicationContext());

        assertFalse(nl.getSize() == 0);

        nl = dm.getNotifications(providerID, getActivity().getApplicationContext());

        assertFalse(nl.getSize() == 0);

    }
}
