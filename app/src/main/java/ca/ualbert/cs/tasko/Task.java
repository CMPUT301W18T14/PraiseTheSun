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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Represents a task object. Contains the name of the task (max 40 characters), its description
 * (max 350 characters), the photos attached to it, its geolocation, the ID of the user who
 * requested the task, ID of the user who provided the task as well as it's current status which
 * can be one of requested, bidded, assigned or done.
 *
 */

public class Task {

    private String taskName;
    private String description;
    private ArrayList<Bitmap> photos;
    private Location geolocation;
    private String taskRequesterID;
    private String taskProviderID;
    //private BidList bidList;
    private Status status;

    @JestId
    private String id;

    /**
     * Basic constructor of a task object that contains all the required information for any task
     * object which is the userID of the task requester, name of the task and the tasks description.
     *
     * @param taskRequesterID userID of the task requester
     * @param taskName name of the task
     * @param description description of the task
     */
    public Task(String taskRequesterID, String taskName, String description){
        this(taskRequesterID, taskName, description, null, null);
    }

    /**
     * More advanced constructor of a task object that contains all the required information for
     * any task object which is the userID of the task requester, name of the task and the tasks
     * description as well as the geolocation for this task.
     *
     * @param taskRequesterID userID of the task requester
     * @param taskName name of the task
     * @param description description of the task
     * @param location geolocation of the task
     */
    public Task(String taskRequesterID, String taskName, String description,
                Location location){
        this(taskRequesterID, taskName, description, null, location);
    }

    /**
     * More advanced constructor of a task object that contains all the required information for
     * any task object which is the userID of the task requester, name of the task and the tasks
     * description as well as the photos attached to this task.
     *
     * @param taskRequesterID userID of the task requester
     * @param taskName name of the task
     * @param description description of the task
     * @param photos photos attached to this task
     */
    public Task(String taskRequesterID, String taskName, String description,
                ArrayList<Bitmap> photos){
        this.taskRequesterID = taskRequesterID;
        this.taskName = taskName;
        this.description = description;
        this.photos = photos;
        this.geolocation = null;
        this.taskProviderID = null;
        this.status = Status.REQUESTED;
    }

    /**
     * Complete constructor of a task object that contains all the required information for
     * any task object which is the userID of the task requester, name of the task and the tasks
     * description as well as the geolocation for this task and the photos attached to this task.
     *
     * @param taskRequesterID userID of the task requester
     * @param taskName name of the task
     * @param description description of the task
     * @param photos photos attached to this task
     * @param location geolocation of the task
     */
    public Task(String taskRequesterID, String taskName, String description,
                ArrayList<Bitmap> photos, Location location){
        this.taskRequesterID = taskRequesterID;
        this.taskName = taskName;
        this.description = description;
        this.photos = photos;
        this.geolocation = location;
        this.taskProviderID = null;
        this.status = Status.REQUESTED;
    }

    // Not implemented yet
    // Todo Part 5
    public void addPhoto(Bitmap photo){

    }

    // Not implemented yet
    // Todo Part 5
    public void removePhoto(int index){

    }

    // Not implemented yet
    // Todo Part 5
    public void addLocation(Location location){

    }

    // Not implemented yet
    // Todo Part 5
    public void removeLocation(){

    }

    /**
     * Method which sets the status for this Task.
     *
     * @param status status of the object
     */
    public void setStatus(Status status){
        this.status = status;
    }

    /**
     * Method which returns the status of this task.
     *
     * @return the status of this task
     * @see #setStatus(Status)
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Method which returns the name of this task.
     *
     * @return the name of this task
     * @see #setTaskName(String)
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Method which sets the name of this task.
     *
     * @param taskName the name of this task
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Method which returns the description of this task.
     *
     * @return the description of this task
     * @see #setDescription(String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method which sets the description of this task.
     *
     * @param description the description of this task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method which returns the geolocation of this task.
     *
     * @return the geolocation of this task
     * @see #setGeolocation(Location)
     */
    public Location getGeolocation() {
        return geolocation;
    }

    /**
     * Method which sets the geolocation of this task
     *
     * @param geolocation the location of this task
     */
    public Location setGeolocation(Location geolocation) {
        this.geolocation = geolocation;
    }

    /**
     * Method which returns the userID of the task requester
     *
     * @return userID of the task requester
     */
    public String getTaskRequesterID() {
        return taskRequesterID;
    }

    /**
     * Method which return the id of this task
     *
     * @return the taskID for this task
     */
    public String getId() {
        return id;
    }

    /**
     * Method which sets the id of this task
     *
     * @param id the taskID for this task
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method which returns the userID of the task provider
     *
     * @return userID of the task provider
     * @see #assign(String)
     */
    public String getTaskProviderID() {
        return taskProviderID;
    }

    /**
     * Method which assigns this task to a task provider
     *
     * @param taskProviderID the userID of the task provider
     */
    public void assign(String taskProviderID) {
        this.taskProviderID = taskProviderID;
        this.status = Status.ASSIGNED;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Task)){
            return false;
        }
        return this.getId().equals(((Task)o).getId());
    }
}
