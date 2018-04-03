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

package ca.ualbert.cs.tasko.Commands.DataCommands;

import android.os.AsyncTask;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;

import ca.ualbert.cs.tasko.TaskList;

/**
 * Created by Thomas on 2018-04-03.
 */

public class GetTasksByLatLng extends GetCommand<TaskList> {
    private Double lat;
    private Double lng;

    public GetTasksByLatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void execute() {
        ArrayList<Double> params = new ArrayList<Double>();
        params.add(lat);
        params.add(lng);
    }

    private static class GetTaskListTask extends AsyncTask<ArrayList<Double>, Void, TaskList> {
        @Override
        protected TaskList doInBackground(ArrayList<Double>... params) {
            return new TaskList();
        }
    }
}
