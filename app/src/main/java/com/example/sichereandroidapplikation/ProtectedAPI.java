package com.example.sichereandroidapplikation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProtectedAPI extends AppCompatActivity {

    private static final String API_URL = "http://10.0.2.2:3000/api/private";

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected_a_p_i);
        Button callAPIWithTokenButton = findViewById(R.id.callAPIWithTokenButton);
        Button loginWithTokenButton = findViewById(R.id.loginButton);
        callAPIWithTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void callAPI(boolean sendToken) {
        final Request.Builder reqBuilder = new Request.Builder()
                .get()
                .url(API_URL);
        if (sendToken) {
            if (accessToken == null) {
                Toast.makeText(ProtectedAPI.this, "Token not found. Log in first.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ProtectedAPI.this, "API call success!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProtectedAPI.this, "API call failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProtectedAPI.this, "An error occurred" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_CLEAR_CREDENTIALS, true);
        startActivity(intent);
        finish();
    }
}