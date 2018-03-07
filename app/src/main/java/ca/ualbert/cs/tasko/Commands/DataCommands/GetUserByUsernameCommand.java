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

import android.util.Log;

import java.util.ArrayList;

import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.ElasticSearchUserController;

/**
 * Created by chase on 3/7/2018.
 */

public class GetUserByUsernameCommand extends GetCommand<User> {

    private String username;

    public GetUserByUsernameCommand(String username){
        this.username = username;
    }

    @Override
    public void execute() {

        final String query = "{\"query\":{\"term\":{\"username\":\"" + username + "\" } } }";
        ElasticSearchUserController.GetUserByUsernameTask getUserTask =
                new ElasticSearchUserController.GetUserByUsernameTask();
        getUserTask.execute(query);
        try{
            setResult(getUserTask.get());
        } catch (Exception e){
            Log.i("Error", "Failed to get the user by username from the async object");
        }
    }
}
