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

        assertNull(user.getBids());
        Bid bid = new Bid(user, 59.99f);
        user.addBid(bid);
        assertTrue(user.getBids().hasBid(bid));
    }

    public void testGetMyTasks() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");

        assertNull(user.getMyTasks());
        Task task = new Task(user, "Good Task Name", "Better description.");
        user.addMyTasks(task);
        assertTrue(user.getMyTasks().getTasks().contains(task));
    }

    public void testGetAssignments() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        User user2 = new User("Bob_Dylan", "Bob", "555-456-1239", "tambourineman@music.com");

        assertNull(user.getAssignments());
        Task task = new Task(user2, "Good Task Name", "Better description.");
        user.addAssignments(task);
        assertTrue(user.getAssignments().getTasks().contains(task));
    }


    public void testGetNotifications() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        User user2 = new User("Bob_Dylan", "Bob", "555-456-1239", "tambourineman@music.com");
        Task task = new Task(user2, "Good Task Name", "Better description.");
        Notification notification = new Notification(task);

        assertNull(user.getNotifications());
        user.addNotification(notification);
        assertTrue(user.getNotifications().contains(notification));
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

