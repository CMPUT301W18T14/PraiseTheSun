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

import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

//Notifications Need to be reworked for part 5... Avoiding in depth documentation until then

public class NotificationTest extends ActivityInstrumentationTestCase2 {
    public NotificationTest() {
        super(MainActivity.class);
    }

    private DataManager dm = DataManager.getInstance();

    private String providerID;
    private String requestorID;
    private Task task;
    private NotificationHandler nh;

    public void setUp() throws NoInternetException {
        nh = new NotificationHandler(getActivity().getApplicationContext());

        User requestor = new User("StevieP", "Steve", "780-450-1000",
                "spacker@ualberta.ca");
        User provider = new User("Stevoo", "Stephen", "780-454-1054",
                "stevooo@ualberta.ca");

        if (dm.getUserByUsername("StevieP", getActivity().getApplicationContext()) == null) {
            dm.putUser(requestor, getActivity().getApplicationContext());
            dm.putUser(provider, getActivity().getApplicationContext());
        }

        requestorID = dm.getUserByUsername("StevieP",
                getActivity().getApplicationContext()).getId();

        providerID = dm.getUserByUsername("Stevoo",
                getActivity().getApplicationContext()).getId();

        task = new Task(requestorID, "TestTask for Notifications", "Notifications");
        dm.putTask(task, getActivity().getApplicationContext());
    }

}
