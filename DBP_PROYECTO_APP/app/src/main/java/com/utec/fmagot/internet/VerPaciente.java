package com.utec.fmagot.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VerPaciente extends AppCompatActivity {
    private String[] info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_paciente);
        Bundle extras = getIntent().getExtras();
        String[] info_intent = (String[]) extras.get("info");
        this.info = info_intent;
        TextView dni = findViewById(R.id.dni);
        TextView nombre_completo = findViewById(R.id.nombre_completo);
        TextView edad = findViewById(R.id.edad);
        TextView residencia = findViewById(R.id.residencia);
        TextView habitacion = findViewById(R.id.habitacion);
        TextView usuario = findViewById(R.id.usuario);

        dni.setText(info[0]);
        String nombre_completo_text = info[1] + " " + info[2];
        nombre_completo.setText(nombre_completo_text);
        edad.setText(info[3]);
        residencia.setText(info[4]);
        habitacion.setText(info[5]);
        usuario.setText(info[6]);
    }

    public void deletePaciente(View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        TextView dni_view = findViewById(R.id.dni);
        String dni = dni_view.getText().toString();
        String url = "http://192.168.0.150:5001/pacientes/" + dni + "/delete_paciente_app";
        Request request = new Request.Builder().url(url).delete().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                Toast.makeText(VerPaciente.this, "Error", Toast.LENGTH_LONG);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(VerPaciente.this, Menu.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void editPaciente(View view) {
        Intent intent = new Intent(VerPaciente.this, EditarPaciente.class);
        TextView dni_view = findViewById(R.id.dni);
        TextView nombre_completo_view = findViewById(R.id.nombre_completo);
        TextView edad_view = findViewById(R.id.edad);
        TextView residencia_view = findViewById(R.id.residencia);
        TextView habitacion_view = findViewById(R.id.habitacion);
        TextView usuario = findViewById(R.id.usuario);

        String dni = dni_view.getText().toString();
        String nombre = nombre_completo_view.getText().toString();
        String edad = edad_view.getText().toString();
        String residencia = residencia_view.getText().toString();
        String habitacion = habitacion_view.getText().toString();
        String[] info = {dni, nombre, edad, residencia, habitacion};
        intent.putExtra("info", info);
        startActivity(intent);
    }
}