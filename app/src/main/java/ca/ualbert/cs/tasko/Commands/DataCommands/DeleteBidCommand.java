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

import ca.ualbert.cs.tasko.Bid;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Delete;

/**
 * Created by chase on 3/24/2018.
 */

public class DeleteBidCommand extends DeleteCommand {

    private String bid;

    public DeleteBidCommand(String bid){
        this.bid = bid;
    }

    @Override
    public void execute() {
        DeleteBidAsycTask delete = new DeleteBidAsycTask();
        delete.execute(bid);
    }

    @Override
    public void undo() {

    }

    private class DeleteBidAsycTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            JestWrapper.verifySettings();

            Delete delete = new Delete.Builder(strings[0]).index(JestWrapper.getIndex()).type
                    ("bid").build();
            try {
                JestWrapper.getClient().execute(delete);
            } catch (IOException e){
                Log.i("Delete Bid Command", "Could not execute the delete bid Jest statement");
            }

            return null;
        }
    }
}
