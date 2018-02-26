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

    }

    public void testGetMyTasks() {

    }

    public void testGetAssignments() {

    }

    public void testGetNotifcations() {

    }

    public void testGetRating() {

    }

}

