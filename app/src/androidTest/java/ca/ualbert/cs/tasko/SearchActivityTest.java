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

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by spack on 2018-03-14.
 */

public class SearchActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private DataManager dm = DataManager.getInstance();
    private User testUser = new User("username1", "John Doe", "123-456-9999",
            "jdoe@example.com");
    private Task task = new Task("requestorID", "TestTask1",
                            "Help me with recyclerview adapters ahhhhhhh");

    public SearchActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testSearchRecycleView() throws NoInternetException {
        //dm.putTask(task, InstrumentationRegistry.getTargetContext());

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.searchQuery), "Help");
        TaskList foundtasks = dm.searchTasks("Help", InstrumentationRegistry.getTargetContext());
        assertEquals(3, foundtasks.getSize());
        assertEquals("TestTask1", foundtasks.get(0).getTaskName());
        solo.clickOnButton("Search");
        //solo.assertCurrentActivity("Wrong Activity", SearchResultsActivity.class);
    }
}
