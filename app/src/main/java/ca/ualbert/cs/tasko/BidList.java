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
import java.util.Collections;

/**
 * Created by chase on 2/23/2018.
 * Represents a List of Bid Objects, Has functionality to return minimum Bid, return a bid
 * associated with a UserID, along with typical ArrayList functionality.
 */

public class BidList{

    private ArrayList<Bid> bids;

    public BidList(){
        bids = new ArrayList<>();
    }

    public void addBid(Bid bid){
        bids.add(bid);
    }

    /**
     * Returns a BidList that contains all Bids from a Given UserID
     * @param UserID The UserID is used to search the BidList and find all matching values
     * @return Returns a BidList that Contains all Bids made by a given user in a BidList.
     */

    public Bid getBid(String UserID){
        for (int i = 0; i < bids.size(); i++){
            return bids.get(i);
        }
        return null;
    }

    /**
     * Sorts the BidList in Ascending Order Based Upon Value and Returns the first Bid.
     * @return The Bid in BidList with the lowest monetary value
     */
    public Bid getMinBid(){
        Collections.sort(bids);
        return bids.get(0);
    }

    public Boolean hasBid(Bid bid){
        return bids.contains(bid);
    }

    public void removeBid(Bid bid){
        bids.remove(bid);
    }

    public ArrayList<Bid> getBids(){
        return bids;
    }

    /**
     * Sorts Bids in a Bid List based on value of Bid, The lower Bids get out first.
     * @return returns a Sorted BidList in Ascending Order(In terms of Bid Value)
     */
    public ArrayList<Bid> sortBids() {
        Collections.sort(bids);
        return bids;
    }

    public Bid get(int index){
        return bids.get(index);
    }

}
