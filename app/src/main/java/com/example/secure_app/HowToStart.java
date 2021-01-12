package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HowToStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_start);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("How to Start?");

    }
}