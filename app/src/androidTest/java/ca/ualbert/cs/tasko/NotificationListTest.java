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

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationList;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationType;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by spack on 2018-03-23.
 */

public class NotificationListTest extends ActivityInstrumentationTestCase2 {
    public NotificationListTest() {super(MainActivity.class);}

    private DataManager dm = DataManager.getInstance();

    private NotificationList nlp;
    private NotificationList nlr;
    private NotificationHandler nh;
    private Task task;
    private String taskID;
    private Context context;
    private User requestor;
    private User provider;

    public void setUp() throws NoInternetException {

        context = getActivity().getApplicationContext();

        nh = new NotificationHandler(context);

        User testrequestor = new User("StevieP", "Steve", "780-450-1000",
                "spacker@ualberta.ca");
        User testprovider = new User("Stevoo", "Stephen", "780-454-1054",
                "stevooo@ualberta.ca");

        dm.putUser(requestor, context);
        dm.putUser(provider, context);

        requestor = dm.getUserByUsername("StevieP",
                context);

        provider = dm.getUserByUsername("Stevoo",
                context);

        task = new Task(requestor.getId(), "TestTask for Notifications", "Notifications");
        dm.putTask(task, context);

        taskID = task.getId();

        //Clear Old test data
        deleteTestInfo();

        //Goes to the requester
        nh.newNotification(taskID, NotificationType.TASK_REQUESTOR_RECIEVED_BID_ON_TASK);

        //Goes to the provider
        nh.newNotification(taskID, NotificationType.TASK_REQUESTOR_REPOSTED_TASK);
        nh.newNotification(taskID, NotificationType.TASK_PROVIDER_BID_ACCEPTED);
        nh.newNotification(taskID, NotificationType.TASK_DELETED);
        nh.newBidDeletedNotification(taskID, provider.getId());

       //Goes to both
        nh.newNotification(taskID, NotificationType.RATING);
    }

    public void testAddNotification() throws NoInternetException{

        nlp = new NotificationList();
        nlr = new NotificationList();

        //Test the requester notifications
        nlr.addAll(dm.getNotifications(requestor.getId(), context).getNotifications());
        //assertEquals(2, nlr.getSize());

        //Test the provider notifications
        nlp.addAll(dm.getNotifications(provider.getId(), context).getNotifications());
        //assertEquals(5, nlp.getSize());
    }

    public void deleteTestInfo() throws NoInternetException {

        nlp = new NotificationList();
        nlr = new NotificationList();

        // Delete the requester notifications
        nlr.addAll(dm.getNotifications(requestor.getId(), context).getNotifications());
        while (nlr.getSize() > 0)
            nlr.delete(0);

        //Delete the provider notifications
        nlp.addAll(dm.getNotifications(provider.getId(), context).getNotifications());
        while (nlr.getSize() > 0)
            nlr.delete(0);
    }
}
