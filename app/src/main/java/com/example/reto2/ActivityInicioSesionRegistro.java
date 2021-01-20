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
import android.widget.Toast;

import intefaces.UserInterface;
import model.User;
import model.UserPrivilege;
import retrofit.UserRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        recuerdame = findViewById(R.id.switchRecuerdo);

        bd = openOrCreateDatabase("emex51db", Context.MODE_PRIVATE,null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS visitor (login VARCHAR,password VARCHAR,musica BOOLEAN,recordar BOOLEAN);");
        //Hacer aqui una select y si recordar esta a true dejar el usuario. desde la portada pasar de esta activity. se puede usar la bd en otra activity?
        //Vemos que boton le  ha pulsado para abrir una u otra o ellayout iniciosesion o registro
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
                if(textoLoginInicioSesion.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"El campo login debe estar informado.",Toast.LENGTH_SHORT);
                else if(textoPasswordInicioSesion.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"El campo password debe estar informado.",Toast.LENGTH_SHORT);
                else{
                    //Login y password los textField están informados llamar a retrofit para consultar con la bbdd
                    UserInterface userInterface = UserRestClient.getUser();
                    Call<User> call = userInterface.findUsersByLogin(textoLoginInicioSesion.getText().toString().trim());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            //El servidor devuelve entre un 200 y un 300. Todo OK. En este caso ! not. Devuelve un error 400 500 o lo que sea
                            if(!response.isSuccessful()){
                                textoLoginInicioSesion.setText("");
                                textoPasswordInicioSesion.setText("");
                                Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT);
                                return;
                            }
                            //Si no hace return sigue por aqui el servidor ha devuelto un 200 Ok.
                            User userResponse = response.body();
                            if(userResponse.getPassword().equalsIgnoreCase(textoPasswordInicioSesion.getText().toString())
                                    && userResponse.getPrivilege() == UserPrivilege.VISITOR){
                                //Me ha devuelto un usuario, he comparado las contraseñas y ademas es visitor.
                                //Guardar en la sqlite el login y password y si tiene el switch recuerdame poner a true para la siguiente vez.
                                Intent intent = new Intent(ActivityInicioSesionRegistro.this,ActivityPrincipal.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            textoLoginInicioSesion.setText("");
                            textoPasswordInicioSesion.setText("");
                            Toast.makeText(getApplicationContext(),"Se ha producido un error",Toast.LENGTH_SHORT);
                            return;
                        }
                    });
                }
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