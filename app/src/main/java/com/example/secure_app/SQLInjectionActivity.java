/**
 * Diese Activity implementiert Maßnahmen gegen SQLite-Injections.
 *
 * @author Marcel Hopp
 */

package com.example.secure_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class SQLInjectionActivity extends AppCompatActivity {

    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SQLite-Injections: Test");

        try {
            String SQL = "INSERT INTO sqliuser(user, password, phone_number) VALUES (?, ?, ?)";

            mDB = openOrCreateDatabase("sqli", MODE_PRIVATE, null);
            mDB.execSQL("DROP TABLE IF EXISTS sqliuser;");
            mDB.execSQL("CREATE TABLE IF NOT EXISTS sqliuser(user VARCHAR, password VARCHAR, phone_number VARCHAR);");

            SQLiteStatement statement = mDB.compileStatement(SQL);
            statement.bindString(1, "admin");
            statement.bindString(2, "passwd123");
            statement.bindString(3, "0123456789");
            statement.executeInsert();

            statement.bindString(1, "mustermann");
            statement.bindString(2, "password");
            statement.bindString(3, "0123456789");
            statement.executeInsert();

        }
        catch(Exception e) {
            Log.d("SecureApp", "Bei der Erstellung der Datenbank ist ein Fehler aufgetreten: " + e.getMessage());
        }
        setContentView(R.layout.activity_s_q_l_injection);
    }

    public void search(View view) {
        EditText srchtxt = (EditText) findViewById(R.id.sqlite_injection_editText);

        try {
            String SQL = "select * from sqliuser where user = ?";

            Cursor cursor = mDB.rawQuery(SQL, new String[]{srchtxt.getText().toString()});
            StringBuilder strb = new StringBuilder("");
            if ((cursor != null) && (cursor.getCount() > 0)) {
                cursor.moveToFirst();

                do {
                    strb.append("User: (" + cursor.getString(0) + ") Passwort: (" + cursor.getString(1) + ") Telefonnummer: (" + cursor.getString(2) + ")\n");
                } while (cursor.moveToNext());
            }
            else {
                strb.append("User: (" + srchtxt.getText().toString() +") nicht gefunden!");
            }
            Toast.makeText(this, strb.toString(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e) {
            Log.d("SecureApp", "Bei der Suche in der Datenbank ist ein Fehler aufgetreten " + e.getMessage());
        }
    }
}