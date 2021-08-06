package com.utec.fmagot.internet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static String EDIT_TEXT = "com.utec.fmagot.internet.EDIT_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        EditText user = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);
        String username = user.getText().toString();
        String password = pass.getText().toString();
        String json = "{\"user\":" + "\"" + username + "\","
                + "\"password\":" + "\"" + password + "\"}";
        Log.v("json", json);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        String url = "http://192.168.0.150:5001/login_app";
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                TextView server_response = findViewById(R.id.response_message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        server_response.setText("Error in BE");
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                FlaskResponse flaskResponse = gson.fromJson(response.body().string(), FlaskResponse.class);
                Log.v("error?", String.valueOf(flaskResponse.isError()));
                TextView server_response = findViewById(R.id.response_message);
                if (flaskResponse.isError()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            server_response.setText(flaskResponse.getMessage());
                        }
                    });
                }
                else {
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void registrarUsuario(View view) {
        Intent intent = new Intent(MainActivity.this, RegistrarUsuario.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}