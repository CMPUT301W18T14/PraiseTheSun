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

package ca.ualbert.cs.tasko.NotificationArtifacts;

import ca.ualbert.cs.tasko.User;
import io.searchbox.annotations.JestId;

/**
 * A rating notification object. I made this a different type than a simple notification object
 * because I was thinking that if a user were to click on the notification it should create a
 * rating screen which would not happen with a regular notification.
 * Created by spack on 2018-03-10.
 */

public class RatingNotification extends Notification{


    public RatingNotification(String message, User recipient){
        super(message, recipient);
    }

    @JestId
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}