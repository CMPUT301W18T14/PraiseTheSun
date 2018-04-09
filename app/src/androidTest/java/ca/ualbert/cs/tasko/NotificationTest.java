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

import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationType;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.MockDataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * Testing class for the entity class Notification
 *
 * @see Notification
 */
public class NotificationTest extends ActivityInstrumentationTestCase2 {
    public NotificationTest() {
        super(MainActivity.class);
    }

    private DataManager dm = DataManager.getInstance();

    private String providerID;
    private String requestorID;
    private Task task;
    private Notification n;

    public void setUp() throws NoInternetException {
        dm.init(getActivity().getApplicationContext());
        User requestor = new User("StevieP", "Steve", "780-450-1000",
                "spacker@ualberta.ca");
        User provider = new User("Stevoo", "Stephen", "780-454-1054",
                "stevooo@ualberta.ca");

        requestorID = MockDataManager.getInstance().getTaskRequester().getId();

        providerID = MockDataManager.getInstance().getTaskProvider().getId();

        task = new Task(requestorID, "TestTask for Notifications", "Notifications");
        task.setId("TestTaskID");
        dm.putTask(task);

        n = new Notification("Test notification", requestorID, providerID, task.getId(),
                NotificationType.TASK_REQUESTER_RECEIVED_BID_ON_TASK);
        n.setId("nID");
    }

    public void testGetMessage() {
        assertEquals("Test notification", n.getMessage());
    }

    public void testGetRecipientID() {
        assertEquals(requestorID, n.getRecipientID());
    }

    public void testGetTaskID() {
        assertEquals(task.getId(), n.getTaskID());
    }

    public void testGetSenderID() {
        assertEquals(providerID, n.getSenderID());
    }

    public void testGetType() {
        assertEquals(NotificationType.TASK_REQUESTER_RECEIVED_BID_ON_TASK, n.getType());
    }

    public void testHasSeen() {
        assertFalse(n.getHasSeen());
        n.hasSeen();
        assertTrue(n.getHasSeen());
    }

    public void testEquals() {
        assertTrue(n.equals(n));
        Notification n2 = new Notification("Not same", requestorID, providerID, task.getId(),
                NotificationType.TASK_REQUESTER_RECEIVED_BID_ON_TASK);
        n2.setId("n2ID");
        assertFalse(n.equals(n2));
    }
}
