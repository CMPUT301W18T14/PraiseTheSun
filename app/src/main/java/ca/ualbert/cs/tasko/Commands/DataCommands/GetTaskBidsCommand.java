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

import ca.ualbert.cs.tasko.BidList;

/**
 * Created by Thomas on 2018-03-10.
 */

public class GetTaskBidsCommand extends GetCommand<BidList> {
    private String taskId;

    public GetTaskBidsCommand(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute() {
        String query = "{\"query\":{\"term\":{\"UserID\":\"" + taskId + "\" } } }";
    }

    public void undo() {
        // TODO: Implement delete
    }

    public boolean canUndo() {
        return true;
    }

    public static class GetBidListTask extends AsyncTask<String, Void, BidList> {
        @Override
        protected BidList doInBackground(String... userIds) {
            return null;
        }
    }
}
