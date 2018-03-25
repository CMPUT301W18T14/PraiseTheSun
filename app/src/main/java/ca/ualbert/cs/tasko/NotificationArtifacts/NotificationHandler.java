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

import java.util.ArrayList;

import ca.ualbert.cs.tasko.Status;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * NotificationHandler deals with the creation of objects of the Notification Type. It acts as a
 * controller object that delgates all activities related to creating Notifications.
 *@see Notification
 *
 * @author spack
 */

public class NotificationHandler {

    private SimpleNotificationFactory simpleNotificationFactory;
    private RatingNotificationFactory ratingNotificationFactory;

    /**
     * The constructor for NotificationHandler sets up the appropriate factories that will handle
     * the creation of notifications.
     * @param context Context is needed to set up the factories (Will be used to communicate with
     *                the DataManager)
     */
    public NotificationHandler(Context context) {
        this.simpleNotificationFactory = new SimpleNotificationFactory();
        this.ratingNotificationFactory = new RatingNotificationFactory();
        simpleNotificationFactory.setContext(context);
        ratingNotificationFactory.setContext(context);

    }


    /**
     * This method is called to create notifications based on bidding, and assigning tasks.
     * @param taskID the ID of the task the notification is related to.
     * @param taskrequestor the taskrequestor who posted the task
     * @param taskprovider the taskprovider who interacts with the task
     * @return This method will return a notification object
     */
    public void newSimpleNotification(String taskID, String taskrequestor
            , String taskprovider) throws NoInternetException {

        simpleNotificationFactory.createNotification(taskID, taskrequestor, taskprovider);
    }

    /**
     * This method is called to create rating notifications sent to both parties upon a tasks completion
     * @param taskID the ID of the task the notification is related to.
     * @param taskrequestor the taskrequestor who posted the task
     * @param taskprovider the taskprovider who interacts with the task
     * @return This function contains an arraylist of two notifications, one to be sent to the
     * task requestor and one to the task provider
     */
    public void newRatingNotification(String taskID, String taskrequestor, String taskprovider)
            throws NoInternetException {

        ratingNotificationFactory.createNotification(taskID, taskrequestor, taskprovider);

    }

}
