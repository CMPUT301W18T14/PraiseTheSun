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
import ca.ualbert.cs.tasko.data.MockDataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Test the creation of the notifications and ensures they are "sent" to the proper party in the
 * proper quantity and that we can retrieve these notifications and store them in
 * a Notification List using addAll. Additionally this test the add, getsize, delete and get
 * methods in the arrayList! The creation of notifications test the notification handler and its
 * associated factories and all the logic that handles those operations.
 * @see ca.ualbert.cs.tasko.NotificationArtifacts.Notification
 *
 * @author spack
 */
public class NotificationsTests extends ActivityInstrumentationTestCase2 {
    public NotificationsTests() {super(MainActivity.class);}

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
        dm.init(context);
        User loggedUser = MockDataManager.getInstance().getUser();
        CurrentUser.getInstance().setCurrentUser(loggedUser);

        nlp = new NotificationList();
        nlr = new NotificationList();

        nh = new NotificationHandler(context);

        User testrequestor = new User("StevieP", "Steve", "780-450-1000",
                "spacker@ualberta.ca");
        User testprovider = new User("Stevoo", "Stephen", "780-454-1054",
                "stevooo@ualberta.ca");

        dm.putUser(testrequestor);
        dm.putUser(testprovider);

        requestor = dm.getUserByUsername("StevieP");

        provider = dm.getUserByUsername("Stevoo");

        task = new Task(requestor.getId(), "TestTask for Notifications", "Notifications");
        task.setTaskRequesterUsername(requestor.getUsername());
        dm.putTask(task);

        taskID = task.getId();

        Bid testBid = new Bid(provider.getId(), 10f, taskID);

        dm.addBid(testBid);


        //Clear Old test data
        deleteTestInfo();

    }

    public void testNewBidNotification() throws NoInternetException{

        nh.newNotification(taskID, NotificationType.TASK_REQUESTER_RECEIVED_BID_ON_TASK);
        sleep();
        nlr.addAll(dm.getNotifications(requestor.getId()).getNotifications());
        assertEquals(1, nlr.getSize());
        deleteTestInfo();
    }

    public void testReOpenedTaskdNotification() throws NoInternetException{

        nh.newNotification(taskID, NotificationType.TASK_REQUESTER_REPOSTED_TASK);
        sleep();
        nlp.addAll(dm.getNotifications(provider.getId()).getNotifications());
        assertEquals(1, nlp.getSize());
        deleteTestInfo();
    }

    public void testBidAcceptedNotification() throws NoInternetException{

        task.assign(provider.getId());
        dm.putTask(task);
        sleep();
        nh.newNotification(taskID, NotificationType.TASK_PROVIDER_BID_ACCEPTED);
        sleep();
        nlp.addAll(dm.getNotifications(provider.getId()).getNotifications());
        assertEquals(1, nlp.getSize());
        deleteTestInfo();
    }

    public void testTaskDeletedNotification() throws NoInternetException{

        nh.newNotification(taskID, NotificationType.TASK_DELETED);
        sleep();
        nlp.addAll(dm.getNotifications(provider.getId()).getNotifications());
        assertEquals(1, nlp.getSize());
        deleteTestInfo();
    }

    public void testBidDeclinedNotification() throws NoInternetException{

        nh.newBidDeletedNotification(taskID, provider.getId());
        sleep();
        nlp.addAll(dm.getNotifications(provider.getId()).getNotifications());
        assertEquals(1, nlp.getSize());
        deleteTestInfo();
    }

    public void testRatingNotification() throws NoInternetException{

        task.assign(provider.getId());

        nh.newNotification(taskID, NotificationType.RATING);
        sleep();

        nlr.addAll(dm.getNotifications(requestor.getId()).getNotifications());
        assertEquals(1, nlr.getSize());


        nlp.addAll(dm.getNotifications(provider.getId()).getNotifications());
        //assertEquals(1, nlp.getSize());

        deleteTestInfo();
    }

    /**
     * Primary function is to delete old test data so testing is pure. This also tests the delete
     * functionality of Notification Lists as well as the get method.
     */
    private void deleteTestInfo() throws NoInternetException {

        nlp = new NotificationList();
        nlr = new NotificationList();

        // Delete the requester notifications
        nlr.addAll(dm.getNotifications(requestor.getId()).getNotifications());
        while (nlr.getSize() > 0) {
            dm.deleteNotification(nlr.getNotification(0).getId());
            nlr.delete(0);
        }

        //Delete the provider notifications
        nlp.addAll(dm.getNotifications(provider.getId()).getNotifications());
        while (nlp.getSize() > 0) {
            dm.deleteNotification(nlp.getNotification(0).getId());
            nlp.delete(0);
        }
    }

    private void sleep(){
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
