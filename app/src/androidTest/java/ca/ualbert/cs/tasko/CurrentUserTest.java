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
 * Created by spack on 2018-03-06.
 * Its important to setCurrentUser to null before the tests, otherwise CurrentUser was remembering
 * previous values which corrupted testing. Its a simple Singelton and everything seems to work.
 */

public class CurrentUserTest extends ActivityInstrumentationTestCase2 {

    private CurrentUser CU = CurrentUser.getInstance();

    public CurrentUserTest(){
        super(MainActivity.class);
    }

    public void testLoggedIn(){
        CU.setCurrentUser(null);
        assertFalse("This should fail as no user has been added", CU.loggedIn());
    }

    public void testReturningNullUser(){
        CU.setCurrentUser(null);
        assertEquals(null, CU.getCurrentUser());
    }

    public void testAddingUser(){
        CU.setCurrentUser(null);
        User testUser = new User("jdoe", "John Doe", "123-456-9999", "jdoe@example.com");
        CU.setCurrentUser(testUser);
        assertEquals(testUser, CU.getCurrentUser());
    }

}
