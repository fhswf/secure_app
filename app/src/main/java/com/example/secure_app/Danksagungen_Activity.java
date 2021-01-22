package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Diese Activity implementiert die Danksagungen.
 *
 * @author Marcel Hopp
 */
public class Danksagungen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danksagungen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
}