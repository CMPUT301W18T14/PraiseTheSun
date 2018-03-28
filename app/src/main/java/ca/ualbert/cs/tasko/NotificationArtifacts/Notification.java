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

import ca.ualbert.cs.tasko.User;
import io.searchbox.annotations.JestId;

/**
 * The super class representing a generic notification object.
 * Created by spack on 2018-03-10.
 */

public class Notification {



    @JestId
    private String id;

    private NotificationType type;
    private String message;
    private String recipientID;
    private String senderID;
    private String taskID;
    private Boolean hasSeen;


    public Notification(String message, String recipient, String sender,
                        String taskID, NotificationType Type){
        this.type = Type;
        this.message = message;
        this.recipientID = recipient;
        this.senderID = sender;
        this.taskID = taskID;
        this.hasSeen = false;

    }

    public String getMessage(){
        return message;
    }

    public String getRecipientID(){
        return recipientID;
    }

    public String getTaskID(){
        return taskID;
    }

    public String getSenderID() {return senderID;}

    public NotificationType getType() {return type;}

    public Boolean getHasSeen() {return hasSeen;}

    public void hasSeen(){
        hasSeen = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
