package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import intefaces.UserInterface;
import model.User;
import retrofit.UserRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarActivity extends AppCompatActivity {

    private EditText email = null;
    private Button enviar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        email = findViewById(R.id.editTextEmailOlvidado);
        enviar = findViewById(R.id.buttonEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim() == "")
                    Toast.makeText(getApplicationContext(),"El campo email esta vacio",Toast.LENGTH_LONG).show();
                else {
                    User user = new User();
                    user.setPassword(email.getText().toString().trim());
                    UserInterface userInterface = UserRestClient.getUser();
                    //Esta no s porque en vez de mandar el mail mando un usuario asi que tengo que crear un usuario
                    Call<Void> email = userInterface.editForgotPassword(user);
                    email.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Te llegara un mail con la nueva contraseña",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RecuperarActivity.this, ActivityInicioSesionRegistro.class);
                                startActivity(i);
                            }else if(response.code() ==403)
                                Toast.makeText(getApplicationContext(),"El email no esta registrado",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(),"Vuelve a intentarlo",Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Error"+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}