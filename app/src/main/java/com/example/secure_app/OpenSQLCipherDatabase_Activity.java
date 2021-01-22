package com.example.secure_app;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.regex.Pattern;

/**
 * Diese Activity implementiert die Grafische Oberfläche der SQL-Cipher-Datenbank.
 *
 * @author Marcel Hopp
 */
public class OpenSQLCipherDatabase_Activity extends AppCompatActivity {

    Button btnAdd, btnUpdate, btnDelete;
    EditText edtEmail;

    ListView lstEmails;

    String saveEmail ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open__s_q_lcipher_database);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SQLCipher-Datenbank");

        SQLiteDatabase.loadLibs(this);

        lstEmails = (ListView)findViewById(R.id.list_email);
        lstEmails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=(String) lstEmails.getItemAtPosition(i);
                edtEmail.setText(item);
                saveEmail = item;
            }
        });

        edtEmail = (EditText)findViewById(R.id.email_edit);
        btnAdd = (Button)findViewById(R.id.button_add_email);
        btnUpdate = (Button)findViewById(R.id.button_update_email);
        btnDelete = (Button)findViewById(R.id.button_delete_email);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                if(isValidEmail(email) == false)
                {
                    Toast.makeText(OpenSQLCipherDatabase_Activity.this, "Keine gültige E-Mail-Adresse", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper.getInstance(OpenSQLCipherDatabase_Activity.this).insertNewEmail(edtEmail.getText().toString());
                }
                reloadEmails();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                if(isValidEmail(email) == false)
                {
                    Toast.makeText(OpenSQLCipherDatabase_Activity.this, "Keine gültige E-Mail-Adresse", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper.getInstance(OpenSQLCipherDatabase_Activity.this)
                            .updateEmail(saveEmail, edtEmail.getText().toString());
                }
                reloadEmails();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(OpenSQLCipherDatabase_Activity.this)
                        .deleteEmail(edtEmail.getText().toString());
                reloadEmails();
            }
        });
        reloadEmails();
    }

    /**
     * Methode zum Neuladen der Email-Adressen
     */
    private void reloadEmails() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                DBHelper.getInstance(this).getAllEmail());

        lstEmails.setAdapter(adapter);
    }

    /**
     * Methode zum Überprüfen der Gültigkeit einer übergebenen E-Mail-Adresse
     * @param email vom Nutzer übergebene E-Mail-Adresse
     * @return true, falls E-Mail-Adresse gültig, false falls E-Mail-Adresse ungültig.
     */
    public boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}