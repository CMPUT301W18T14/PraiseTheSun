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

import android.content.Context;

import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * NotificationHandler deals with the creation of objects of the Notification Type. It acts as a
 * controller object that delegates all activities related to creating Notifications.
 * @see Notification
 *
 * @author spack
 */

public class NotificationHandler{

    private NotificationFactory NotificationFactory;
    private BidDeletedNotificationFactory BidDeletedFactory;

    /**
     * The constructor for NotificationHandler sets up the appropriate factories that will handle
     * the creation of notifications.
     * @param context Context is needed to set up the factories (Will be used to communicate with
     *                the DataManager)
     */
    public NotificationHandler(Context context) {
        this.NotificationFactory = new NotificationFactory();

        this.BidDeletedFactory = new BidDeletedNotificationFactory();
    }


    /**
     * This method is called to create most notifications, this includes notification based on bidding
     * assigning tasks, deleting bids or creating rating messages. The type of
     * notification that will be created by the factory.
     * @param taskID the ID of the task the notification is related to.
     * @param Type The type of the notification that will be created, An enumeration.
     */
    public void newNotification(String taskID, NotificationType Type) throws NoInternetException{

        NotificationFactory.createNotification(taskID, Type);
    }

    public void newBidDeletedNotification(String taskID, String recipientID) throws NoInternetException{
        BidDeletedFactory.createNotification(taskID, recipientID);
    }


}
