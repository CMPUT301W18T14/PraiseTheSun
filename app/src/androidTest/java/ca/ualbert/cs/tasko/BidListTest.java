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
    private User provider;
    private User provider2;

    public BidListTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() {
        provider = new User("Bob1234", "Bob", "123-456-7890", "bobby9@cooldomain.com");
        provider2 = new User("JohnDoe", "John Doe", "999-123-4567", "jdoe@example.com");
    }

    public void testHasBid(){
        BidList bids = new BidList();
        Bid bid = new Bid(provider, 10.99f);


        assertFalse(bids.hasBid(bid));
        bids.addBid(bid);
        assertTrue(bids.hasBid(bid));
    }

    public void testAddBid(){
        BidList bids = new BidList();
        Bid bid = new Bid(provider, 10.99f);

        bids.addBid(bid);
        assertTrue(bids.hasBid(bid));
    }

    public void testRemoveBid(){
        BidList bids = new BidList();
        Bid bid = new Bid(provider, 10.99f);

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
        Bid bid = new Bid(provider, 10.99f);
        Bid bid2 = new Bid(provider2, 9.00f);

        bids.addBid(bid);
        bids.addBid(bid2);

        Bid minBid = bids.getMinBid();
        assertTrue(minBid.equals(bid2));
    }

    public void testGetBid(){
        BidList bids = new BidList();
        Bid bid = new Bid(provider, 10.99f);

        Bid returned = bids.getBid(provider);
        assertTrue(returned.equals(bid));
    }
}
