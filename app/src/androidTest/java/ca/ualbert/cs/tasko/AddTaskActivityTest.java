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
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.MockDataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Created by Thomas on 2018-03-04.
 * Testing class for the activity AddTaskActivity
 *
 * @see AddTaskActivity
 * @author tlafranc
 */

public class AddTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private EditText nameText;
    private EditText descriptionText;

    public AddTaskActivityTest() {
        super(AddTaskActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        User taskRequester = MockDataManager.getInstance().getTaskRequester();
        CurrentUser.getInstance().setCurrentUser(taskRequester);
        nameText = (EditText) solo.getView(R.id.addTaskName);
        descriptionText = (EditText) solo.getView(R.id.addTaskDescription);
    }

    public void testTask() {
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);
        solo.enterText(nameText, "Test task");
        solo.enterText(descriptionText, "This is a description for the " +
                "test task");
        solo.clickOnButton("Post your task!");
    }

    public void testEmptyName() {
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);
        solo.enterText(descriptionText, "Description");
        solo.clickOnButton("Post your task!");
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);
    }

    public void testEmptyDescription() {
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);
        solo.enterText(nameText, "Name");
        solo.clickOnButton("Post your task!");
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);
    }

    public void testTransitionAddTask () {
        solo.assertCurrentActivity("Wrong Activity", AddTaskActivity.class);
        solo.enterText(nameText, "Test task");
        solo.enterText(descriptionText, "This is a description for the " +
                "test task");
        solo.clickOnButton("Add Photo");
        solo.assertCurrentActivity("Wrong Activity", AddPhotoActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
