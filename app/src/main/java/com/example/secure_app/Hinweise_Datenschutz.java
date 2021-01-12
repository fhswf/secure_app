package com.example.sichereandroidapplikation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Hinweise_Datenschutz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinweise__datenschutz);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hinweise zum Datenschutz");
    }
}