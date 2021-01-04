package com.example.sichereandroidapplikation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.Auth0;
import com.auth0.android.Auth0Exception;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.VoidCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

public class LoginActivity extends AppCompatActivity {

    private Auth0 auth0;

    public static final String EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN";

    private static final String API_URL = "http://127.0.0.1:3000/api/private";
    private static final String API_IDENTIFIER = "http://127.0.0.1:3000/api/private";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);

        RootDetection rootdetection = new RootDetection();
        rootdetection.detectTestKeys();
        rootdetection.checkForSuBinary();

        //Check if the activity was launched to log the user out
        if (getIntent().getBooleanExtra(EXTRA_CLEAR_CREDENTIALS, false)) {
            logout();
        }
    }

    private void login() {
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience(String.format(API_IDENTIFIER))
                .start(this, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthenticationException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Erfolgreich eingeloggt!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, Protected_API_Call.class);
                                intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getAccessToken());
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
    }

    private void logout() {
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, new VoidCallback() {
                    @Override
                    public void onSuccess(Void payload) {
                        Toast.makeText(LoginActivity.this, "Logout erfolgreich", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Auth0Exception error) {
                        //Log out canceled, keep the user logged in
                        Toast.makeText(LoginActivity.this, "Logout fehlgeschlagen, nicht eingeloggt!", Toast.LENGTH_SHORT).show();
                        showNextActivity();
                    }
                });
    }

    private void showNextActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}