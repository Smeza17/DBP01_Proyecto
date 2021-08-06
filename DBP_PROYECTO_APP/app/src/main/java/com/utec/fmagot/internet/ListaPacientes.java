package com.utec.fmagot.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListaPacientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.0.150:5001/pacientes/app").build();
        TextView tv = findViewById(R.id.message);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("Error");
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new Gson();
                            Paciente[] pacientes = gson.fromJson(response.body().string(), Paciente[].class);

                            LinearLayout paciente_layout = findViewById(R.id.pacientes_layout);
                            for (int i = 0; i < pacientes.length; i++) {
                                LinearLayout paciente = new LinearLayout(getApplicationContext());
                                paciente.setOrientation(LinearLayout.HORIZONTAL);
                                paciente.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                paciente.setWeightSum(100);

                                final float scale = getResources().getDisplayMetrics().density;
                                int forty_dp = (int) (40*scale);

                                TextView dni_view = new TextView(getApplicationContext());
                                dni_view.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        forty_dp,
                                        30
                                ));
                                TextView nombre_completo_view = new TextView(getApplicationContext());
                                nombre_completo_view.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        forty_dp,
                                        50
                                ));
                                Button ver_paciente = new Button(getApplicationContext());
                                ver_paciente.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        forty_dp,
                                        20
                                ));
                                ver_paciente.setText("+ Info");
                                View separador = new View(getApplicationContext());
                                separador.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        forty_dp/40
                                ));
                                separador.setBackgroundColor(getResources().getColor(R.color.darker_gray));
                                int finalI = i;
                                ver_paciente.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String[] info = new String[7];
                                        info[0] = pacientes[finalI].getDni();
                                        info[1] = pacientes[finalI].getNombre();
                                        info[2] = pacientes[finalI].getApellido();
                                        info[3] = pacientes[finalI].getEdad();
                                        info[4] = pacientes[finalI].getResidencia();
                                        info[5] = pacientes[finalI].getHabitacion();
                                        info[6] = pacientes[finalI].getUser();
                                        verPaciente(info);
                                    }
                                });
                                //TextView apellido = new TextView(getApplicationContext());
                                //TextView edad = new TextView(getApplicationContext());
                                //TextView habitacion = new TextView(getApplicationContext());
                                //TextView residencia = new TextView(getApplicationContext());
                                //TextView usuario = new TextView(getApplicationContext());
                                String nombre_completo = pacientes[i].getNombre() + " " + pacientes[i].getApellido();
                                String dni = pacientes[i].getDni();
                                dni_view.setText(dni);
                                nombre_completo_view.setText(nombre_completo);
                                //apellido.setText(pacientes[i].getApellido());
                                //edad.setText(pacientes[i].getEdad());
                                //habitacion.setText(pacientes[i].getHabitacion());
                                //residencia.setText(pacientes[i].getResidencia());
                                //usuario.setText(pacientes[i].getUser());
                                paciente.addView(dni_view);
                                paciente.addView(nombre_completo_view);
                                paciente.addView(ver_paciente);
                                paciente_layout.addView(paciente);
                                paciente_layout.addView(separador);
                                //layout.addView(apellido);
                                //layout.addView(edad);
                                //layout.addView(habitacion);
                                //layout.addView(residencia);
                                //layout.addView(usuario);
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
        Intent intent = new Intent(ListaPacientes.this, VerPaciente.class);
        intent.putExtra("info", info);
        startActivity(intent);
    }
}