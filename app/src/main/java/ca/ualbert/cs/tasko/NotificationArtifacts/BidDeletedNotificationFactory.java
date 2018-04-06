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

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by spack on 2018-04-05.
 */

public class BidDeletedNotificationFactory {

    DataManager dm = DataManager.getInstance();
    Context context;

    public void setContext(Context context){
        this.context = context;
    }

    public void createNotification(String taskID, String recipientID) throws NoInternetException {

        Task task = dm.getTask(taskID, context);
        String taskname = task.getTaskName();
        String message = "Your Bid on " + taskname + " has been Declined. Try making a lower Bid ";
        Notification notification = new Notification(message, recipientID, null, taskID,
                NotificationType.TASK_PROVIDER_BID_DECLINED);
        dm.putNotification(notification, context);
    }
}
