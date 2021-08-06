package com.utec.fmagot.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class RegistrarPaciente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_paciente);

        Spinner spinner = findViewById(R.id.residencias);
        String[] items = new String[]{"Los Portales", "San Juan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    public void postPaciente(View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        EditText dni_view = findViewById(R.id.dni);
        EditText nombre_view = findViewById(R.id.nombre);
        EditText apellido_view = findViewById(R.id.apellido);
        EditText edad_view = findViewById(R.id.edad);
        EditText habitacion_view = findViewById(R.id.habitacion);
        Spinner spinner_view = findViewById(R.id.residencias);
        String dni = dni_view.getText().toString();
        String nombre = nombre_view.getText().toString();
        String apellido = apellido_view.getText().toString();
        String edad = edad_view.getText().toString();
        String habitacion = habitacion_view.getText().toString();
        String residencia_id;
        if (spinner_view.getSelectedItem().toString().equals("Los Portales")) {
            residencia_id = "2";
        } else {
            residencia_id = "1";
        }
        dni_view.getText().clear();
        nombre_view.getText().clear();
        apellido_view.getText().clear();
        edad_view.getText().clear();
        habitacion_view.getText().clear();
        String json = "{\"nombre\":" + "\"" + nombre + "\"" +
                ", \"apellido\":" + "\"" + apellido + "\"" +
                ", \"edad\":" + "\"" + edad + "\"" +
                ", \"habitacion\":" + "\"" + habitacion + "\"" +
                ", \"residencia_id\":" + "\"" + residencia_id + "\"" + "}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        String url = "http://192.168.0.150:5001/pacientes/" + dni + "/registrar_app";

        Request request = new Request.Builder().url(url).post(body).build();

        TextView res = findViewById(R.id.response);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        res.setText("Failure");
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        try {
                            FlaskResponse flaskResponse = gson.fromJson(response.body().string(), FlaskResponse.class);
                            Log.v(this.getClass().getName(), flaskResponse.getMessage());
                            Log.v(this.getClass().getName(), String.valueOf(flaskResponse.isError()));
                            if (flaskResponse.isError()) {
                                res.setText("Error in BE");
                            } else {
                                if (flaskResponse.getCategory().equals("error")) {
                                    res.setText(flaskResponse.getMessage());
                                } else {
                                    res.setText("Se agrego el paciente");
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}