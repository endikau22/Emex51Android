package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import intefaces.UserInterface;
import model.Sectores;
import model.User;
import model.Visitor;
import retrofit.UserRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //Recoger los datos del intent
        Intent i = getIntent();
        Visitor visitor = (Visitor)i.getSerializableExtra("user_logged_in");

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldPass.getText().toString().equals("")||newPAss.getText().toString().equals("")||
                        repeatNewPass.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Rellena los tres campos.",Toast.LENGTH_LONG).show();
                else if(!newPAss.getText().toString().trim().equalsIgnoreCase(repeatNewPass.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Las nuevas contraseñas deben coincidir.",Toast.LENGTH_LONG).show();
                }else{
                    //Los tres campos rellenos y las contraseñas coinciden
                    //Llamar a editpassword del servidor
                    UserInterface userInterface = UserRestClient.getUser();
                    Call<Void> user = userInterface.editChangePassword(oldPass.getText().toString().trim(),newPAss.getText().toString().trim(),visitor);
                    user.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "La contraseña se ha actualizado.", Toast.LENGTH_LONG).show();
                                //Volver a la activity anterior de mi cuenta
                                Intent intent = new Intent(ActivityEditPassword.this,ActivityMiCuenta.class);
                                startActivity(intent);
                            }else
                                Toast.makeText(getApplicationContext(),"La contraseña no se ha podido actualizar.",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Se ha producido un error"+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}