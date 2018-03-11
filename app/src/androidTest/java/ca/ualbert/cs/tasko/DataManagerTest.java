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

import java.io.IOException;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.JestWrapper;
import ca.ualbert.cs.tasko.data.NoInternetException;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by chase on 3/4/2018.
 */

public class DataManagerTest extends ActivityInstrumentationTestCase2 {

    public DataManagerTest(){
        super(MainActivity.class);
    }

    private User user1;
    private User user2;
    private DataManager dm;

    public void setUp(){
        user1 = new User("jdoe62", "John Doe", "555-333-1234", "jdoe7@example.com");
        user2 = new User("jdoe63", "John Doe", "555-333-1234", "jdoe7@example.com");
        dm = DataManager.getInstance();
    }

    public void testPutUser(){
        User returnedUser = null;
        try {
            dm.putUser(user2, getActivity().getApplicationContext());
        } catch (NoInternetException e) {
            Log.i("Error", "No internet connection. Can not add a new user at the moment");
        } catch (IllegalArgumentException e){
            //this is good
        }
        try {
            returnedUser = dm.getUserById(user2.getId(), getActivity()
                    .getApplicationContext());
        } catch (NoInternetException e){
            Log.i("Error", "The phone has no internet so this test will fail");
        }
        assertFalse(returnedUser == null);
        assertEquals(returnedUser.getId(), user2.getId());

        boolean noDup = false;
        try {
            dm.putUser(user2, getActivity().getApplicationContext());
        } catch (IllegalArgumentException e){
            noDup = true;
        } catch (NoInternetException e) {

        }

        assertTrue(noDup);
    }

    public void testGetUserByUsername(){
        User returnedUser = null;
        try {
            dm.putUser(user1, getActivity().getApplicationContext());
        } catch (NoInternetException e){
            Log.i("Error", "No internet connection, can not add the user to elasticsearch");
        } catch (IllegalArgumentException e){

        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            returnedUser = dm.getUserByUsername(user1.getUsername(), getActivity()
                    .getApplicationContext());
        } catch (NoInternetException e){
            Log.i("Error", "No internet connection, can not get the user from elasticsearch");
        }
        assertFalse(returnedUser == null);
        assertEquals(user1.getUsername(), returnedUser.getUsername());
    }
}
