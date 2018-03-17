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

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by chase on 2/23/2018.
 */

public class BidListTest extends ActivityInstrumentationTestCase2 {

    public BidListTest(){
        super(MainActivity.class);
    }

    public void testHasBid(){
        BidList bids = new BidList();
        Bid bid = new Bid("User1", 10.99f, "TestTask1");
        Bid bid2 = new Bid("User2", 9.00f, "TestTask2");


        assertFalse(bids.hasBid(bid));
        bids.addBid(bid);
        assertTrue(bids.hasBid(bid));
        assertFalse(bids.hasBid(bid2));
    }

    public void testAddBid(){
        BidList bids = new BidList();
        Bid bid = new Bid("User1", 10.99f, "TestTask1");

        bids.addBid(bid);
        assertTrue(bids.hasBid(bid));
    }

    public void testRemoveBid(){
        BidList bids = new BidList();
        Bid bid = new Bid("User1", 10.99f, "TestTask1");

        bids.addBid(bid);
        assertTrue(bids.hasBid(bid));
        bids.removeBid(bid);
        assertFalse(bids.hasBid(bid));
    }

    public void testGetBids(){
        BidList bids = new BidList();

        ArrayList<Bid> theBids = bids.getBids();
        assertTrue(theBids != null);
    }

    public void testGetMinBid(){
        BidList bids = new BidList();
        Bid bid = new Bid("User1", 10.99f, "TestTask1");
        Bid bid2 = new Bid("User2", 9.00f, "TestTask2");

        bids.addBid(bid);
        bids.addBid(bid2);

        Bid minBid = bids.getMinBid();
        assertTrue(minBid.equals(bid2));
    }

    public void testGetBidsMatchingUserID(){
        BidList bids = new BidList();
        Bid bid = new Bid("User1", 10.99f, "TestTask1");

        bids.addBid(bid);

        Bid returned = bids.getBid("User1");
        assertTrue(returned.equals(bid));
    }

    public void testSortBids(){
        BidList bids = new BidList();
        Bid bid = new Bid("User1", 10.99f, "TestTask1");
        Bid bid2 = new Bid("User2", 9.00f, "TestTask2");

        bids.addBid(bid);
        bids.addBid(bid2);

        assertNotSame(bids.get(0).getValue(), 9.00f);

        ArrayList<Bid> sortedBids = bids.sortBids();

        assertEquals(sortedBids.get(0).getValue(), 9.00f);

    }

}
