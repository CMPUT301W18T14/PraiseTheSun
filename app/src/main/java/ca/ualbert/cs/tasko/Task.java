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
 * Created by Chase on 2/23/2018.
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

    public Task(String taskRequesterID, String taskName, String description){
        this(taskRequesterID, taskName, description, null, null);
    }

    public Task(String taskRequesterID, String taskName, String description,
                Location location){
        this(taskRequesterID, taskName, description, null, location);
    }

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
    
    public void addPhoto(Bitmap photo){

    }

    public void removePhoto(int index){

    }

    public void addLocation(Location location){

    }

    public void removeLocation(){

    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getGeolocation() {
        return geolocation;
    }

    public String getTaskRequesterID() {
        return taskRequesterID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskProviderID() {
        return taskProviderID;
    }

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
