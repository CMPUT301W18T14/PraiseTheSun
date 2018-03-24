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
 * Used to create a simple notification, the body of which is dependent on the status of the task
 * its related to.
 * Created by spack on 2018-03-10.
 */

public class SimpleNotificationFactory {

    public SimpleNotification createNotification(Status currentStatus, String taskName, User taskrequestor
            , User taskprovider) {

        SimpleNotification notification = null;
        String message = null;
        String taskname = taskName;
        User recipient;

        Status status = currentStatus;

        switch (status) {
            case REQUESTED:
                recipient = taskprovider;
                message = "Default Message for Testing";
                notification = new SimpleNotification(message, recipient);
                break;
            case BIDDED:
                recipient = taskrequestor;
                message = "You have received a new Bid on" + taskname;
                notification = new SimpleNotification(message, recipient);
                break;
            case ASSIGNED:
                recipient = taskprovider;
                message = "You have been assigned to complete" + taskname;
                notification = new SimpleNotification(message, recipient);
                break;

        }

        return notification;
    }
}
