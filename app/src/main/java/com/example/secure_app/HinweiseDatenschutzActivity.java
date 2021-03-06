package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Diese Activity implementiert die Hinweise zum Datenschutz.
 *
 * @author Marcel Hopp
 */
public class HinweiseDatenschutzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinweise__datenschutz);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hinweise zum Datenschutz");
    }
}