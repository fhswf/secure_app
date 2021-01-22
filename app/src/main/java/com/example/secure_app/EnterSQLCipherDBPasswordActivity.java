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

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Diese Activity implementiert die Übergabe des Datenbank-Passworts.
 *
 * @author: Marcel Hopp
 */
public class EnterSQLCipherDBPasswordActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    Context context;
    String password;
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__s_q_lcipher__database__password);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Passwort eingeben");

        initialize_encrypted_shared_preferences();
        password = sharedPreferences.getString("password","");

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        editText.addTextChangedListener(password_textwatcher);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();

                if(text.equals(password))
                {
                    open_database();
                }
                else {
                    Toast.makeText(EnterSQLCipherDBPasswordActivity.this,"Falsches Passwort übergeben!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Textwatcher Objekt, welches den Button aktiviert, sobald das Passwort den Anforderungen entspricht.
     */
    private final TextWatcher password_textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String password = editText.getText().toString().trim();

            button.setEnabled(!password.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    /**
     * Funktion zum Öffnen der Datenbank.
     */
    private void open_database()
    {
        Intent intent = new Intent(this, OpenSQLCipherDatabase_Activity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Funktion zum Initialisieren der EncryptedSharedPreferences.
     */
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