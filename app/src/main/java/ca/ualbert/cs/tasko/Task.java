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

import android.location.Location;
import android.media.Image;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by Chase on 2/23/2018.
 *
 */

public class Task {

    private String taskName;
    private String description;
    private ArrayList<Image> photos;
    private Location geolocation;
    private User taskRequester;
    private User taskProvider;
    private BidList bidList;
    private Status status;

    @JestId
    private String id;

    public Task(User taskRequester, String taskName, String description){
        this(taskRequester, taskName, description, null, null);
    }

    public Task(User taskRequester, String taskName, String description,
                ArrayList<Image> photos){
        this(taskRequester, taskName, description, photos, null);
    }

    public Task(User taskRequester, String taskName, String description,
                Location location){
        this(taskRequester, taskName, description, null, location);
    }

    public Task(User taskRequester, String taskName, String description,
                ArrayList<Image> photos, Location location){
        this.taskRequester = taskRequester;
        this.taskName = taskName;
        this.description = description;
        this.photos = photos;
        this.geolocation = location;
        this.taskProvider = null;
        this.status = Status.REQUESTED;
    }

    public void acceptBid(Bid bid){

    }

    /*
    public BidList getBids(){
        return bidList;
    }*/

    public void addPhoto(Image photo){

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

    public User getTaskRequester() {
        return taskRequester;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getTaskProvider() {
        return taskProvider;
    }

    public void assign(User taskProvider) {
        this.taskProvider = taskProvider;
        this.status = Status.ASSIGNED;
    }

    public void setTaskProvider (User taskProvider) { this.taskProvider = taskProvider; }

}
