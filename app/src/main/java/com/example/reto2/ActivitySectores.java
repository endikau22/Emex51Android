package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import model.Sectores;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

import intefaces.SectorInterface;
import model.Sector;
import model.Sectores;
import model.Visitor;
import retrofit.SectorRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySectores extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listViewSectores = null;
    private Button botonIrCuenta = null;
    private List<Sector> arrayListSectores = null;
    private List<String> arrayStringSectores = null;
    private Visitor visitor = null;
    private ConstraintLayout layoutBar = null;
    private ConstraintLayout layoutSector = null;
    private ProgressBar progressBar = null;
    private CountDownTimer cdt = null;

    private ListView listViewSectores = null;
    private Button buttonVolver = null;
    private Button buttonSeleccionar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectores);

        listViewSectores = findViewById(R.id.listaSectores);
        layoutBar = findViewById(R.id.layoutProgress);
        layoutSector = findViewById(R.id.layoutSectores);
        botonIrCuenta = findViewById(R.id.buttonIrCuentaUsuario);
        arrayListSectores = new ArrayList<>();
        arrayStringSectores = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        cdt = new CountDownTimer(4000, 30) {
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressBar.getProgress()+1);
            }
            public void onFinish() {
                layoutBar.setVisibility(View.GONE);
                layoutSector.setVisibility(View.VISIBLE);
            }
        }.start();

        //Recojo el intent que viene de la activity iniciar sesion. Aqui viene  el id del visitor
        Intent i = getIntent();
        Integer idVisitor = i.getIntExtra("user_logged_in",0);
        //Esto lo hago para pasar en el item selected no alcanza la variable
        visitor = new Visitor();
        visitor.setId(idVisitor);

        //Cargar los sectores desde la base de datos
        SectorInterface sectorInterface = SectorRestClient.getSector();
        Call <Sectores> sectores = sectorInterface.findAll();
        sectores.enqueue(new Callback<Sectores>() {
            @Override
            public void onResponse(Call<Sectores> call, Response<Sectores> response) {
                if(response.isSuccessful()){
                    Sectores sectores = (Sectores) response.body();
                    arrayListSectores = sectores.getSectors();
                    for(Sector s: arrayListSectores){
                        //Pasar a un arraylist de string porque no se sino se puede con  el objeto sector entero
                        arrayStringSectores.add(s.getName());
                        //Añadir al listview
                        anadirSectoresListView(arrayStringSectores);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"No hay sectores que mostrar.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Sectores> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Se ha producido un error"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        //Hacemos la lista clickable
        listViewSectores.setOnItemClickListener(this);

        botonIrCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySectores.this,ActivityMiCuenta.class);
                intent.putExtra("visitorId",idVisitor);
                startActivity(intent);
            }
        });
    }

    private void anadirSectoresListView(List<String> sectores) {
        ArrayAdapter<String> adaptador;
        adaptador=new ArrayAdapter<>(this,R.layout.lista_fila,sectores);
        listViewSectores.setAdapter(adaptador);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ActivitySectores.this,CalendarioActivity.class);
    //Guardar el sector, cojo el del arraylist que coincide con la posicion del de la listview
        Sector sector = arrayListSectores.get(position);
        intent.putExtra("sectorelegido",sector.getIdSector());
        intent.putExtra("visitor",visitor.getId());
        startActivity(intent);
    }
}