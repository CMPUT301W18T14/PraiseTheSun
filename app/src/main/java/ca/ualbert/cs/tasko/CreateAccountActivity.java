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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

/**
 * Activity that creates an account. It is called from the LoginActivity screen when the user
 * selects the "Create Account" button.
 *
 * @author tlafranc
 * @see LoginActivity
 */
public class CreateAccountActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText nameText;
    private EditText emailText;
    private EditText phoneText;
    private String username;
    private String name;
    private String email;
    private String phone;

    /**
     * Called when activity is started. Initializes the usernameText, nameText, emailText and
     * phoneText instance variables.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        usernameText = (EditText) findViewById(R.id.createAccountUsername);
        nameText = (EditText) findViewById(R.id.createAccountName);
        emailText = (EditText) findViewById(R.id.createAccountEmail);
        phoneText = (EditText) findViewById(R.id.createAccountPhone);
    }

    /**
     * Called when the user clicks on the "Create" button in this activity. Checks to see
     * if all fields satisfy the requirements. If they don't, does nothing. Otherwise, it checks to
     * see if the selected username has already been chosen. If it has, notifies user to enter a
     * new username. Otherwise, it creates a user using the instance variables username, name,
     * email and phone and tries to store this user in our database.
     *
     */
    public void onCreateClick(View view) {
        boolean valid = checkFieldsForEmptyValues();
        if (valid) {
            try {
                User retrievedUser = DataManager.getInstance().getUserByUsername(username, this
                        .getApplicationContext());
                if (retrievedUser.getUsername() == null) {
                    User newUser = new User(username, name, email, phone);
                    Log.i("NotError", "ERROR IS HERE");
                    try {
                        DataManager.getInstance().putUser(newUser, this.getApplicationContext());
                        finish();
                    } catch (NoInternetException e) {
                        Log.i("Error", "No internet connection in CreateAccountActivity");
                        Toast.makeText(this.getApplicationContext(), "No Internet Connection!",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this.getApplicationContext(), "Sorry, username already " +
                            "taken", Toast.LENGTH_LONG).show();
                }
            } catch (NoInternetException e) {
                Log.i("Error", "No internet connection in CreateAccountActivity");
                Toast.makeText(this.getApplicationContext(), "No Internet Connection!", Toast
                        .LENGTH_LONG).show();
            }
        }
    }

    /**
     * Function that determines if there are any blank fields for username, name, email and phone
     * as all of these fields must be provided. Also, checks to see that the email given has a
     * valid email format and checks that the phone number is of format ***-***-****.
     *
     * @return returns a boolean. Returns false if there is at least one field that does not
     * satisfy the requirements and true otherwise.
     */
    private boolean checkFieldsForEmptyValues() {
        username = usernameText.getText().toString();
        name = nameText.getText().toString();
        email = emailText.getText().toString();
        phone = phoneText.getText().toString();
        boolean validInputs = true;

        if (username.trim().equals("")) {
            usernameText.setError("Username cannot be left blank");
            validInputs = false;
        }
        if (name.trim().equals("")) {
            nameText.setError("Name cannot be left blank");
            validInputs = false;
        }
        /*
         * Code on determining whether a valid email was entered
         * https://stackoverflow.com/questions/12947620/email-address-validation-in-android-on
         * -edittext
         * Taken on 2018-03-16
         *
         */

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Invalid email address");
            validInputs = false;
        }
        if (email.trim().equals("")) {
            emailText.setError("Email cannot be left blank");
            validInputs = false;
        }
        if (phone.trim().equals("")) {
            phoneText.setError("Phone number cannot be left blank");
            validInputs = false;
        }
        if (!Pattern.compile(getString(R.string.phone_number_pattern)).matcher(phone).matches()) {
            phoneText.setError("Phone number must be in form ***-***-***");
            validInputs = false;
        }
        return validInputs;
    }
}
