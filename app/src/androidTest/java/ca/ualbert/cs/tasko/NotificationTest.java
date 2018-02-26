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
 * Created by spack on 2018-02-23.
 */

public class NotificationTest extends ActivityInstrumentationTestCase2 {
    public NotificationTest(){
        super(MainActivity.class);
    }

    public void testCreate() {
        User user = new User("StevieP", "Steve", "911", "spacker");
        Task task = new Task(user,  "TestTask1", "Help me test software");
        Notification notification = new Notification(task);

        //Test to see if notification is being created and can get info from the passed in task.
        assertEquals(notification.getTaskname(), "TestTask1");

        //Test to see if notification switch block is working properly.
        assertEquals(notification.getMessage(), "Default Message for Testing");
    }
}
