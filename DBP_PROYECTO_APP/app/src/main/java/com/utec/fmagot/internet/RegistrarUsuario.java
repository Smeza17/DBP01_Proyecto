package com.utec.fmagot.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
    }

    public void registrarUsuario(View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        EditText dni_view = findViewById(R.id.dni);
        EditText usuario_view = findViewById(R.id.user);
        EditText pass_view = findViewById(R.id.pass);
        String usuario = usuario_view.getText().toString();
        String pass = pass_view.getText().toString();
        String dni = dni_view.getText().toString();
        String url = "http://192.168.0.150:5001/usuarios/" + usuario + "/registrar_app";
        String json = "{\"dni\":" + "\"" + dni + "\"," +
                "\"user\":" + "\"" + usuario + "\"," +
                "\"password\":" + "\"" + pass + "\"," +
                "\"es_admin\":" + "\"" + "0" + "\"}";

        Log.v("json: ", json);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                TextView server_response = findViewById(R.id.server_response);
                Gson gson = new Gson();
                FlaskResponse flaskResponse = gson.fromJson(response.body().string(), FlaskResponse.class);
                if (flaskResponse.isError()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            server_response.setText(flaskResponse.getMessage());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistrarUsuario.this, flaskResponse.getMessage(), Toast.LENGTH_LONG);
                            Intent intent = new Intent(RegistrarUsuario.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}