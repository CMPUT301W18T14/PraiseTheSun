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

import ca.ualbert.cs.tasko.Status;
import ca.ualbert.cs.tasko.User;


/**
 * Created by spack on 2018-02-23.
 * Anytime something happens to a Task i.e a status change or a bid is made a called will be made to
 * Create a notification object, this way, we will always have a Task object to pass into the
 * notifications class in order to get necessary info. The body of the notification will change
 * depending on the status of the task. These notifications get auto stored to the respective user,
 * Im not sure if this will work with online connectivity, its something to discuss on monday. I was
 * thinking about something like facebook where on the home page UI there is a little icon indicating,
 * unread notifications that when clicked on expands into a listview of notifications. I am
 * also unsure exactly how the rating notification will work.
 */

public class NotificationHandler {

    private NotificationFactory notificationFactory;

    public NotificationHandler(NotificationFactory nf) {

        this.notificationFactory = nf;

    }

    public SimpleNotification newNotification(Status status, String taskname, User taskrequestor
            , User taskprovider){

        SimpleNotification notification;

        notification = notificationFactory.createNotification(status, taskname, taskrequestor,
                    taskprovider);

        return notification;
    }

}
