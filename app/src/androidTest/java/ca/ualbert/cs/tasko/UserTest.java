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

import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by ryan on 2018-02-24.
 * Edited by chase on 2018-03-05
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest(){
        super(MainActivity.class);
    }

    User user;
    User provider;
    Bid bid;
    Task task;

    @Override
    public void setUp(){
        user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        user.setId("userid");
        provider = new User("jdoe", "John Doe", "123-456-9999", "jdoe@example.com");
        user.setId("userid2");

        task = new Task("jdoeID", "Test Task", "This is a simple test!");
        task.setId("taskid");

        /*TODO: Implememnt using new bid*/
        bid = new Bid(provider.getId(), 4.99f, task.getId());
    }

    public void testCreateUser() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");

        assertEquals(user.getUsername(), "rromano");
        assertEquals(user.getName(), "Ryan");
        assertEquals(user.getPhoneNumber(), "111-222-3333");
        assertEquals(user.getEmail(), "rromano@ualberta.ca");

    }

    /*TODO: RedoTest cases for Updated Notifications Class*/
//    public void testGetNotifications() {
//        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
//        User user2 = new User("Bob_Dylan", "Bob", "555-456-1239", "tambourineman@music.com");
//        Task task = new Task(user2, "Good Task Name", "Better description.");
//        NotificationHandler notification = new NotificationHandler(task);
//
//        assertNull(user.getNotifications());
//        notification.sendNotification(notification);
//        assertTrue(user.getNotifications().contains(notification));
//    }


    public void testGetRating() {
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");

        assertTrue(user.getRating() == 5);
        user.setRating(3.25f);
        assertFalse(user.getRating() == 4.25f);
        assertFalse(user.getRating() == -1.0f);
        assertTrue(user.getRating() == 3.25f);
    }

}

