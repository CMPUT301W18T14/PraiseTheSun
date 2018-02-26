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

import static ca.ualbert.cs.tasko.Status.BIDDED;

/**
 * Created by spack on 2018-02-23.
 */

public class Notification {

    User recipient; //Maybe an ArrayList of users to handle multiple recipients...
    String taskname;
    String message;

    Notification(Task task){

        this.taskname = task.getTaskName();
        this.recipient = task.getTaskRequester(); //Default

        Status status = task.getStatus();
        switch (status){
            case REQUESTED:
                this.message = "Default Message for Testing";
                break;
            case BIDDED:
                this.recipient = task.getTaskRequester();
                this.message = "You have received a new Bid on" + taskname;
                break;
            case ASSIGNED:
                // this.recipient = task.getTaskProvider();
                this.message = "You have been assigned to complete" + taskname;
                break;
            case DONE:
                // This would be a special case because we want to send a "rating notification" to
                // both the task requestor and provider
                this.message = "Please rate ..."; //No idea how to implement this....

        }
    }

    public String getMessage() {
        return message;
    }

    public String getTaskname() {return taskname; }

    public void sendNotification(Notification notification){
        //send to recipient
    }

}
