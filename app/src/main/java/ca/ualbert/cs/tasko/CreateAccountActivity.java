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
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText nameText;
    private EditText emailText;
    private EditText phoneText;
    private String username;
    private String name;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        usernameText = (EditText) findViewById(R.id.createAccountUsername);
        nameText = (EditText) findViewById(R.id.createAccountName);
        emailText = (EditText) findViewById(R.id.createAccountEmail);
        phoneText = (EditText) findViewById(R.id.createAccountPhone);
    }

    public void onCreateClick(View view) {
        boolean valid = checkFieldsForEmptyValues();
        if (valid) {
            User newUser = new User(username, name, email, phone);
            // This relies on Chases DataManager class. Subject to change in future.
            // DataManager.getInstance().putUser(newUser);
        }
    }

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
        if (email.trim().equals("")) {
            emailText.setError("Email cannot be left blank");
            validInputs = false;
        }
        if (phone.trim().equals("")) {
            phoneText.setError("Phone number cannot be left blank");
            validInputs = false;
        }

        return validInputs;
    }
}
