package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import model.Visitor;
import musica.AudioPlay;

public class ActivityMiCuenta extends AppCompatActivity {

    private Button contrasenia = null;
    private ImageButton musica = null;
    private TextView login = null;
    private TextView email = null;
    private Boolean encenderAudio = false;
    private SQLiteDatabase bd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);

        contrasenia = findViewById(R.id.buttonModificarPassword);
        musica = findViewById(R.id.imageButtonMusica);
        login = findViewById(R.id.textViewNombreMicuenta);
        email = findViewById(R.id.textViewEmailMiCuenta);
        bd = openOrCreateDatabase("emex51db", Context.MODE_PRIVATE,null);

        Intent intent = getIntent();
        Visitor visitor = (Visitor) intent.getSerializableExtra("visitor");
        login.setText(visitor.getFullName());
        email.setText(visitor.getEmail());

        contrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMiCuenta.this,ActivityEditPassword.class);
                startActivity(intent);
            }
        });
        musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musica.getDrawable() == getDrawable(R.drawable.encendido)){
                    musica.setImageDrawable(getDrawable(R.drawable.apagado));
                    AudioPlay.stopAudio();
                    bd.execSQL("UPDATE t_visitor SET musica = 0 WHERE login = 'login'");

                }else{
                    encenderAudio = true;
                    musica.setImageDrawable(getDrawable(R.drawable.encendido));
                }

            }
        });
        if(encenderAudio)
            AudioPlay.playAudio(this);

    }
}