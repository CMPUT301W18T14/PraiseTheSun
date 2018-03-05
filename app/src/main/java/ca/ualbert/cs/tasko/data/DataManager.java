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

package ca.ualbert.cs.tasko.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import ca.ualbert.cs.tasko.User;

/**
 * The DataManager class is built to provide an abstraction to elastic search
 * functionality. This class provides a singleton interface with various
 * methods to provide a simple way to store and retrieve data from the
 * elastic search index.
 */
public class DataManager {

    public static DataManager instance = new DataManager();

    private DataManager(){

    }

    public static DataManager getInstance(){
        return instance;
    }

    /**
     * putUser will attempt to add a user to the elastic search database.
     * context is used to check for network connectivity.
     * @param user user to be inserted
     * @param context Application Context Object
     * @return boolean indicating success or not
     */
    public void putUser(User user, Context context) throws NoInternetException{
        context = context.getApplicationContext();
        if(isOnline(context)){
            ElasticSearchUserController.AddUserTask addUserTask =
                    new ElasticSearchUserController.AddUserTask();
            addUserTask.execute(user);

            Log.i("Result USER ID MANAGER", user.getId());
        } else {
            throw new NoInternetException();
        }
    }

    public User getUserById(String id, Context context) throws NoInternetException{
        context = context.getApplicationContext();
        if(isOnline(context)){
            ElasticSearchUserController.GetUserByIdTask getUserTask =
                    new ElasticSearchUserController.GetUserByIdTask();
            getUserTask.execute(id);

            try {
                User user = getUserTask.get();
                return user;
            } catch (Exception e){
                Log.i("Error", "Failed to get user from the async object");
            }
        } else {
            throw new NoInternetException();
        }
        return null;
    }

    public User getUserByUsername(String username, Context context){
        return new User();
    }

    /**
     * isOnline will check the netWork info and verify that we are not only connected to a
     * network but that we are connected via wifi.
     * @param context Application Context
     * @return true when connected to wifi, false otherwise.
     */
    private boolean isOnline(Context context){
        /*
        Retrieved on 04-03-2018
        https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
         */
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }



}
