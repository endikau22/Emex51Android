package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EntryActivity extends AppCompatActivity {

    private Button botonIniciaSesion = null;
    private Button botonRegistro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        botonIniciaSesion = (Button) findViewById(R.id.buttonIniciaSesionEntry);
        botonRegistro = (Button) findViewById(R.id.buttonRegistroActivityInicio);

        botonIniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntryActivity.this,InicioActivity.class);
                intent.putExtra("Entry","FromActivityInicio");
                startActivity(intent);
            }
        });
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntryActivity.this,InicioActivity.class);
                intent.putExtra("Entry","FromActivityRegistro");
                startActivity(intent);
            }
        });
    }
}