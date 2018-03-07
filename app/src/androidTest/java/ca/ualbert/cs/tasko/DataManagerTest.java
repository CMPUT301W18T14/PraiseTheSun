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
import android.util.Log;

import junit.framework.TestCase;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by chase on 3/4/2018.
 */

public class DataManagerTest extends ActivityInstrumentationTestCase2 {

    public DataManagerTest(){
        super(MainActivity.class);
    }

    private User user1;
    private DataManager dm;

    public void setUp(){
        user1 = new User("jdoe3", "John Doe", "555-333-1234", "jdoe3@example.com");
        dm = DataManager.getInstance();
    }

    public void testPutUser(){
        User returnedUser = null;
        try {
            dm.putUser(user1, getActivity().getApplicationContext());
        } catch (NoInternetException e) {
            Log.i("Error", "No internet connection. Can not add a new user at the moment");
        }
        try {
            returnedUser = dm.getUserById(user1.getId(), getActivity()
                    .getApplicationContext());
        } catch (NoInternetException e){
            Log.i("Error", "The phone has no internet so this test will fail");
        }
        assertFalse(returnedUser == null);
        assertEquals(returnedUser.getId(), user1.getId());
    }

    public void testGetUserByUsername(){
        User returnedUser = null;
        try {
            dm.putUser(user1, getActivity().getApplicationContext());
        } catch (NoInternetException e){
            Log.i("Error", "No internet connection, can not add the user to elasticsearch");
        }

        try {
            returnedUser = dm.getUserByUsername(user1.getUsername(), getActivity()
                    .getApplicationContext());
        } catch (NoInternetException e){
            Log.i("Error", "No internet connection, can not get the user from elasticsearch");
        }
        assertFalse(returnedUser == null);
        assertEquals(returnedUser.getId(), user1.getId());
    }
}
