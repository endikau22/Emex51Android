package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class ActivitySolicitarVisita extends AppCompatActivity {

    private Button buttonVolver = null;
    private Button buttonSolicitarVisita = null;
    private CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_visita);

        buttonVolver = findViewById(R.id.buttonVolver);
        buttonSolicitarVisita = findViewById(R.id.buttonSolicitarVisita);
        calendarView = findViewById(R.id.calendarView);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySolicitarVisita.this,ActivitySectores.class);
                intent.putExtra("Seleccionar","FromActivitySectores");
                startActivity(intent);
            }
        });

        buttonSolicitarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}