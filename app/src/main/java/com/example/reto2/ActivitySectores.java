package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import model.Sectores;
import retrofit2.Call;

public class ActivitySectores extends AppCompatActivity {

    private ListView listViewSectores = null;
    private Button buttonVolver = null;
    private Button buttonSeleccionar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectores);

        buttonVolver = findViewById(R.id.buttonVolver);
        buttonSeleccionar = findViewById(R.id.buttonSeleccionar);
        listViewSectores = findViewById(R.id.listViewSectores);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        buttonSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySectores.this,ActivitySolicitarVisita.class);
                intent.putExtra("Seleccionar","FromActivitySectores");
                startActivity(intent);
            }
        });

    }


    private void listarSectores(){


    }
}