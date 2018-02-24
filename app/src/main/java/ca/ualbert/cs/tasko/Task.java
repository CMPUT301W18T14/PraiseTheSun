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

import android.location.Location;
import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Chase on 2/23/2018.
 */

public class Task {

    private String taskName;
    private String description;
    private ArrayList<Image> photos;
    private Location geolocation;
    private User taskRequester;
    private ArrayList<Bid> bidList;
    private Status status;

    public Task(User taskRequester, String taskName, String description){
        this(taskRequester, taskName, description, null, null)
    }

    public Task(User taskRequester, String taskName, String description, ArrayList<Image> photos){
        this(taskRequester, taskName, description, photos, null);
    }

    public Task(User taskRequester, String taskName, String description, Location location){
        this(taskRequester, taskName, description, null, location);
    }

    public Task(User taskRequester, String taskName, String description, ArrayList<Image> photos,
                Location location){

    }
}
