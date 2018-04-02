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

import android.content.Context;

import java.util.ArrayList;


import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

import io.searchbox.annotations.JestId;

/**
 * Created by chase on 2/23/2018.
 * Represents a user (which can be both a task requester and task provider). Contains info such
 * as name, username, number, email, tasks bidded on, tasks that you have requested, tasks that
 * you are assigned and their rating.
 * @author imtihan, chase
 *
 */

public class User {
    @JestId private String id;

    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private ArrayList<Float> ratings;



    public User(){

    }

    /**
     * Constructor for a user object. All values are required.
     *
     * @param username username of the user
     * @param name the name of the user
     * @param phoneNumber the user's phone number
     * @param email the user's email
     */
    public User(String username, String name, String phoneNumber, String email){
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ratings = new ArrayList<>(5);

    }

    /**
     * Method that returns the username of the user.
     *
     * @return username of the user
     * @see #setUsername(String)
     */
    public String getUsername() {
        return username;
    }


    /**
     * Method that sets the username of this user.
     *
     * @param username the username of the user
     */
    public void setUsername (String username) {
        this.username = name;
    }

    /**
     * Method the returns the name of the user.
     *
     * @return name of the user
     * @see #setName(String)
     */
    public String getName() {
        return name;
    }

    /**
     * Method that sets the name of the user.
     *
     * @param name the name of the user
     */
    public void setName (String name) {
        this.name = name;
    }


    /**
     * Method that returns the phone number of the user.
     *
     * @return phone number of the user
     * @see #setPhoneNumber(String)
     */
    public String getPhoneNumber() {
        return phoneNumber ;
    }

    /**
     * Method that sets the phone number of the user
     *
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * Method that returns the email of the user
     *
     * @return email of the user
     * @see #setEmail(String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method that sets the email of the user
     *
     * @param email the email of the user
     */
    public void setEmail (String email) {
        this.email = email;
    }

    /**
     * Method that returns the rating of the user
     *
     * @return the rating of the user
     * @see #setRating(float)
     */
    public float getRating() {

        float sum = 0;
        for (int i = 0; i < ratings.size(); i++){
            sum += ratings.get(i);
        }
        return sum/ratings.size();
    }

    /**
     * Method that sets the rating of the user
     *
     * @param rating the rating of the user
     */
    public void setRating (float rating){
        if (ratings.size() == 4){
            ratings.remove(0);
            ratings.add(rating);
        }else
            ratings.add(rating);

    }

    /**
     * Method that returns the userId of the user
     *
     * @return the userId of this user
     * @see #setId(String)
     */
    public String getId(){
        return id;
    }

    /**
     * Method that sets the userId of this user
     *
     * @param id the userId of this user
     */
    public void setId(String id){
        this.id = id;
    }

}
