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

package ca.ualbert.cs.tasko.NotificationArtifacts;

import java.util.ArrayList;
import java.util.Collection;

import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;

/**
 * A simple Variation of an ArrayList to store Notifications. Includes the ability to
 * add/ remove notifications as well as getting Notifications at specific indices or returning the entire
 * ArrayList.
 * @see Notification
 *
 * @author spack
 */
public class NotificationList{

    /**
     * Initializes The NotificationList as an ArrayList of Notification objects.
     */
    private ArrayList<Notification> NotificationList = new ArrayList<Notification>();

    /**
     * Default Constructor.
     */
    public NotificationList() {NotificationList = new ArrayList<Notification>();}

    /**
     * Adds a Notification to the NotificationList.
     * @param notification a Notification object.
     */
    public void addNotification(Notification notification){
        NotificationList.add(notification);
    }

    /**
     * Returns the size of the NotificationList (How many Notifications it contains) Needed for RecyclerView Adapter
     * @return An integer representing the size of the NotificationList
     */
    public int getSize(){
        return NotificationList.size();
    }

    /**
     * Utilizes the Collections.addAll() method to add multiple Notifications that are all contained
     * inside a collection to this NotificationList
     *
     * @param notifications a collection of Notifications
     */
    public void addAll(Collection<Notification> notifications){
        this.NotificationList.addAll(notifications);
    }

    /**
     * Returns the Notification found at a specific position in the NotificationList.
     * @param index The interger position of the Notification we wish to return.
     * @return a Notification object.
     */
    public Notification getNotification(int index){
        return NotificationList.get(index);
    }

    /**
     * Get all the Notifications from a NotificationList.
     * @return An ArrayList containing all Notification objects stored in the NotificationList.
     */
    public ArrayList<Notification> getNotifications(){
        return NotificationList;
    }

    /**
     * Removes a specific Notification from the NotificationList
     * @param index the index of the Notification that is requested to be removed.
     */
    public void delete(int index){
        NotificationList.remove(index);
    }
}
