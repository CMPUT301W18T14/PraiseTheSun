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
import android.widget.EditText;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;


/**
 * SearchActivity Test test the functionality of the search activity which begins in main activity,
 * and can go to ViewSearchedTaskDetailsActivityTest if a task in the recyclerview displayed in the
 * SearchResultActivity is clicked. By testing Searchactivity, I am also testing that my TaskList
 * Adapter is working and that the DataManger command to search tasks is working.
 * @see SearchResultsActivity
 *
 * @author spack
 *
 */
public class SearchActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private DataManager dm = DataManager.getInstance();

    public SearchActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        User user = new User("rromano", "Ryan", "111-222-3333", "rromano@ualberta.ca");
        CurrentUser.getInstance().setCurrentUser(user);
        try {
            dm.putUser(user);
        }catch(IllegalArgumentException e){
        }

        User dmuser = dm.getUserByUsername("rromano");

        Task task = new Task(dmuser.getId(), "TestTask", "TestMessage");
        dm.putTask(task);

    }

    /**
     * A valid search means that there is a task in the database whose description matches with a
     * value in the keyword string provided by the user.
     * @throws NoInternetException
     */
    public void testValidSearch() throws NoInternetException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.searchQuery), "Help");
        solo.clickOnButton("Search");
        solo.assertCurrentActivity("Wrong Activity", SearchResultsActivity.class);
    }

    /**
     * An Invalid search means that there is no task in the database whose description matches with a
     * value in the keyword string provided by the user.
     * @throws NoInternetException
     */
    public void testInvalidSearch() throws NoInternetException {

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.searchQuery), "Help");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }

    /**
     * Ensures that the recyclerview goes to the appropriate activity when clicked.
     * @throws NoInternetException
     */
    public void testRecyclerViewOnClick() throws NoInternetException {

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.searchQuery), "Help");
        solo.clickOnButton("Search");
        solo.assertCurrentActivity("Wrong Activity", SearchResultsActivity.class);
        solo.clickInRecyclerView(1);
        solo.assertCurrentActivity("Wrong Activity", ViewSearchedTaskDetailsActivity.class);


    }
}
