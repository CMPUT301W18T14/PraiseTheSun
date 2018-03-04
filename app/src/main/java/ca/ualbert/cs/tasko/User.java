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
    private String number;
    private String email;
    private TaskList bids;
    private TaskList myTasks;
    private TaskList assignments;
    private Float rating;
    private ArrayList<Notification> notifications;

    public User(){
    }

    public User(String username, String name, String number, String email){
        this.username = username;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public void addNotification(Notification notification){
        notifications.add(notification);
    }
}
