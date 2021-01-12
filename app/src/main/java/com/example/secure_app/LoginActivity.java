package com.example.secure_app;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.Auth0;
import com.auth0.android.Auth0Exception;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.VoidCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

/**
 * Created by lbalmaceda on 5/10/17.
 */

public class LoginActivity extends AppCompatActivity {

    private Auth0 auth0;
    private SecureCredentialsManager credentialsManager;

    /*
     * Required when setting up Local Authentication in the Credential Manager
     * Refer to SecureCredentialsManager#requireAuthentication method for more information.
     */
    @SuppressWarnings("unused")
    private static final int CODE_DEVICE_AUTHENTICATION = 22;
    private static final String API_IDENTIFIER = "https://jupiter.fh-swf.de/secureapp/api/private";

    public static final String EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN";
    public static final String EXTRA_ID_TOKEN = "com.auth0.ID_TOKEN";

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup the UI
        setContentView(R.layout.login_activity);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("JWT-Authentifizierung: Login");

        //Setup CredentialsManager
        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        credentialsManager = new SecureCredentialsManager(this, new AuthenticationAPIClient(auth0), new SharedPreferencesStorage(this));

        //Optional - Uncomment the next line to use:
        //Require device authentication before obtaining the credentials
        //credentialsManager.requireAuthentication(this, CODE_DEVICE_AUTHENTICATION, getString(R.string.request_credentials_title), null);

        //Check if the activity was launched to log the user out
        if (getIntent().getBooleanExtra(EXTRA_CLEAR_CREDENTIALS, false)) {
            doLogout();
            return;
        }

        if (credentialsManager.hasValidCredentials()) {
            // Obtain the existing credentials and move to the next activity
            showNextActivity();
        }
    }

    /**
     * Override required when setting up Local Authentication in the Credential Manager
     * Refer to SecureCredentialsManager#requireAuthentication method for more information.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (credentialsManager.checkAuthenticationResult(requestCode, resultCode)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showNextActivity() {
        credentialsManager.getCredentials(new BaseCallback<Credentials, CredentialsManagerException>() {
            @Override
            public void onSuccess(final Credentials credentials) {
                Intent intent = new Intent(LoginActivity.this, ProtectedAPI.class);
                intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getAccessToken());
                intent.putExtra(EXTRA_ID_TOKEN, credentials.getIdToken());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(CredentialsManagerException error) {
                //Authentication cancelled by the user. Exit the app
                finish();
            }
        });
    }

    private void doLogin() {
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience(API_IDENTIFIER)
                .withScope("openid offline_access")
                .start(this, loginCallback);
    }

    private void doLogout() {
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, logoutCallback);
    }

    private final AuthCallback loginCallback = new AuthCallback() {
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
                    Toast.makeText(LoginActivity.this, "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onSuccess(@NonNull Credentials credentials) {
            credentialsManager.saveCredentials(credentials);
            showNextActivity();
        }
    };

    private VoidCallback logoutCallback = new VoidCallback() {
        @Override
        public void onSuccess(Void payload) {
            credentialsManager.clearCredentials();
        }

        @Override
        public void onFailure(Auth0Exception error) {
            //Log out canceled, keep the user logged in
            showNextActivity();
        }
    };

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}