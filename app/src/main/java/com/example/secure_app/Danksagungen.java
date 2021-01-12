package com.example.sichereandroidapplikation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Danksagungen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danksagungen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
}