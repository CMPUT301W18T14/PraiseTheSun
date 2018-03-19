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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.IOException;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * LoginActivity should be the first Activity a user will see if they are not logged in.
 * Just contains a EditText that allows the user to Login, If the text in the EditText matches
 * a username in the database then access is granted. The user also has the option to navigate to
 * the create a new account activity if they do not already have an account.
 *
 * @author spack
 */
public class LoginActivity extends AppCompatActivity {

    private static final String FILENAME = "nfile.sav";
    private DataManager DM = DataManager.getInstance();
    private CurrentUser CU = CurrentUser.getInstance();

    private LoginActivity activity = this;
    private EditText usernameText;
    private Button loginButton;
    private Button createAccountButton;

    /**
     * Creates the activity, which includes setting the layout, finding and binding all the widgets
     * and setting up the buttons.
     * @param savedInstanceState Get the saved state form the current device
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.usernameEditText);
        loginButton = (Button) findViewById(R.id.LoginButton);
        createAccountButton = (Button) findViewById(R.id.CreateAccountButton);

        setupLoginButton();
        setupCreateAccountButton();
    }

    /**
     * The login button checks to make sure the username provided matches a username in the
     * Database, if it does it sets that user to the current user using the CurrentUser singelton
     * and also save the use to a local file so it does not have to login again.
     * Otherwise the user is prompted to provide a valid username or create a new user profile.
     */
    public void setupLoginButton (){
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                User usernameInput = null;
                try {
                    usernameInput = DM.getUserByUsername(usernameText.getText().toString(),
                            LoginActivity.this);
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }

                if (usernameInput.getUsername() != null){
                    CU.setCurrentUser(usernameInput);
                    try {
                        FileOutputStream fos = openFileOutput(FILENAME,
                                Context.MODE_APPEND);
                        fos.write(usernameInput.getUsername().getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                } else {
                    usernameText.setError("This is not a valid username");
                }
            }
        });
    }

    /**
     * The Create Account Activity simply sends the user to the create account activity.
     */
    public void setupCreateAccountButton (){
        createAccountButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(activity, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}
