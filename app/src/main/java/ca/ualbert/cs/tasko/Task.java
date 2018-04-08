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
import android.util.Base64;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import io.searchbox.annotations.JestId;

/**
 * Represents a task object. Contains the name of the task (max 40 characters), its description
 * (max 350 characters), the photos attached to it, its geolocation, the ID of the user who
 * requested the task, ID of the user who provided the task as well as it's current status which
 * can be one of requested, bidded, assigned or done.
 *
 */

public class Task implements Serializable {

    private String taskName;
    private String description;
    private ArrayList<String> photos;
    //private Location geolocation;
    private String taskRequesterID;
    private String taskRequesterUsername;
    private String taskProviderID;
    private Float minBid;
    private double lat;
    private double lng;
    private TaskStatus status;


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
        this.taskRequesterID = taskRequesterID;
        this.taskName = taskName;
        this.description = description;
        this.photos = new ArrayList<String>();
        this.taskProviderID = null;
        this.status = TaskStatus.REQUESTED;
        this.minBid = null;
        this.id = UUID.randomUUID().toString();
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
                LatLng location){
        this(taskRequesterID, taskName, description, new ArrayList<String>(), location);
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
                ArrayList<String> photos){
        this.taskRequesterID = taskRequesterID;
        this.taskName = taskName;
        this.description = description;
        this.photos = photos;
        this.taskProviderID = null;
        this.status = TaskStatus.REQUESTED;
        this.minBid = null;
        this.id = UUID.randomUUID().toString();
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
                ArrayList<String> photos, LatLng location){
        this.taskRequesterID = taskRequesterID;
        this.taskName = taskName;
        this.description = description;
        this.photos = photos;
        this.taskProviderID = null;
        this.id = UUID.randomUUID().toString();
        this.lat = location.latitude;
        this.lng = location.longitude;
        this.status = TaskStatus.REQUESTED;
    }

    /**
     * Returns a String representing the UserName of the Task Requester (Used for TaskList Displays)
     * @return String representing the UserName of the Task Requester.
     */
    public String getTaskRequesterUsername() {
        return taskRequesterUsername;
    }

    /**
     * Sets the Task Requester UserName in the Task. Occurs when Task is created.
     * @param taskRequesterUsername The Task Requester UserName
     */
    public void setTaskRequesterUsername(String taskRequesterUsername) {
        this.taskRequesterUsername = taskRequesterUsername;
    }

    /**
     * Returns the minimum bid on a Task (Makes TaskViews where minBids are displayed much faster)
     * @return A Float representing the minimum bid.
     */
    public Float getMinBid(){
        return minBid;
    }

    /**
     * Sets the minimum bid on the task if it is less then the current minimum
     * @param value The value we compare to minBid
     */
    public void setMinBid(Float value) {
        if (minBid == null || value < minBid)
            this.minBid = value;
    }

    /**
     * Sets the minimum bid on the task back to null
     */
    public void setMinBidNull() {
        this.minBid = null;
    }

    // Not implemented yet
    // Todo Part 5
    public boolean hasPhoto(){
        if (photos != null) {
            return photos.size() > 0;
        }
        else {
            return false;
        }
    }

    public Bitmap getCoverPhoto() {
        if (hasPhoto()) {
            byte[] byteArray = Base64.decode(photos.get(0), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        else {
            return null;
        }
    }

    // Not implemented yet
    // Todo Part 5
    public ArrayList<Bitmap> getPhotos(){
        if (hasPhoto()) {
            ArrayList<Bitmap> images = new ArrayList<Bitmap>();
            for (int i = 0; i < photos.size(); i++) {
                byte[] byteArray = Base64.decode(photos.get(i), Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                images.add(image);
            }
            return images;
        }
        else {
            return null;
        }
    }


    /**
     * Sets the location of the task
     * @see LatLng, NearbyTasksActivity
     * @param location
     */
    public void addLocation(LatLng location){
        lat = location.latitude;
        lng = location.longitude;
    }

    public ArrayList<String> getPhotoStrings(){
        return this.photos;
    }


    public ArrayList<byte[]> getByteArrays() {
        if (hasPhoto()) {
            ArrayList<byte[]> imageBytes = new ArrayList<byte[]>();
            for (int i = 0; i < photos.size(); i++) {
                byte[] byteArray = Base64.decode(photos.get(i), Base64.DEFAULT);
                imageBytes.add(byteArray);
            }
            return imageBytes;
        }
        else {
            return null;
        }
    }

    // Not implemented yet
    // Todo Part 5
    public void addLocation(Location location){

    }


    /**
     * Removes the location of the task
     * @see NearbyTasksActivity
     */
    public void removeLocation(){
        this.lat = 0.00f;
        this.lng = 0.00f;
    }

    /**
     * Method which sets the status for this Task.
     *
     * @param status status of the object
     */
    public void setStatus(TaskStatus status){
        this.status = status;
    }

    /**
     * Method which returns the status of this task.
     *
     * @return the status of this task
     * @see #setStatus(TaskStatus)
     */
    public TaskStatus getStatus() {
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
     * @see #addLocation(LatLng)
        */
    public LatLng getGeolocation() {
        LatLng geolocation = new LatLng(lat, lng);
        return geolocation;
    }
/*
    /**
     * Method which sets the geolocation of this task
     *
     * @param geolocation the location of this task
     */
/*    public void setGeolocation(Location geolocation) {
        this.geolocation = geolocation;
    }
*/
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
        this.status = TaskStatus.ASSIGNED;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Task)){
            return false;
        }
        return this.getId().equals(((Task)o).getId());
    }
}
