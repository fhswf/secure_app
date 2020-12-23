/**
 *
 * Diese Klasse implementiert die MainActivity der Applikation.
 * Neben einem Willkommenstext wird auch ein Button angezeigt, welcher mit einem Klick das Hauptmenü öffnet.
 *
 * @author Marcel Dominik Hopp
 * @version 1.0
 *
 */

package com.example.sichereandroidapplikation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.auth0.android.Auth0;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Auth0 auth0;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });
    }

    /**
     * Funktion, die einen Intent startet und das Hauptmenü öffnet.
     * Wird mit einem Klick des Start-Buttons aufgerufen.
     */
    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

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