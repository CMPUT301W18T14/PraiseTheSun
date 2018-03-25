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
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * A factory the creates the appropriate rating notifications and returns them in an arraylist.
 * Created by spack on 2018-03-10.
 */

public class RatingNotificationFactory {

    private DataManager dm = DataManager.getInstance();

    public void createNotification(String taskID, String taskrequestorID, String taskproviderID)
            throws NoInternetException {

        RatingNotification providerNotification;
        RatingNotification requestorNotification;
        String message;
        String taskname = dm.getTask(taskID, this).getTaskName();
        User taskprovider = dm.getUserById(taskproviderID, this);
        User taskrequestor = dm.getUserById(taskrequestorID, this);

        message = taskprovider.getUsername() + " has completed " + taskname
                + ". Please rate their services";
        providerNotification = new RatingNotification(message, taskrequestorID, taskID);
        dm.putNotification(providerNotification, this);

        message = "You have completed " + taskname + ". Please rate your experience with "
                + taskrequestor.getUsername();
        requestorNotification = new RatingNotification(message, taskproviderID, taskID);
        dm.putNotification(requestorNotification, this);

    }
}
