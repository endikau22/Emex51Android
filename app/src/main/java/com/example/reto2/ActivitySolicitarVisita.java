package com.example.reto2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import intefaces.SectorInterface;
import intefaces.VisitorInterface;
import model.Sector;
import model.Sectores;
import model.Visitor;
import retrofit.SectorRestClient;
import retrofit.VisitorRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySolicitarVisita extends AppCompatActivity {

    private TextView textViewSector = null;
    private CalendarView calendario = null;
    private Button botonEnviar = null;
    private String date = null;
    private Date fechaVisita = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_visita);

        textViewSector = findViewById(R.id.textViewSectorSolicitado);
        calendario = findViewById(R.id.calendarView);
        botonEnviar = findViewById(R.id.buttonSolicitarVisita);

        //Recojo el intent que viene de la activity iniciar sesion. Aqui viene  el id del visitor
        Intent i = getIntent();
        Integer idSector = i.getIntExtra("sector_elegido",0);
        Integer idVisitor = i.getIntExtra("visitor",0);

        SectorInterface sectorInterface = SectorRestClient.getSector();
        Call<Sector> sector = sectorInterface.find(idSector);
        sector.enqueue(new Callback<Sector>() {
            @Override
            public void onResponse(Call<Sector> call, Response<Sector> response) {
               if(response.isSuccessful()){
                   Sector sectorResponse = response.body();
                   textViewSector.setText(sectorResponse.getName());
               }else{
                   Toast.makeText(getApplicationContext(),"No se pudo recoger el sector.",Toast.LENGTH_LONG).show();
               }
            }

            @Override
            public void onFailure(Call<Sector> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error del servidor no se puede cargar el sector.",Toast.LENGTH_LONG).show();
            }
        });
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date == null){
                    Toast.makeText(getApplicationContext(),"Debes elegir una fecha.",Toast.LENGTH_LONG).show();
                }else{
                    VisitorInterface visitorInterface = VisitorRestClient.getVisitor();
                    Call<Visitor> call = visitorInterface.find(idVisitor);
                    call.enqueue(new Callback<Visitor>() {
                        @Override
                        public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                            if(response.isSuccessful()){
                                Visitor visitor = (Visitor) response.body();
                                visitor.setVisitaSolicitada(fechaVisita);
                                actualizarVisitante(visitor);
                            }else{
                                Toast.makeText(getApplicationContext(),"El visitante no volvio.",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Visitor> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Error del servidor no se puede cargar el visitante."+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "/" + month + "/" + (dayOfMonth+1);
                try {
                    fechaVisita = new SimpleDateFormat("yyyy/MM/dd").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void actualizarVisitante(Visitor visitor) {
        VisitorInterface visitorInterface = VisitorRestClient.getVisitor();
        Call<Visitor> call = visitorInterface.edit(visitor);
        call.enqueue(new Callback<Visitor>() {
            @Override
            public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Visita guardada",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ActivitySolicitarVisita.this,ActivitySectores.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"No se ha podido guardar la visita",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Visitor> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error."+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}