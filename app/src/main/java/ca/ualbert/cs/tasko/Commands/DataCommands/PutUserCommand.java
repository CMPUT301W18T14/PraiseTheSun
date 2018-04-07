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
import android.util.Log;

import java.io.IOException;

import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * An extension of PutCommand, PutUserCommand will take a user and attempt to
 * add it to our elastic search database.
 *
 * @author Chase Buhler
 * @see PutCommand
 * @see ca.ualbert.cs.tasko.Commands.Command
 */
public class PutUserCommand extends PutCommand<User> {
    private User user;

    /**
     * Constructor for the PutUserCommand. Requires the parameter user in
     * order to be initialized.
     *
     * @param user the user object to be stored in the database
     */
    public PutUserCommand(User user){
        super(user, "PutUserCommand");
        this.user = user;
    }

    /**
     * Once execute is called. The method first builds a put request and then
     * attempts to add user to the elastic search Database.
     */
    @Override
    public void execute() {
        PutUserTask task =  new PutUserTask();
        task.execute(user);
        try {
            user.setId(task.get());
        } catch (Exception e){
            e.printStackTrace();
            Log.i("Error", "AsyncTask was interrupted or something");
        }
    }

    //TODO
    @Override
    public void undo() {
        //TODO: Implement Delete
    }

    /**
     * Return true because we can undo this command by deleting the user that
     * we added.
     * @return true
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    private class PutUserTask extends AsyncTask<User, Void, String>{

        @Override
        protected String doInBackground(User... users) {

            Index index = new Index.Builder(users[0]).index(JestWrapper.getIndex()).type("user")
                    .build();

            try {
                DocumentResult result = JestWrapper.getClient().execute(index);
                if (result.isSucceeded()) {
                    return result.getId();
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the user");
            }
            return null;
        }
    }
}
