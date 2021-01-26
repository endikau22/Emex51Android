package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityEditPassword extends AppCompatActivity {

    private EditText oldPass = null;
    private EditText newPAss = null;
    private EditText repeatNewPass = null;
    private Button guardar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        oldPass = findViewById(R.id.editOldPassword);
        newPAss = findViewById(R.id.editNewPassword);
        repeatNewPass = findViewById(R.id.editRepeatNewPassword);
        guardar = findViewById(R.id.buttonGuardarCambioContrsenia);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}