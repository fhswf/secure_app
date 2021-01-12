package com.example.sichereandroidapplikation;

/**
 * Diese Klasse impelementiert das Hauptmenü der Applikation.
 * Sie enthält mehrere Buttons, aus denen die einzelnen Schwachstellen heraus geöffnet werden.
 *
 * @author: Marcel Dominik Hopp
 * @version: 1.0
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    private Button button_insecure_data_storage, button_insecure_communication, button_insecure_authentication,
            button_client_side_injection, button_danksagungen, button_how_to_start, button_datenschutz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hauptmenü");

        button_insecure_data_storage = (Button) findViewById(R.id.insecure_data_storage);
        button_insecure_data_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_insecure_data_storage_activity();
            }
        });

        button_insecure_communication = (Button) findViewById(R.id.insecure_communication);
        button_insecure_communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_insecure_communication_activity();
            }
        });


        button_insecure_authentication = (Button) findViewById(R.id.insecure_authentication_jwt);
        button_insecure_authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_login_activity();
            }
        });


        button_client_side_injection = (Button) findViewById(R.id.client_side_injection_button_xml);
        button_client_side_injection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_client_side_injection_activity();
            }
        });

        button_danksagungen = (Button) findViewById(R.id.button_danksagungen_links_xml);
        button_danksagungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_link_activity();
            }
        });

        button_how_to_start = (Button) findViewById(R.id.button_how_to_start_xml);
        button_how_to_start .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_how_to_start_activity();
            }
        });

        button_datenschutz = (Button) findViewById(R.id.button_datenschutz_xml);
        button_datenschutz .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_datenschutz_activity();
            }
        });
    }

    /**
     * Diese Funktion impelementiert das Öffnen der Activity "Weiterführende Links".
     */
    public void open_link_activity()
    {
        Intent intent = new Intent(this, Weiterfuehrende_Links.class);
        startActivity(intent);
    }

    /**
     * Diese Funktion impelementiert das Öffnen der Activity "Weiterführende Links".
     */
    public void open_insecure_data_storage_activity()
    {
        Intent intent = new Intent(this, insecure_data_storage.class);
        startActivity(intent);
    }

    /**
     * Diese Funktion impelementiert das Öffnen der Activity "Weiterführende Links".
     */
    public void open_insecure_communication_activity()
    {
        Intent intent = new Intent(this, insecure_communication.class);
        startActivity(intent);
    }

    /**
     * Diese Funktion impelementiert das Öffnen der Activity "Weiterführende Links".
     */
   public void open_login_activity() {
       Intent intent = new Intent(this, LoginActivity.class);
       startActivity(intent);
   }

    /**
     * Diese Funktion impelementiert das Öffnen der Activity "Weiterführende Links".
     */
    public void open_client_side_injection_activity()
    {
        Intent intent = new Intent(this, client_side_injection.class);
        startActivity(intent);
    }

    /**
     * Diese Funktion impelementiert das Öffnen der Activity "Weiterführende Links".
     */
    public void open_how_to_start_activity()
    {
        Intent intent = new Intent(this, HowToStart.class);
        startActivity(intent);
    }

    /**
     * Diese Funktion impelementiert das Öffnen der Activity "Weiterführende Links".
     */
    public void open_datenschutz_activity()
    {
        Intent intent = new Intent(this, Hinweise_Datenschutz.class);
        startActivity(intent);
    }

}