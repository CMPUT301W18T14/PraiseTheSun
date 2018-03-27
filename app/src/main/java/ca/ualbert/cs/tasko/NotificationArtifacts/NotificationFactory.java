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

import ca.ualbert.cs.tasko.Status;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

import static ca.ualbert.cs.tasko.Status.REQUESTED;

/**
 * Used to create a simple notification, the body of which is dependent on the status of the task
 * its related to.
 * Created by spack on 2018-03-10.
 */

public class NotificationFactory {

    DataManager dm = DataManager.getInstance();
    Context context;

    public void setContext(Context context){
        this.context = context;
    }

    public void createNotification(String taskID, NotificationType Type) throws NoInternetException{


        Notification notification = null;
        Task task = dm.getTask(taskID, context);

        String TaskID = task.getId();
        String message;
        String taskname = task.getTaskName();
        String recipientID;

        NotificationType notificationType = Type;

        switch (notificationType){
            case TaskRequesterRecievedBidOnTask:
                recipientID = task.getTaskRequesterID();
                message = "You have received a new Bid on" + taskname;
                notification = new Notification(message, recipientID, null, taskID
                        , NotificationType.TaskRequesterRecievedBidOnTask);
                dm.putNotification(notification, context);
                break;
            case TaskProviderBidAccepted:
                recipientID = task.getTaskProviderID();
                message = "You have been assigned to complete" + taskname;
                notification = new Notification(message, recipientID, null, taskID,
                        NotificationType.TaskProviderBidAccepted);
                dm.putNotification(notification, context);
                break;
            case Rating:

                User taskprovider = dm.getUserById(task.getTaskProviderID(), context);
                User taskrequestor = dm.getUserById(task.getTaskRequesterID(), context);

                message = taskprovider.getUsername() + " has completed " + taskname
                        + ". Please rate their services";
                notification = new Notification(message, taskrequestor.getId(), taskID);
                dm.putNotification(notification, context);

                message = "You have completed " + taskname + ". Please rate your experience with "
                        + taskrequestor.getUsername();
                notification = new Notification(message, taskprovider.getId(), taskID);
                dm.putNotification(notification, context);
                break;
        }

        dm.putNotification(notification, context);

    }
}
