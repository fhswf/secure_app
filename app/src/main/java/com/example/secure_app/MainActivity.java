package com.example.secure_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Diese Klasse implementiert die MainActivity der Applikation.
 * Neben einem Willkommenstext wird auch ein Button angezeigt, welcher mit einem Klick das Hauptmenü öffnet.
 *
 * @author Marcel Hopp
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    private Button button_openMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        button_openMainMenu = (Button) findViewById(R.id.button);

        button_openMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });

    }

    /**
     * Funktion, die einen Intent startet und das Hauptmenü öffnet.
     * Wird mit einem Klick des Start-Buttons aufgerufen.
     */
    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu_Activity.class);
        startActivity(intent);
    }
}