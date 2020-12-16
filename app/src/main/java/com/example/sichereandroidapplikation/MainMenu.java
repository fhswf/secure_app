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
            button_insecure_authorization, button_client_side_injection, button_danksagungen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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

        button_insecure_authentication = (Button) findViewById(R.id.insecure_authentication);
        button_insecure_authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });

        button_insecure_authorization = (Button) findViewById(R.id.insecure_authorization);
        button_insecure_authorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity6();
            }
        });

        button_client_side_injection = (Button) findViewById(R.id.client_side_injection_button_xml);
        button_client_side_injection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity8();
            }
        });

        button_danksagungen = (Button) findViewById(R.id.button_danksagungen_links_xml);
        button_danksagungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkActivity();
            }
        });

    }


    public void openLinkActivity()
    {
        Intent intent = new Intent(this, Weiterfuehrende_Links.class);
        startActivity(intent);
    }

    public void open_insecure_data_storage_activity()
    {
        Intent intent = new Intent(this, insecure_data_storage.class);
        startActivity(intent);
    }

    public void open_insecure_communication_activity()
    {
        Intent intent = new Intent(this, insecure_communication.class);
        startActivity(intent);
    }

    public void openActivity5()
    {
        Intent intent = new Intent(this, API_Test.class);
        startActivity(intent);
    }

    public void openActivity6()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void openActivity8()
    {
        Intent intent = new Intent(this, client_side_injection.class);
        startActivity(intent);
    }



}