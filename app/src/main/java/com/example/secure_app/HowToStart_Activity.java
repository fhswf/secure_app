package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Diese Activity implementiert Hinweise zu den ersten Schritten in dieser Applikation.
 *
 * @author Marcel Hopp
 */
public class HowToStart_Activity extends AppCompatActivity {

    TextView link_github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_start);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("How to Start?");

        link_github = (TextView) findViewById(R.id.text_how_to_start);
        link_github.setMovementMethod(LinkMovementMethod.getInstance());
    }
}