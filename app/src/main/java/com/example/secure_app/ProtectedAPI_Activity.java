package com.example.secure_app;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Diese Activity implementiert den Zugriff auf die private API des Backends.
 *
 * @author Marcel Hopp
 */
public class ProtectedAPI_Activity extends AppCompatActivity {

    private static final String API_URL = "https://jupiter.fh-swf.de/secureapp/api/private";

    private String accessToken;
    private TextView textView_api_text;
    ProgressBar progressBar;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected_a_p_i);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("JWT-Authentifizierung: Eingeloggt");

        Button callAPIWithTokenButton = findViewById(R.id.callAPIWithTokenButton);
        Button loginWithTokenButton = findViewById(R.id.loginButton);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_protected_api);

        callAPIWithTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                callAPI(true);

            }
        });
        loginWithTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        accessToken = getIntent().getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN);
    }

    /**
     * Methode zum Aufruf der Privaten API.
     * @param sendToken true, da das AccessToken verwendet werden soll.
     */
    private void callAPI(boolean sendToken) {
        final Request.Builder reqBuilder = new Request.Builder()
                .get()
                .url(API_URL);
        if (sendToken) {
            if (accessToken == null) {
                Toast.makeText(ProtectedAPI_Activity.this, "Token nicht gefunden, bitte zuerst einloggen!", Toast.LENGTH_SHORT).show();
                return;
            }
            reqBuilder.addHeader("Authorization", "Bearer " + accessToken);
        }

        OkHttpClient client = new OkHttpClient();
        Request request = reqBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProtectedAPI_Activity.this, "API-Call erfolgreich!", Toast.LENGTH_SHORT).show();
                            print_private_api_message();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ProtectedAPI_Activity.this, "API-Call fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProtectedAPI_Activity.this, "An error occurred" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Gibt die Private Nachricht des Backends aus.
     */
    private void print_private_api_message() {
        RequestQueue queue = Volley.newRequestQueue(ProtectedAPI_Activity.this);

        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, API_URL, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                textView_api_text = findViewById(R.id.textView_api_protected_xml);

                try {
                    textView_api_text.setText(response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", "Bearer " + accessToken);
                return headerMap;
            }
        };
        queue.add(request);
    }

    /**
     * Implementiert den Logout.
     */
    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_CLEAR_CREDENTIALS, true);
        startActivity(intent);
        finish();
    }

    /**
     * Prüft zur Laufzeit, ob eine Internetverbindung besteht und gibt ggf. eine Meldung aus.
     */
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}