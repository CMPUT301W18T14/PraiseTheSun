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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;



/**
 * OpeningActivity is the first activity our app will open too, it checks in a local file if a
 * user string exsits and if it does it starts the app at the MainActivity. Otherwise, a user
 * is directed to the LoginActivity.
 *
 * @author spack
 */

public class OpeningActivity extends AppCompatActivity {

    private CurrentUser cu = CurrentUser.getInstance();
    private DataManager dm = DataManager.getInstance();
    private static final String FILENAME = "nfile.sav";
    private String loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * If the current user is already set and we navigate to this activity, then exit the app and
     * set the current user to null, otherwise the app has just started and we go and check
     * for user in the local file.
     */
    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (!cu.loggedIn())
                checkForUser();
            else
                cu.setCurrentUser(null);
                this.finishAffinity();
        } catch (NoInternetException e) {
            e.printStackTrace();
        }
    }

    /**
     * checkForUser is called when the app starts, it looks in a local file to see if a username
     * string exsits and send the user to the appropriate activity depending on if the string
     * exsits or not. If it does exsists it sets that user as the currently logged in user.
     * @throws NoInternetException
     */
    private void checkForUser() throws NoInternetException {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            loggedInUser = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(loggedInUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            cu.setCurrentUser(dm.getUserByUsername(loggedInUser, this));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
