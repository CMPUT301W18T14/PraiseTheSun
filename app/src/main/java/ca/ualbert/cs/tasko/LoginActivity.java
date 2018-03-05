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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private LoginActivity activity = this;
    private EditText usernameText;
    private Button loginButton;
    private Button createAccountButton;

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

    public void setupLoginButton (){
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                // Need to use getUsernamebyUsername function chase is working on.
                if (usernameText.getText() != null) { //usernameText.getText is in DataBase
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                } else {
                    usernameText.setError("This is not a valid username");
                }
            }
        });

    }

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
