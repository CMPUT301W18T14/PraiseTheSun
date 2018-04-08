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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ca.ualbert.cs.tasko.data.LocalDataManager;

/**
 * The LogOutActivity logs the user out by deleting their login information form the internal
 * storage and setting the CurrentUser's user object to null.
 */
public class LogOutActivity extends AppCompatActivity {

    private static final String FILENAME = "nfile.sav";

    /**
     * Standard OnCreate Method, note there is no layout associated with this activity.
     * @param savedInstanceState Get the saved state form the current device.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open starting the Activity, attempt to log the Current User out.
     */
    @Override
    protected void onStart() {
        super.onStart();
        try {
            logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles all necessary steps to Log a user out.
     * @throws IOException Catch errors if the file we try to Overwrite is not found.
     */
    private void logout() throws IOException {

        ((JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE)).cancelAll();
        CurrentUser.getInstance().setCurrentUser(null);
        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
        Gson gson = new Gson();
        gson.toJson((User) null, out);
        out.flush();

        //Clear local Task saves
        TaskList empty = new TaskList();
        LocalDataManager.saveLocalTasks(empty, getApplicationContext());

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();

    }

}
