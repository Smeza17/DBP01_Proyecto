package com.utec.fmagot.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditarPaciente extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_paciente);

        Bundle extras = getIntent().getExtras();
        String[] info = (String[]) extras.get("info");

        String[] nombre_completo = info[1].split(" ");

        TextView dni_view = findViewById(R.id.dni);
        EditText nombre_view = findViewById(R.id.nombre);
        EditText apellido_view = findViewById(R.id.apellido);
        EditText edad_view = findViewById(R.id.edad);
        EditText habitacion_view = findViewById(R.id.habitacion);
        dni_view.setText(info[0]);
        nombre_view.setText(nombre_completo[0]);
        apellido_view.setText(nombre_completo[1]);
        edad_view.setText(info[2]);
        int index = info[3].equals("Los Portales")?0:1;
        habitacion_view.setText(info[4]);
        String[] items = new String[]{"Los Portales", "San Juan"};
        Spinner spinner = findViewById(R.id.residencias);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setSelection(index);
    }
    public void editPaciente(View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        TextView dni_view = findViewById(R.id.dni);
        EditText nombre_view = findViewById(R.id.nombre);
        EditText apellido_view = findViewById(R.id.apellido);
        EditText edad_view = findViewById(R.id.edad);
        Spinner residencia_spinner = findViewById(R.id.residencias);
        EditText habitacion_view = findViewById(R.id.habitacion);
        String nombre = nombre_view.getText().toString();
        String apellido = apellido_view.getText().toString();
        String edad = edad_view.getText().toString();
        String residencia_id = residencia_spinner.getSelectedItem().toString().equals("Los Portales")?"2":"1";
        String url = "http://192.168.0.150:5001/pacientes/" + dni_view.getText().toString() + "/editar_app";
        String habitacion = habitacion_view.getText().toString();
        String json = "{\"nombre\":" + "\"" + nombre + "\"" +
                ", \"apellido\":" + "\"" + apellido + "\"" +
                ", \"edad\":" + "\"" + edad + "\"" +
                ", \"habitacion\":" + "\"" + habitacion + "\"" +
                ", \"residencia_id\":" + "\"" + residencia_id + "\"" + "}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                TextView server_response = findViewById(R.id.server_response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        server_response.setText("Error en el servidor");
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Intent intent = new Intent(EditarPaciente.this, VerPaciente.class);
                TextView server_response = findViewById(R.id.server_response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new Gson();
                            FlaskResponse flaskResponse = gson.fromJson(response.body().string(), FlaskResponse.class);
                            if (flaskResponse.isError()) {
                                server_response.setText("Error in BE");
                            } else {
                                Intent intent = new Intent(EditarPaciente.this, ListaPacientes.class);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void verPaciente(String[] info) {
        Intent intent = new Intent(EditarPaciente.this, VerPaciente.class);
        intent.putExtra("info", info);
        startActivity(intent);
    }
}