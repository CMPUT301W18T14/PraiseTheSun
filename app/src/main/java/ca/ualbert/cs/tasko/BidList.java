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
import java.util.Collection;
import java.util.Collections;

/**
 * Created by chase on 2/23/2018.
 * Represents a List of Bid Objects, Has functionality to return minimum Bid, return a bid
 * associated with a UserID, along with typical ArrayList functionality.
 * @see Bid
 */

public class BidList{

    private ArrayList<Bid> bids = new ArrayList<Bid>();

    /**
     * Initializes a Bidlist to be an ArrayList of Bids
     */
    public BidList(){
        bids = new ArrayList<Bid>();
    }

    /**
     * Adds a Bid Object to a Bidlist
     * @param bid a Bid object
     */
    public void addBid(Bid bid){
        bids.add(bid);
    }

    /**
     * Adds multiple bids at once to a BidList
     * @param bids a Bid object
     */
    public void addAll(Collection<Bid> bids) {
        this.bids.addAll(bids);
    }

    /**
     * Get a specific Bid from the BidList that matches with a UserID
     * @param UserID the Users ID used to compare bids in the BidList
     * @return Returns A bid if such a bid is matching, or null if no bid matches with a UserID
     */
    public Bid getBid(String UserID){
        for (int i = 0; i < bids.size(); i++){
            if(bids.get(i).getUserID().compareTo(UserID) == 0){
                return bids.get(i);
            }
        }
        return null;
    }

    /**
     * Sorts the BidList in Ascending Order Based Upon Value and Returns the first Bid.
     * @return The Bid in BidList with the lowest monetary value
     */
    public Bid getMinBid(){
        Collections.sort(bids);
        if(bids.size() <= 0){
            return null;
        }
        return bids.get(0);
    }

    /**
     * Checks if a Bidlist contains a specific Bid
     * @param bid The Bid object that is trying to be found in a BidList
     * @return True if the Bid is in the Bidlist or False if not
     */
    public Boolean hasBid(Bid bid){
        return bids.contains(bid);
    }

    /**
     * Remove a Bid from a BidList
     * @param bid a Bid object
     */
    public void removeBid(Bid bid){
        bids.remove(bid);
    }

    /**
     * Returns the Bidlist
     * @return A Bidlist (ArrayList<Bid>)
     */
    public ArrayList<Bid> getBids(){
        return bids;
    }

    public int getSize(){
        return bids.size();
    }

    /**
     * Sorts Bids in a Bid List based on value of Bid, The lower Bids get out first.
     * @return returns a Sorted BidList in Ascending Order(In terms of Bid Value)
     */
    public ArrayList<Bid> sortBids() {
        Collections.sort(bids);
        return bids;
    }

    /**
     * Get a specific bid from a BidList, based on a index
     * @param index The index of the Bid we are trying to return
     * @return A Bid object found at index in the BidList
     */
    public Bid get(int index){
        return bids.get(index);
    }

}
