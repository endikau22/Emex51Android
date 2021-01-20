package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class ActivityInicioSesionRegistro extends AppCompatActivity {

    private LinearLayout layoutBotonesCabecera = null;
    private ConstraintLayout layoutInicioSesion = null;
    private ConstraintLayout layoutRegistro = null;
    private Button botonInicioCabecera = null;
    private Button botonRegistroCabecera = null;
    private Button botonInicio = null;
    private Button botonRegistro = null;
    private TextView textoRecuperarPassword = null;
    private SQLiteDatabase bd = null;
    private EditText textoLoginInicioSesion = null;
    private EditText textoPasswordInicioSesion = null;
    private EditText textoNombreRegistro = null;
    private EditText textoDNIRegistro = null;
    private EditText textoEmailRegistro = null;
    private EditText textoLoginRegistro = null;
    private EditText textoPasswordRegistro = null;
    private Switch recuerdame = null;
    private CheckBox aceptarCondicionesRegistro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesionregistro);

        layoutBotonesCabecera = findViewById(R.id.layoutBotones);
        layoutInicioSesion = findViewById(R.id.layoutInicioSesion);
        layoutRegistro = findViewById(R.id.layoutRegistro);
        botonInicioCabecera = findViewById(R.id.buttonInicioSesionActivityIninio);
        botonRegistroCabecera = findViewById(R.id.buttonRegistroActivityInicio);
        botonInicio = findViewById(R.id.buttonInicio);
        botonRegistro = findViewById(R.id.buttonRegistro);
        textoRecuperarPassword = findViewById(R.id.textViewPasswordOlvidada);
        bd = openOrCreateDatabase("emex51db", Context.MODE_PRIVATE,null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS visitor (email VARCHAR,password VARCHAR,musica INTEGER);");

        Intent intent = this.getIntent();
        if (intent != null){
            String extra = intent.getExtras().getString("Entry");
            if(extra.equals("FromActivityInicio")){
                layoutRegistro.setVisibility(View.GONE);
                layoutInicioSesion.setVisibility(View.VISIBLE);
            }else{
                layoutInicioSesion.setVisibility(View.GONE);
                layoutRegistro.setVisibility(View.VISIBLE);
            }
        }
        botonInicioCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutRegistro.setVisibility(View.GONE);
                layoutInicioSesion.setVisibility(View.VISIBLE);
            }
        });
        botonRegistroCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInicioSesion.setVisibility(View.GONE);
                layoutRegistro.setVisibility(View.VISIBLE);
            }
        });
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textoRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityInicioSesionRegistro.this,RecuperarActivity.class);
                startActivity(intent);
            }
        });
    }
}