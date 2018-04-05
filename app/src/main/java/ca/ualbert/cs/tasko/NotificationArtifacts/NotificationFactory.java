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

import ca.ualbert.cs.tasko.BidList;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Handles the creation of all notifications by using a switch block to create unique messages and
 * send the notifications to specific users via the DataManager
 * @see Notification
 *
 * @author spack
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

        String message;
        String taskname = task.getTaskName();
        String recipientID;

        NotificationType notificationType = Type;

        switch (notificationType){
            case TASK_REQUESTOR_RECIEVED_BID_ON_TASK:
                recipientID = task.getTaskRequesterID();
                message = "You have received a new bid on your task: " + taskname + "!";
                notification = new Notification(message, recipientID, null, taskID
                        , NotificationType.TASK_REQUESTOR_RECIEVED_BID_ON_TASK);
                dm.putNotification(notification, context);
                break;
            case TASK_PROVIDER_BID_ACCEPTED:
                recipientID = task.getTaskProviderID();
                message = "You have been assigned to complete " + taskname + "!";
                notification = new Notification(message, recipientID, null, taskID,
                        NotificationType.TASK_PROVIDER_BID_ACCEPTED);
                dm.putNotification(notification, context);
                break;
            case RATING:
                User taskprovider = dm.getUserById(task.getTaskProviderID(), context);
                User taskrequestor = dm.getUserById(task.getTaskRequesterID(), context);

                message = taskprovider.getUsername() + " has completed " + taskname
                        + ". Please rate their services";
                notification = new Notification(message, taskrequestor.getId(), taskprovider.getId(), taskID, NotificationType.RATING);
                dm.putNotification(notification, context);

                message = "You have completed " + taskname + ". Please rate your experience with "
                        + taskrequestor.getUsername();
                notification = new Notification(message, taskprovider.getId(), taskrequestor.getId(), taskID, NotificationType.RATING);
                dm.putNotification(notification, context);
                break;
            case TASK_PROVIDER_BID_DECLINED:
                recipientID = task.getTaskProviderID();
                message = "Your Bid on " + taskname + " has been Declined. Try making a lower Bid ";
                notification = new Notification(message, recipientID, null, taskID,
                        NotificationType.TASK_PROVIDER_BID_DECLINED);
                dm.putNotification(notification, context);
                break;
            case TASK_DELETED:
                BidList deletedBids = dm.getTaskBids(taskID, context);
                for(int i = 0; i < deletedBids.getSize(); i++){
                    message = taskname + "Has been deleted by the poster. Sorry for the inconvience.";
                    notification = new Notification(message, deletedBids.get(i).getUserID(), null, taskID,
                            NotificationType.TASK_DELETED);
                    dm.putNotification(notification, context);
                }
                break;
        }

    }
}
