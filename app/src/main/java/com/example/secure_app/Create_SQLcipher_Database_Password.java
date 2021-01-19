package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Pattern;

public class Create_SQLcipher_Database_Password extends AppCompatActivity {

    EditText passwort, passwort_confirm;
    Button speicher_button;
    Context context;

    SharedPreferences sharedPreferences = null;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // mindestens eine Ziffer
                    "(?=.*[a-z])" +         // mindestens einen Kleinbuchstaben
                    "(?=.*[A-Z])" +         // mindestens einen Großbuchstaben
                    "(?=.*[@#$%^&+=.])" +    // mindestens ein Sonderzeichen
                    "(?=\\S+$)" +           // keine Leerzeichen erlaubt
                    ".{8,}" +               // mindestens 8 Ziffern
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lcipher_database);

        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Passwort erstellen");

        passwort = (EditText) findViewById(R.id.passwort_sqlcipher);
        passwort_confirm = (EditText) findViewById(R.id.passwort_sqlcipher_confirmation);
        speicher_button = (Button) findViewById(R.id.speicherbutton_sqlcipher);

        passwort.addTextChangedListener(password_textwatcher);
        passwort_confirm.addTextChangedListener(password_textwatcher);

        speicher_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwort.getText().toString();
                String confirmed_password = passwort_confirm.getText().toString();

                if (password.equals(confirmed_password) && (validate_password()))
                {
                    initialize_encrypted_shared_preferences();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password", password);
                    editor.apply();

                    open_database();
                }
                else
                {
                    Toast.makeText(Create_SQLcipher_Database_Password.this, "Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Textwatcher Objekt, welches den PasswortErstellen-Button aktiviert, sobald die Felder Passwort und
     * PasswortConfirm mit einem Char gefüllt sind.
     */
    private final TextWatcher password_textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            validate_password();

            String password = passwort.getText().toString().trim();
            String password_confirm = passwort_confirm.getText().toString().trim();

            speicher_button.setEnabled(!password.isEmpty() && !password_confirm.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private boolean validate_password() {
        String passwordInput = passwort.getText().toString().trim();
        if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            passwort.setError("Passwort zu schwach!");
            return false;
        } else {
            passwort.setError(null);
            return true;
        }
    }

    /**
     * Funktion zum Initalisieren der verschlüsselten SQLite-Datenbank.
     */
    private void open_database() {
        Intent intent = new Intent(this, Open_SQLcipher_database.class);
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