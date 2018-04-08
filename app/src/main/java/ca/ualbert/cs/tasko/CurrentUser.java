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

/**
 * Current User is a singleton that stores the currently logged in user. This allows all other
 * activities to be able to get the current user. It also has a check to see if a user is logged
 * in, which can restrict the app functionality if false is returned. Set in either the Login or
 * OpeningActivity.
 * @see User
 * @see LoginActivity
 * @see OpeningActivity
 *
 * @author spack
 */
public class CurrentUser {

    /**
     * Stores a instance of the CurrentUser Singelton in the instance variable.
     */
    private static final CurrentUser Instance = new CurrentUser();

    /**
     * Initials a User.
     */
    private User currentUser;

    private boolean inApp = false;

    public void setInApp(boolean inApp) {
        this.inApp = inApp;
    }

    public boolean getInApp(){
        return inApp;
    }

    /**
     * By calling this method, any java class can get an instance of the CurrentUser singleton.
     * @return Instance, which is an instance of a CurrentUser singleton
     */
    public static CurrentUser getInstance() {
        return Instance;
    }

    /**
     * Default Constructor, labeled private so only one singleton can be created at a time.
     */
    private CurrentUser() {
    }

    /**
     * Sets the Current user to the User passed in.
     * @param user The User object that will be set as the CurrentUser.
     */
    public void setCurrentUser(User user){
        currentUser = user;
    }

    /**
     * Returns the currently logged in User object.
     * @return currently logged in User object currentUser.
     */
    public User getCurrentUser(){
        return currentUser;
    }

    /**
     * A method that checks if a User object has been set in the CurrentUser, This indicates whether
     * A User has "logged in" or not.
     * @return True If a User has been Set, False if not.
     */
    public boolean loggedIn(){
        if (currentUser == null){
            return false;
        }else{ return true; }
    }
}
