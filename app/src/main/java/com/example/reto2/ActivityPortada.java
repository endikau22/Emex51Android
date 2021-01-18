package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import musica.AudioPlay;

public class ActivityPortada extends AppCompatActivity {

    private Button botonIniciaSesion = null;
    private Button botonRegistro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        botonIniciaSesion = (Button) findViewById(R.id.buttonIniciaSesionEntry);
        botonRegistro = (Button) findViewById(R.id.buttonRegistroActivityInicio);

        AudioPlay.playAudio(this);

        botonIniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPortada.this, ActivityInicioSesionRegistro.class);
                intent.putExtra("Entry","FromActivityInicio");
                startActivity(intent);
            }
        });
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPortada.this, ActivityInicioSesionRegistro.class);
                intent.putExtra("Entry","FromActivityRegistro");
                startActivity(intent);
            }
        });
    }
}