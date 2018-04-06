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

/**
 * A simple Variation of an ArrayList to store Notifications
 * @see Notification
 *
 * @author spack
 */
public class NotificationList{

    private ArrayList<Notification> NotificationList = new ArrayList<Notification>();

    public NotificationList() {NotificationList = new ArrayList<Notification>();}

    public void addNotification(Notification notification){
        NotificationList.add(notification);
    }

    public void deleteNotification(int index){
        NotificationList.remove(index);
    }

    public int getSize(){
        return NotificationList.size();
    }

    public void addAll(Collection<Notification> notifications){
        this.NotificationList.addAll(notifications);
    }

    public boolean hasNotification(Notification notification){
        return NotificationList.contains(notification);
    }

    public Notification getNotification(int index){
        return NotificationList.get(index);
    }

    public ArrayList<Notification> getNotifications(){
        return NotificationList;
    }

    public void delete(int index){
        NotificationList.remove(index);
    }
}
