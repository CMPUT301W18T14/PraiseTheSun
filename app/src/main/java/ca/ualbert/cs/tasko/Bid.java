
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

import ca.ualbert.cs.tasko.data.DataManager;
import io.searchbox.annotations.JestId;

/**
 * Created by Chase on 2/23/2018.
 * Represents a bid Object containing the ID of the bidder, the value they bid and the task they
 * were biddng on.
 *
 * @author Chase Buhler
 * @see Task
 */
public class Bid implements Comparable<Bid>{

    private String TaskID;
    private String UserID;
    private float value;

    @JestId
    private String BidID;

    /**
     * Constructor for Bid, All values are Required
     * @param UserID The UserID of the task provider who placed the bid
     * @param value The value of the bid in dollars, a float
     * @param TaskID The ID of the Task which is being bid on
     */
    public Bid(String UserID, float value, String TaskID){
        this.UserID = UserID;
        this.TaskID = TaskID;
        this.value = value;
    }

    /**
     * Method that returns the userID of the user that placed this bid.
     *
     * @return the userID of the user that placed this bid.
     * @see #setUserID(String)
     */
    public String getUserID(){
        return UserID;
    }

    /**
     * Method that sets the userID for this Bid object. This userID refers to the user who placed
     * this bid
     *
     * @param UserID the userID of the user that placed this bid.
     */
    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    /**
     * Method that returns the taskID of the task that this bid is placed on.
     *
     * @return the taskID of the task that this bid is placed on
     * @see #setTaskID(String)
     */
    public String getTaskID(){
        return TaskID;
    }

    /**
     * Method that sets the taskID for this bid object. This taskId refers to the task that this bid
     * is placed on.
     *
     * @param TaskID the taskID of the task that this is bid is placed on
     */
    public void setTaskID(String TaskID){
        this.TaskID = TaskID;
    }

    /**
     * Method that returns the value of the bid in dollars.
     *
     * @return the value of the bid in dollars
     * @see #setValue(float)
     */
    public float getValue() {
        return value;
    }

    /**
     * Method that sets the value of this bid.
     *
     * @param value the value of the bid in dollars.
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * Implementing Comparable in a way that allows bids to be sorted in order of increasing value
     * @param bid The bids to be sorted
     * @return returns an int (-1, 0, 1) which is used to create sorting order
     */
    @Override
    public int compareTo(Bid bid){
        return Float.compare(this.value, bid.getValue());
    }

    /**
     * Method that returns the BidID of this bid object.
     *
     * @return the BidID of this bid object
     * @see #setBidID(String)
     */
    public String getBidID() {
        return BidID;
    }

    /**
     * Method that sets the ID for this bid object.
     *
     * @param bidID the bidID for this bid object.
     */
    public void setBidID(String bidID) {
        BidID = bidID;
    }
}
