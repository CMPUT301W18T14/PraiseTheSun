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

import io.searchbox.annotations.JestId;

/**
 * A notification object that has a Type (So the factory knows how to make the Notificatoin), a
 * message containing pertinent information, and info about who to send to and who sent the
 * notification. The actual sending of the notification is done through elastic search and a polling
 * system that uses the hasSeen Boolean to check if the notification is new or not and based on that
 * make a new Android Notification notifying the user things have occurred in Tasko. Notifications
 * are created in a various of scenarios.
 * @see NotificationList
 * @see AndroidNotificationCreator
 * @see ca.ualbert.cs.tasko.data.DataManager
 *
 * @author spack
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

    /**
     * Default constructor for the Notification Class
     * @param message Message containing pertinent information
     * @param recipient A string representing the user the notification will be sent too.
     * @param sender A string representing the user that sent the notification.
     * @param taskID The id of the task the notification is related too
     * @param Type The Type of Notification
     * @see NotificationType
     */
    public Notification(String message, String recipient, String sender,
                        String taskID, NotificationType Type){
        this.type = Type;
        this.message = message;
        this.recipientID = recipient;
        this.senderID = sender;
        this.taskID = taskID;
        this.hasSeen = false;

    }

    /**
     * Get the message
     * @return A string representing the message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Get the recipientID
     * @return A string representing the recipient ID
     */
    public String getRecipientID(){
        return recipientID;
    }

    /**
     * get the TaskID
     * @return A string representing the TaskID
     */
    public String getTaskID(){
        return taskID;
    }

    /**
     * Get the senderID
     * @return A string representing the sender ID
     */
    public String getSenderID() {return senderID;}

    /**
     * get the Task Type
     * @return A string representing the Task Type
     */
    public NotificationType getType() {return type;}

    /**
     * Used for polling. In the database, if a Task is seen, hasSeen will return false and the
     * Notification will trigger an android Notification to be created. Otherwise, It is ignored.
     */
    public Boolean getHasSeen() {return hasSeen;}

    /**
     * Set hasSeen to True
     */
    public void hasSeen(){
        hasSeen = true;
    }

    /**
     * Get the Notification ID
     * @return A string representing the Notification ID
     */
    public String getId() {
        return id;
    }

    /**
     * Set the notification ID
     * @param id the Id we will set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * A method used to compare notification objects with other objects and get the proper id when
     * searching
     * @param o The other object we compare with
     * @return A boolen representing if o is a Notification Object and if so if its id matches this
     * notification object
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Notification)){
            return false;
        } else {
            return ((Notification) o).getId().equals(this.getId());
        }
    }
}
