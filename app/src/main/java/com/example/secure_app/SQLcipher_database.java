package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class SQLcipher_database extends AppCompatActivity {

    EditText passwort, passwort_confirm;
    Button speicher_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lcipher_database);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SQLite-Verschlüsselung");

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

                if (password.equals(confirmed_password))
                {
                    InitializeSQLCipher(password);
                }
                else
                {
                    Toast.makeText(SQLcipher_database.this, "Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show();
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
            String password = passwort.getText().toString().trim();
            String password_confirm = passwort_confirm.getText().toString().trim();

            speicher_button.setEnabled(!password.isEmpty() && !password_confirm.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    /**
     * Funktion zum Initalisieren der verschlüsselten SQLite-Datenbank.
     * @param password Das zuvor übergebene Passwort in der Activity.
     */
    private void InitializeSQLCipher(String password) {
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath("encrypted_database.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, password, null);
        database.execSQL("create table example(email)");
        database.execSQL("insert into example(email) values(?)", new Object[]{"hanspaulus@testmail.de"});
        database.execSQL("insert into example(email) values(?)", new Object[]{"mustermann@testmail.de"});
        Toast.makeText(this, "Verschlüsselte Datenbank erfolgreich angelegt!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Open_SQLcipher_database.class);
        startActivity(intent);
    }
}