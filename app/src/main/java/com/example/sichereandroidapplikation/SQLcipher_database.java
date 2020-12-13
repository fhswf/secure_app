package com.example.sichereandroidapplikation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class SQLcipher_database extends AppCompatActivity {

    EditText editText1, editText2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lcipher_database);

        editText1 = (EditText) findViewById(R.id.passwort_sqlcipher);
        editText2 = (EditText) findViewById(R.id.passwort_sqlcipher_confirmation);
        button = (Button) findViewById(R.id.speicherbutton_sqlcipher);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editText1.getText().toString();
                String confirmed_password = editText2.getText().toString();

                if(password.equals("") || confirmed_password.equals(""))
                {
                    Toast.makeText(SQLcipher_database.this, "Kein Passwort übergeben!", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(confirmed_password))
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

    private void InitializeSQLCipher(String password) {
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath("encrypted_database.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, password, null);
        database.execSQL("create table example(a, b)");
        database.execSQL("insert into example(a, b) values(?, ?)", new Object[]{"ich bin eine testeingabe",
                "ich auch"});
        Toast.makeText(this, "Verschlüsselte Datenbank erfolgreich angelegt!", Toast.LENGTH_SHORT).show();
    }
}