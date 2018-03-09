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

import ca.ualbert.cs.tasko.data.DataCommandManager;
import ca.ualbert.cs.tasko.data.DataManager;

/**
 * Created by chase on 3/9/2018.
 */

public class DataManagerTaskTest extends ActivityInstrumentationTestCase2 {

    private User requester;
    private Task task1;
    private Task task2;
    private DataManager dm;

    public DataManagerTaskTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp(){
        dm = DataManager.getInstance();
        requester = new User("requester", "Ima Requester", "4567891234", "gimmieRequests@example" +
                ".com");
        task1 = new Task(requester, "A Task", "Descriptionononion");
        task2 = new Task(requester, "Different Name", "Explination");

    }

    public void testPutTask(){
        dm.putTask(task1, getActivity().getApplicationContext());
    }
}
