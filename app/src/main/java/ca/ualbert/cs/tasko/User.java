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

import io.searchbox.annotations.JestId;

/**
 * Created by chase on 2/23/2018.
 * Represents a user (which can be both a task requester and task provider). Contains info such
 * as name, username, number, email, tasks bidded on, tasks that you have requested, tasks that
 * you are assigned and their rating.
 *
 *
 */

public class User {

    private String username;
    private String name;
    private String phoneNumber;
    private String email;

    private BidList userBids;
    private TaskList myTasks;
    private TaskList assignments;
    private Float rating;
    private ArrayList<Notification> notifications;
    private float rating;

    @JestId
    private String id;

    public User(){
    }

    public User(String username, String name, String phoneNumber, String email){
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userBids = null;
        this.myTasks = null;
        this.assignments = null;
        this.notifications = new ArrayList<>();
        this.rating = null;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername (String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }


    public String getPhoneNumber() {
        return phoneNumber ;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void addBid(Bid bid) {
        /*TODO: Add bid to server through DataManager*/
    }

    public BidList getBids() {
        return new BidList();
    }


    public void addMyTask(Task task) {
        /*TODO: Implement this*/
    }

    public TaskList getMyTasks() {
        return new TaskList();
    }


    public TaskList getAssignments () {
        return new TaskList();
    }


    public ArrayList<Notification> getNotifications () {
        return new ArrayList<>();
    }

    public float getRating() {
        return rating;
    }

    public void setRating (float rating) throws IllegalArgumentException{
        if(rating < 0 || rating > 10){
            throw new IllegalArgumentException("Argument must be between 0 and 10 inclusive.");
        }
        this.rating = rating;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}
