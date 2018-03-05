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

import static org.apache.commons.lang3.ObjectUtils.min;

/**
 * Created by chase on 2/23/2018.
 */

public class BidList{

    private ArrayList<Bid> bids;

    public BidList(){
        bids = new ArrayList<>();
    }

    public void addBid(Bid bid){
        bids.add(bid);
    }

    public Bid getBid(User provider){
        for (int i = 0; i < bids.size(); i++){
            if (bids.get(i).getTaskProvider() == provider){
                return bids.get(i);
            }
        }

    //Throw exception maybe?
    return null;
    }

    public Bid getMinBid(){
        Collections.sort(bids);
        return bids.get(0);
    }

    public Boolean hasBid(Bid bid){
        return bids.contains(bid);
    }

    // Does Not need any Exception Handling, Trying to remove an Object That Does not exists does
    // not cause an error.
    public void removeBid(Bid bid){
        bids.remove(bid);
    }

    public ArrayList<Bid> getBids(){
        return bids;
    }

    public ArrayList<Bid> sortBids() {
        Collections.sort(bids);
        return bids;
    }

    //Mainly for testing below this point
    public Bid get(int index){
        return bids.get(index);
    }

    public int size(){
        return bids.size();
    }

}
