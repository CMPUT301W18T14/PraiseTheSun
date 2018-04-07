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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.ualbert.cs.tasko.User;

/**
 * Created by Chase on 2018-04-07.
 */

public class GetUserMapCommand extends GetCommand<Map<String, User>, ArrayList<String>> {
    ArrayList<String> ids;
    public GetUserMapCommand(ArrayList<String> ids){
        super(ids, "GetUserMapCommand");
        this.ids = ids;
    }

    @Override
    public void execute() {
        //from ids list make the query string
        //pass query to the async task
        //similar in the task to searchTerms
        //For each user in the returned list add
        Map<String, User> userMap = new HashMap<String, User>();
        // userMap.put(userid, User object);
    }

    private static class GetUserMapTask extends AsyncTask<String, Void, ArrayList<User>>{

        @Override
        protected ArrayList<User> doInBackground(String... strings) {
            return null;
        }
    }
}
