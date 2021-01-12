package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.io.File;

public class SQLi_Injection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_li__injection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        initializeSQLcipher();
    }

    private void initializeSQLcipher() {
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath("encrypted_database_sqli.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "password", null);
        database.execSQL("create table example(email_adressen)");

        String sql = "insert into example (email_adressen) values (?)";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, "strenggeheime_emailadresse@test.de");
        statement.executeInsert();
        statement.bindString(1, "noch_gemheimere_emailadresse@test.de");
        statement.executeInsert();
    }

}