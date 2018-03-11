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

/**
 * Created by spack on 2018-03-10.
 */

public class RatingNotificationFactory {
    public ArrayList<RatingNotification> createNotification(Status currentStatus, String taskName, User taskrequestor
            , User taskprovider) {

        ArrayList<RatingNotification> notifications = new ArrayList<>();
        RatingNotification providerNotification = null;
        RatingNotification requestorNotification = null;
        String message;
        String taskname = taskName;

        message = taskprovider.getUsername() + " has completed " + taskname
                + ". Please rate their services";
        providerNotification = new RatingNotification(message, taskrequestor);
        notifications.add(providerNotification);

        message = "You have completed " + taskname + ". Please rate your experience with "
                + taskrequestor.getUsername();
        requestorNotification = new RatingNotification(message, taskprovider);
        notifications.add(requestorNotification);

        return notifications;

    }
}
