package com.example.secure_app;

/**
 * Diese Klasse implementiert die Splash-Activity.
 *
 * @author Marcel Hopp
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Splash_Activity extends AppCompatActivity {

    String password;
    Context context;

    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Laden...");

        initialize_encrypted_shared_preferences();
        password = sharedPreferences.getString("password","");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(password.equals(""))
                {
                    open_create_sqlcipher_database_password_activity();
                } else {
                    open_enter_sqlcipher_database_password_activity();
                }
            }
        },2000);
    }

    private void open_create_sqlcipher_database_password_activity()
    {
        Intent intent = new Intent(getApplicationContext(), Create_SQLcipher_Database_Password.class);
        startActivity(intent);
        finish();
    }

    private void open_enter_sqlcipher_database_password_activity()
    {
        Intent intent = new Intent(getApplicationContext(), Enter_SQLcipher_Database_Password.class);
        startActivity(intent);
        finish();
    }

    private void initialize_encrypted_shared_preferences()
    {
        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}