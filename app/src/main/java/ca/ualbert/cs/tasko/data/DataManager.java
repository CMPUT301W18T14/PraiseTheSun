/*
 * DataManager
 *
 * March 15, 2018
 *
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

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.BidList;

import ca.ualbert.cs.tasko.Commands.DataCommands.DeleteBidCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.DeleteTaskCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetNotificationsCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetTaskCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserByIdCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserByUsernameCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserTasksCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.PutNotificationCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.PutTaskCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetTaskBidsCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserBidsCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.PutBidCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.PutUserCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.SearchTasksCommand;
import ca.ualbert.cs.tasko.CurrentUser;
import ca.ualbert.cs.tasko.NotificationArtifacts.Notification;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationList;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.User;

/**
 * The DataManager class is built to provide an abstraction to elastic search
 * functionality. This class provides a singleton interface with various
 * methods to provide a simple way to store and retrieve data from the
 * elastic search index.
 *
 * @author Chase Buhler
 * @author Thomas Lafrance
 * @version 1
 * @see ca.ualbert.cs.tasko.Commands.DataCommands
 * @see DataCommandManager
 */
public class DataManager {
    private static DataManager instance = new DataManager();
    private DataCommandManager dcm;
    private Context appCtx;

    /**
     * Construct the singleton and create a DataCommandManager for use
     * throughout this class
     */
    private DataManager(){
        dcm = DataCommandManager.getInstance();
    }

    /**
     * Return the singleton instance of the DataManager
     * @return the single DataManager Object
     */
    public static DataManager getInstance(){
        return instance;
    }

    public void init(Context context){
        appCtx = context.getApplicationContext();
    }

    /**
     * Given a User, attempt to add the user to the elastic search database.
     *  @param user user to be inserted
     *
     */
    public void putUser(User user) throws NoInternetException{
        PutUserCommand command = new PutUserCommand(user);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(command);

        } else {
            throw new NoInternetException();
        }
    }

    /**
     * Given a userID, attempt to retrieve a User object from elastic search
     * based on the provided UUID string.
     *
     * @param id UUID of the desired user
     * @return The user found on the database or an empty user if not found.
     * @throws NoInternetException when the device has no internet.
     * @see GetUserByIdCommand
     */
    public User getUserById(String id) throws NoInternetException{
        GetUserByIdCommand command = new GetUserByIdCommand(id);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(command);
            return command.getResult();

        } else {
            throw new NoInternetException();
        }
    }

    /**
     * Given a username, attempt to retrieve a User object from elastic
     * search based on the provided username.
     *
     * @param username username of desired user
     * @return The found user or an empty user if not found
     * @throws NoInternetException when the device has no internet.
     */
    public User getUserByUsername(String username)
            throws NoInternetException{
        GetUserByUsernameCommand command =
                new GetUserByUsernameCommand(username);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(command);

            return command.getResult();
        } else {
            throw new NoInternetException();
        }
    }

    //TODO part 5
    public void deleteUser(String userId){

    }

    /**
     * Given a task, add it to the elasticSearch Database
     *
     * @param task task to be added
     * @throws NoInternetException when not connected to the internet.
     */
    public void putTask(Task task) throws NoInternetException{
        PutTaskCommand command = new PutTaskCommand(task);
        if(ConnectivityState.getConnected()){
            //TODO: WRITE THIS TASK TO LOCAL STORAGE
            dcm.invokeCommand(command);
        } else {
            //TODO: WRITE THIS TASK TO LOCAL STORAGE
            dcm.addToQueue(command);
            throw new NoInternetException();
        }

    }

    /**
     * Given a task with a non-null id, execute the command to remove that task
     * from the elastic search database.
     *  @param task task to be deleted
     *
     */
    public void deleteTask(final Task task) throws NoInternetException{
        DeleteTaskCommand dtc = new DeleteTaskCommand(task);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(dtc);
        } else {
            dcm.addToQueue(dtc);
            throw new NoInternetException();
        }
    }

    /**
     * Given a taskID, attempt to retrieve the task object from elastic search
     * based on the provided UUID string.
     *
     * @param taskId UUID of the desired task
     * @return the found task object or null if not found
     * @throws NoInternetException when not connected to the internet
     */
    public Task getTask(String taskId)
            throws NoInternetException{
        GetTaskCommand command = new GetTaskCommand(taskId);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(command);
            return command.getResult();

        } else {
            throw new NoInternetException();
        }
    }

    /**
     * Given a string containing search terms, attempt to get the first 100
     * tasks from elastic search who's description contains all the terms in
     * the searchTerm string.
     *
     * @param searchTerm string containing terms to search for in the
     *                   description of a task
     * @return a TaskList of all tasks who's description contains all terms in
     * searchTerm
     * @throws NoInternetException when not connected to the internet.
     */
    public TaskList searchTasks(String searchTerm)
            throws NoInternetException{
        SearchTasksCommand command = new SearchTasksCommand(searchTerm);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(command);
            TaskList tl = command.getResult();
            TaskList toRemove = new TaskList();
            for(Task t: tl.getTasks()){
                if(CurrentUser.getInstance().getCurrentUser().getId()
                        .equals(t.getTaskRequesterID())){
                    //toRemove.addTask(t);
                }
            }
            tl.getTasks().removeAll(toRemove.getTasks());
            return tl;
        } else {
            throw new NoInternetException();
        }
    }

    /**
     * Given a userID, attempt to get all the tasks that have the requesterID
     * matching the provided userId.
     *
     * @param userId userID of the user who's tasks are requested
     * @return TaskList of the tasks containing userId as the requesterID
     * @throws NoInternetException
     */
    public TaskList getUserTasks(String userId) throws NoInternetException{
        GetUserTasksCommand command = new GetUserTasksCommand(userId);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(command);
            return command.getResult();
        } else {
            throw new NoInternetException();
        }
    }

    /**
     * addBid will attempt to store a bid into the database.
     *
     * @param bid the bid object that must be stored in the database
     * @throws NoInternetException Thrown if user does not have a valid internet connection.
     * @author tlafranc
     */
    public void addBid(Bid bid) throws NoInternetException{
        PutBidCommand putBidCommand = new PutBidCommand(bid);
        if (ConnectivityState.getConnected()) {
            dcm.invokeCommand(putBidCommand);
        } else {
            throw new NoInternetException();
        }

    }

    /**
     * getUserBids will return all bids associated to the userId given as a parameter.
     *
     * @param userId the userID associated to the bids in the returned BidList
     * @return A BidList containing all bids associated with the user that was given as a parameter.
     * @throws NoInternetException Thrown if user does not have a valid internet connection.
     * @author tlafranc
     */
    public BidList getUserBids(String userId) throws NoInternetException{
        GetUserBidsCommand command = new GetUserBidsCommand(userId);
        if (ConnectivityState.getConnected()) {
            dcm.invokeCommand(command);
            return command.getResult();
        }
        else {
            throw new NoInternetException();
        }
    }

    /**
     * getTaskBids will return all bids associated to the userID given as a parameter.
     *
     * @param taskId the taskId associated to the bids in the returned BidList
     * @return A BidList containing all bids associated with the task that was given as a parameter.
     * @throws NoInternetException Thrown if user does not have a valid internet connection.
     * @author tlafranc
     */
    public BidList getTaskBids(String taskId) throws NoInternetException{
        GetTaskBidsCommand command = new GetTaskBidsCommand(taskId);
        if (ConnectivityState.getConnected()) {
            dcm.invokeCommand(command);
            return command.getResult();
        }
        else {
            throw new NoInternetException();
        }
    }

    //TODO Part 5
    public void deleteBid(Bid bid) throws NoInternetException{
        DeleteBidCommand dbc = new DeleteBidCommand(bid);
        if(ConnectivityState.getConnected()){
            dcm.invokeCommand(dbc);
        } else {
            dcm.addToQueue(dbc);
            throw new NoInternetException();
        }
    }

    public void putNotification(Notification notification)
            throws NoInternetException{
        if(ConnectivityState.getConnected()){
            PutNotificationCommand pnc = new PutNotificationCommand(notification);
            dcm.invokeCommand(pnc);
        } else {
            throw new NoInternetException();
        }
    }

    public NotificationList getNotifications(String userId)
            throws NoInternetException{
        if(ConnectivityState.getConnected()){
            GetNotificationsCommand gnc = new GetNotificationsCommand(userId);
            dcm.invokeCommand(gnc);
            return gnc.getResult();
        } else {
            throw new NoInternetException();
        }
    }

    //TODO
    public void deleteNotification(String userId, Context context){

    }

    /**
     * isOnline will check the network info and verify that we are connected
     *
     * @param context Application Context
     * @return true when connected to wifi, false otherwise.
     *
    private boolean isOnline(Context context){
        /*
        Retrieved on 04-03-2018
        https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
         *
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            return activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET
                    || activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.getType() == ConnectivityManager
                    .TYPE_MOBILE;
        }
        return false;
    }*/
}
