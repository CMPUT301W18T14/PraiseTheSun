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

import ca.ualbert.cs.tasko.Status;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * Created by spack on 2018-02-23.
 * NotificationHandler deals with the creation of objects of the Notification Type. Essentially
 * A middle man to communicate with the Factories.
 *
 * Currently I am using Users as parameters, I can change this to UserIDs pretty easily if we think
 * thats a good idea, I just was not sure the best way to integrate that with the data manager so
 * I kept it simple.
 */

public class NotificationHandler {

    private ArrayList<RatingNotification> notifications = new ArrayList<>();
    private SimpleNotificationFactory simpleNotificationFactory;
    private RatingNotificationFactory ratingNotificationFactory;

    /**
     * Currently there are 3 constructors. One for each possible factory combination we could use
     * @param nf This represents the Factory Object we pass in, a Notification Factory
     */
    public NotificationHandler(SimpleNotificationFactory nf) {

        this.simpleNotificationFactory = nf;

    }

    public NotificationHandler(RatingNotificationFactory rnf) {

        this.ratingNotificationFactory = rnf;

    }

    public NotificationHandler(SimpleNotificationFactory nf, RatingNotificationFactory rnf) {

        this.simpleNotificationFactory = nf;
        this.ratingNotificationFactory = rnf;

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
