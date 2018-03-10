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

import java.util.ArrayList;

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.BidList;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserBidsCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserByIdCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserByUsernameCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.PutBidCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.PutUserCommand;
import ca.ualbert.cs.tasko.Notification;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.User;

/**
 * The DataManager class is built to provide an abstraction to elastic search
 * functionality. This class provides a singleton interface with various
 * methods to provide a simple way to store and retrieve data from the
 * elastic search index.
 */
public class DataManager {

    public static DataManager instance = new DataManager();
    private DataCommandManager dcm;

    private DataManager(){
        dcm = DataCommandManager.getInstance();
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
        PutUserCommand command = new PutUserCommand(user);
        GetUserByUsernameCommand isDuplicate = new GetUserByUsernameCommand(user.getUsername());
        if(isOnline(context)){
            dcm.invokeCommand(isDuplicate);
            if(isDuplicate.getResult().getId() != user.getId()){
                throw new IllegalArgumentException("Can not add duplicate users");
            }
            dcm.invokeCommand(command);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            //TODO: Add to a todoQueue for when we reconnect???
            throw new NoInternetException();
        }
    }

    public User getUserById(String id, Context context) throws NoInternetException{
        context = context.getApplicationContext();
        GetUserByIdCommand command = new GetUserByIdCommand(id);
        if(isOnline(context)){
            dcm.invokeCommand(command);
            return command.getResult();

        } else {
            throw new NoInternetException();
        }
    }

    public User getUserByUsername(String username, Context context) throws NoInternetException{
        context = context.getApplicationContext();
        GetUserByUsernameCommand command = new GetUserByUsernameCommand(username);
        if(isOnline(context)){
            dcm.invokeCommand(command);

            return command.getResult();
        } else {
            throw new NoInternetException();
        }
    }

    //TODO part 5
    public void deleteUser(String userId, Context context){

    }

    //TODO
    public void putTask(Task task, Context context){

    }

    //TODO
    public Task getTask(String taskId, Context context){
        return new Task(null, null, null);
    }

    //TODO
    public TaskList searchTasks(String[] searchParameters, Context context ){
        return new TaskList();
    }

    //TODO
    public TaskList getUserTasks(String userId, Context context){
        return new TaskList();
    }

    //TODO
    public void addBid(Bid bid, Context context) throws NoInternetException{
        context = context.getApplicationContext();
        PutBidCommand putBidCommand = new PutBidCommand(bid);
        if (isOnline(context)) {
            dcm.invokeCommand(putBidCommand);
        } else {
            throw new NoInternetException();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //TODO
    public BidList getUserBids(String userId, Context context) throws NoInternetException{
        context = context.getApplicationContext();
        GetUserBidsCommand command = new GetUserBidsCommand(userId);
        if (isOnline(context)) {
            dcm.invokeCommand(command);
            return command.getResult();
        } else {
            throw new NoInternetException();
        }

    }

    //TODO
    public BidList getTaskBids(String taskId, Context context){
        return new BidList();
    }

    //TODO Part 5
    public void deleteBid(String bidId, Context context){

    }

    //TODO
    public void putNotification(Notification notification, Context context){

    }

    //TODO
    public ArrayList<Notification> getNotifications(String userId, Context context){
        return new ArrayList<>();
    }

    //TODO
    public void deleteNotification(String userId, Context context){

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
        return isConnected;
    }



}
