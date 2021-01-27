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
import android.widget.Toast;

import intefaces.VisitorInterface;
import model.Visitor;
import musica.AudioPlay;
import retrofit.VisitorRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMiCuenta extends AppCompatActivity {

    private Button contrasenia = null;
    private Button eliminarCuenta = null;
    private ImageButton musica = null;
    private TextView login = null;
    private TextView email = null;
    private Boolean encenderAudio = false;
    private SQLiteDatabase bd = null;
    private Visitor visitor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);

        contrasenia = findViewById(R.id.buttonModificarPassword);
        musica = findViewById(R.id.imageButtonMusica);
        login = findViewById(R.id.textViewNombreMicuenta);
        email = findViewById(R.id.textViewEmailMiCuenta);
        bd = openOrCreateDatabase("emex51db", Context.MODE_PRIVATE,null);
        eliminarCuenta = findViewById(R.id.buttonEliminarCuenta);


        //Recojo el intent que viene de la activity sesion. Aqui viene  el id del visitor
        Intent i = getIntent();
        Integer idVisitor = i.getIntExtra("user_logged_in",0);

        //Vamos a buscar el visitor por id al servidor
        VisitorInterface visitorInterface = VisitorRestClient.getVisitor();
        Call<Visitor> call = visitorInterface.find(idVisitor);
        call.enqueue(new Callback<Visitor>() {
            @Override
            public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                if(response.isSuccessful()){
                    visitor = response.body();
                    login.setText(visitor.getLogin());
                    email.setText(visitor.getEmail());
                }else{
                    Toast.makeText(getApplicationContext(),"Registro incorrecto",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Visitor> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Se ha producido un error"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisitorInterface visitorInterface1 = VisitorRestClient.getVisitor();
                Call<Void> call = visitorInterface1.remove(idVisitor);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Usuario eliminado",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActivityMiCuenta.this,ActivityPortada.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Registro incorrecto",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Se ha producido un error"+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        contrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMiCuenta.this,ActivityEditPassword.class);
                intent.putExtra("visitorId",idVisitor);
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