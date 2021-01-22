package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import musica.AudioPlay;

public class ActivityPortada extends AppCompatActivity {

    private Button botonIniciaSesion = null;
    private Button botonRegistro = null;
    private LottieAnimationView alien;
    private boolean moviendo;
    private MediaPlayer mediaPlayer;
    private boolean reproduciendo = true;
    private ImageView imagenMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        botonIniciaSesion = (Button) findViewById(R.id.buttonIniciaSesionEntry);
        botonRegistro = (Button) findViewById(R.id.buttonRegistroActivityInicio);
        alien=findViewById(R.id.imageViewLottieAlien);
        mediaPlayer = new MediaPlayer();
        imagenMusica = findViewById(R.id.imagenMusica);

        AudioPlay.playAudio(this);

        botonIniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPortada.this, ActivityInicioSesionRegistro.class);
                intent.putExtra("Entry","FromActivityInicio");
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPortada.this, ActivityInicioSesionRegistro.class);
                intent.putExtra("Entry","FromActivityRegistro");
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        alien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!moviendo){
                    alien.playAnimation();
                    moviendo = true;
                } else{
                    alien.pauseAnimation();
                    moviendo = false;
                }
            }
        });
    }
}