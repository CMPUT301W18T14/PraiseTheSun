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

import ca.ualbert.cs.tasko.NotificationArtifacts.AndroidNotificationCreator;

/**
 * Basic Test to make sure AndroidNotificationCreator doe snot produce any crashes. Robotium cannot
 * click on the notification bar so Im a little hamstrung.
 */
public class AndroidNotificationCreatorTest extends ActivityInstrumentationTestCase2 {

    public AndroidNotificationCreatorTest() {
        super(MainActivity.class);
    }

    public void testCreate(){
        AndroidNotificationCreator anc = new AndroidNotificationCreator(getActivity().getApplicationContext());
        anc.createAndriodNotification();
    }
}
