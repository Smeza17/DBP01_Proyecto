package com.utec.fmagot.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void registrarPaciente(View view) {
        Intent intent = new Intent(Menu.this, RegistrarPaciente.class);
        startActivity(intent);
    }

    public void verPacientes(View view) {
        Intent intent = new Intent(Menu.this, ListaPacientes.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(Menu.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}