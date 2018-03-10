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

import android.location.Location;
import android.media.Image;
import android.support.test.espresso.proto.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Chase on 2/23/2018.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {
    public TaskTest(){
        super(MainActivity.class);
    }

    public void testGetTaskName(){
        Task task = new Task("bobismyID", "atask", "test");
        assertEquals("atask", task.getTaskName());
    }

    public void testGetDescription(){
        Task task = new Task("bobismyID", "atask", "test");
        assertEquals("test", task.getDescription());
    }

    public void testSetDescription(){
        Task task = new Task("bobismyID", "atask", "");
        task.setDescription("new description");
        assertEquals("new description", task.getDescription());
    }

    public void testGetGeolocation(){
        Location location = new Location("");
        location.setLatitude(0.0d);//your coords of course
        location.setLongitude(0.0d);
        Task task = new Task("bobismyID", "atask", "test", location);
        assertEquals(location, task.getGeolocation());
    }

    public void testGetTaskRequester(){

        Task task = new Task("bobismyID", "atask", "test");
        assertEquals("bobismyID", task.getTaskRequesterID());
    }

    public void testGetStatus(){

        Task task = new Task("bobismyID", "atask", "test");
        assertTrue(task.getStatus() == Status.REQUESTED);
    }

    public void testSetStatus(){

        Task task = new Task("bobismyID", "atask", "test");
        task.setStatus(Status.BIDDED);
        assertEquals(Status.BIDDED,task.getStatus());
    }



    public void testAcceptBid(){
        Task task = new Task("bobismyID", "atask", "test");
        Bid bid = new Bid("secondUser", 2.56f, "atask");
        task.acceptBid(bid);
        assertEquals(task.getStatus(),Status.ASSIGNED);
    }

    public void testAddPhoto() {
        //TODO
        //we may not be able to use Image class for android studio
        Image photo;
        User user = new User();
        //Task task = new Task(user, "atask", "test", photo);
    }

    public void testRemovePhoto(){
        //TODO
        //we may not be able to use the image class for android
    }

    public void testAddLocation(){
        Location location = new Location("");
        location.setLatitude(0.0d);//your coords of course
        location.setLongitude(0.0d);
        Task task = new Task("bobismyID", "atask", "test");
        task.addLocation(location);
        assertEquals(location, task.getGeolocation());
    }

    public void testRemoveLocation(){
        Location location = new Location("");
        location.setLatitude(0.0d);//your coords of course
        location.setLongitude(0.0d);
        Task task = new Task("bobismyID", "atask", "test", location);
        task.removeLocation();
        assertNull(task.getGeolocation());
    }


}
