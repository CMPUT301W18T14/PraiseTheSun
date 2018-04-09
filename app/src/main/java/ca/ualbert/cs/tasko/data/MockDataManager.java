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

import java.util.UUID;

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.BidList;
import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.TaskStatus;
import ca.ualbert.cs.tasko.User;

/**
 * MockDataManager class that serves as a mock DataManager class and returns
 * fake information to the test classes for testing purposes
 *
 * @author tlafranc
 */
public class MockDataManager {
    private static MockDataManager instance = new MockDataManager();
    private User user1;
    private User user2;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;
    private TaskList taskList;
    private Bid bid1;
    private Bid bid2;
    private Bid bid3;
    private Bid bid4;
    private Bid bid5;
    private BidList bidList;

    /**
     * Construct the singleton and create a DataCommandManager for use
     * throughout this class
     */
    private MockDataManager() {
        // Mock users
        user1 = new User("taskReq", "Task Requester", "task@req.ca", "777-777-7777");
        user1.setId(UUID.randomUUID().toString());
        user2 = new User("taskPro", "Task Provider", "task@pro.ca", "888-888-8888");
        user2.setId(UUID.randomUUID().toString());

        // Mock tasks
        task1 = new Task(user1.getId(),"Task Name 1", "Description for test task. Has " +
                "status bidded. This task is not real and will never ever be used.");
        task1.setId(UUID.randomUUID().toString());
        task2 = new Task(user1.getId(),"Task Name 2", "This is a different description for " +
                "test task. Has status assigned. This task is not real and will never ever be " +
                "used.");
        task2.setId(UUID.randomUUID().toString());
        task3 = new Task(user1.getId(),"Task Name 3", "TEST TASSSSSSKKSKSKSSKSKSKSKSKSKSKSK. " +
                "Task has status requested.");
        task3.setId(UUID.randomUUID().toString());
        task4 = new Task(user1.getId(),"Task Name 4", "TEST TASSSSSSKKSKSKSSKSKSKSKSKSKSKSK. " +
                "Task has status done.");
        task4.setId(UUID.randomUUID().toString());

        // Mock TaskList
        taskList = new TaskList();
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);

        // Mock bids
        bid1 = new Bid(user2.getId(), 10, task1.getId());
        bid1.setBidID(UUID.randomUUID().toString());
        task1.setStatus(TaskStatus.BIDDED);
        bid2 = new Bid(user2.getId(), 100, task1.getId());
        bid2.setBidID(UUID.randomUUID().toString());

        bid3 = new Bid(user2.getId(), 90, task2.getId());
        bid3.setBidID(UUID.randomUUID().toString());
        task2.assign(user2.getId());
        bid4 = new Bid(user2.getId(), 100, task2.getId());
        bid4.setBidID(UUID.randomUUID().toString());

        bid5 = new Bid(user2.getId(), 30, task2.getId());
        bid5.setBidID(UUID.randomUUID().toString());
        task4.assign(user2.getId());
        task4.setStatus(TaskStatus.DONE);

        // Mock BidList
        bidList = new BidList();
        bidList.addBid(bid1);
        bidList.addBid(bid2);
        bidList.addBid(bid3);
        bidList.addBid(bid4);
    }

    public static MockDataManager getInstance(){
        return instance;
    }

    public User getUser() {
        return user1;
    }

    public User getTaskRequester() {
        return user1;
    }

    public User getTaskProvider() {
        return user2;
    }

    public Task getTask() {
        return task1;
    }

    public Task getBiddedTask() {
        return task1;
    }

    public Task getAssignedTask() {
        return task2;
    }

    public Task getRequestedTask() {
        return task3;
    }

    public Task getDoneTask() {
        return task4;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public BidList getBidList() {
        return bidList;
    }
}
