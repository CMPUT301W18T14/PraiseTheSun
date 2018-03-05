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

package ca.ualbert.cs.tasko;

import java.util.ArrayList;

import static ca.ualbert.cs.tasko.Status.BIDDED;

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

public class Notification {

    private ArrayList<User> recipients;
    private String taskname;
    private String message;

    public Notification(Task task){

        this.recipients = new ArrayList<User>();

        this.taskname = task.getTaskName();

        Status status = task.getStatus();
        // Creates the appropriate message given a tasks status
        switch (status){
            case REQUESTED:
                this.recipients.add(task.getTaskRequester());
                this.message = "Default Message for Testing";
                break;
            case BIDDED:
                this.recipients.add(task.getTaskRequester());
                this.message = "You have received a new Bid on" + taskname;
                break;
            case ASSIGNED:
                this.recipients.add(task.getTaskProvider());
                this.message = "You have been assigned to complete" + taskname;
                break;
            case DONE:
                this.recipients.add(task.getTaskRequester());
                this.recipients.add(task.getTaskProvider());
                this.message = "Please rate ..."; // No idea how to implement this, embedded in this
                //message should be something that when opened directs the user to a rating activity

        }

         sendNotification();

    }

    public String getMessage() {
        return message;
    }

    public String getTaskname() {return taskname; }

    private void sendNotification(){
        for (int i = 0; i < recipients.size(); i++){
            recipients.get(i).addNotification(this);
        }
    }

}
