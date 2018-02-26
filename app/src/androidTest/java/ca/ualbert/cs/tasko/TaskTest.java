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

/**
 * Created by Chase on 2/23/2018.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {
    public TaskTest(){
        super(MainActivity.class);
    }

    public void testacceptBid(){
        User user = new User();
        User secondUser = new User();
        Task task = new Task(user, "atask", "test");
        Bid bid = new Bid(secondUser, 2.56f);
        task.acceptBid(bid);
        assertEquals(task.getStatus(),Status.ASSIGNED);
    }
}
