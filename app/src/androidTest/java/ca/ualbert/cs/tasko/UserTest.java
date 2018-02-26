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

/**
 * Created by ryan on 2018-02-24.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest(){
        super(MainActivity.class);
    }

    public void testCreateUser() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");

        assertEquals(user.getUsername(), "rromano");
        assertEquals(user.getName(), "Ryan");
        assertEquals(user.getPhoneNumber(), "111-222-3333");
        assertEquals(user.getEmail(), "rromano@ualberta.ca");

    }

    public void testGetUserBids() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        BidList bids = new BidList();

        assertTrue(user.getBids() == null);
        Bid bid = new Bid(user, 59.99f);
        bids.addBid(bid);
        assertTrue(user.getBids() == bids);

    }

    public void testGetMyTasks() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        TaskList tasks = new TaskList();

        assertTrue(user.getMyTasks() == null);
        Task task = new Task(user, "Good Task Name", "Better description.");
        tasks.addTask(task);
        assertTrue(user.getMyTasks() == tasks);
    }



    public void testGetAssignments() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        User user2 = new User("Bob_Dylan", "Bob", "555-456-1239", "tambourineman@music.com");
        TaskList assignedTasks = new TaskList();

        assertTrue(user.getAssignements() == null);
        Task task = new Task(user2, "Good Task Name", "Better description.");

        assignedTasks.addTask(task);
        assertTrue(user.getAssignements() == assignedTask);
    }


    public void testGetNotifications() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        User user2 = new User("Bob_Dylan", "Bob", "555-456-1239", "tambourineman@music.com");
        Task task = new Task(user2, "Good Task Name", "Better description.");
        ArrayList<Notification> notifications = new ArrayList<>();
        Notification notification = new Notification(task);

        assertTrue(user.getNotifications() == null);
        notifications.add(notification);

        assertTrue(user.getNotifications() == notification);
    }

    public void testGetRating() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");

        assertTrue(user.getRating() == null);
        user.setRating(3.25f);
        assertFalse(user.getRating() == 4.25f);
        assertFalse(user.getRating() == -1.0f);
        assertTrue(user.getRating() == 3.25f);
    }

}

