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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import intefaces.UserInterface;
import intefaces.VisitorInterface;
import model.User;
import model.UserPrivilege;
import model.Visitor;
import retrofit.UserRestClient;
import retrofit.VisitorRestClient;
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
        textoLoginInicioSesion = findViewById(R.id.editTextLoginInicioSesion);
        textoPasswordInicioSesion = findViewById(R.id.editTextTextPasswordInicioSesion);
        textoDNIRegistro = findViewById(R.id.editTextDniRegistro);
        textoNombreRegistro = findViewById(R.id.editTextNameRagistro);
        textoEmailRegistro = findViewById(R.id.editTextCorreoElectronicoRegistro);
        textoLoginRegistro = findViewById(R.id.editTextLoginRegistro);
        textoPasswordRegistro = findViewById(R.id.editTextPasswordRegistro);
        aceptarCondicionesRegistro = findViewById(R.id.checkBoxCondiciones);

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
                    Toast.makeText(getApplicationContext(),"El campo login debe estar informado.",Toast.LENGTH_SHORT).show();
                else if(textoPasswordInicioSesion.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"El campo password debe estar informado.",Toast.LENGTH_SHORT).show();
                else{
                    //Login y password los textField están informados llamar a retrofit para consultar con la bbdd
                    UserInterface userInterface = UserRestClient.getUser();
                    Call<User> call = userInterface.loginUser(textoLoginInicioSesion.getText().toString().trim(),
                            textoPasswordInicioSesion.getText().toString().trim());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            //El servidor devuelve entre un 200 y un 300
                            //Todo ok. En este  not. Devuelve un error 400 500 o lo que sea
                            if(!response.isSuccessful()){
                                textoLoginInicioSesion.setText("");
                                textoPasswordInicioSesion.setText("");
                                Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();
                            }else{
                                //Si no hace return sigue por aqui el servidor ha devuelto un 200 Ok.
                                User userResponse = response.body();
                                if(userResponse.getPrivilege() == UserPrivilege.VISITOR){
                                    //Me ha devuelto un usuario, he comparado las contraseñas y ademas es visitor.
                                    //Guardar en la sqlite el login y password y si tiene el switch recuerdame poner a true para la siguiente vez.
                                    Intent intent = new Intent(ActivityInicioSesionRegistro.this,ActivityPrincipal.class);
                                    startActivity(intent);
                                }else
                                    Toast.makeText(getApplicationContext(),"App solo para visitantes",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            textoLoginInicioSesion.setText("");
                            textoPasswordInicioSesion.setText("");
                            Toast.makeText(getApplicationContext(),"Se ha producido un error"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textoDNIRegistro.getText().toString().equals("")||textoEmailRegistro.getText().toString().equals("")||
                        textoLoginRegistro.getText().toString().equals("")||textoNombreRegistro.getText().toString().equals("")||
                            textoPasswordRegistro.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Rellena todos los campos.",Toast.LENGTH_SHORT).show();
                else if(!aceptarCondicionesRegistro.isChecked())
                    Toast.makeText(getApplicationContext(),"Acepta las condiciones antes de registrarte.",Toast.LENGTH_SHORT).show();
                else{
                    //Crear visitante
                    Visitor visitor = new Visitor();
                    visitor.setDni(textoDNIRegistro.getText().toString().trim());
                    visitor.setEmail(textoEmailRegistro.getText().toString().trim());
                    visitor.setFullName(textoNombreRegistro.getText().toString().trim());
                    //visitor.setPassword(cifradoPassword(textoPasswordRegistro.getText().toString().trim()));
                    visitor.setPassword(textoPasswordRegistro.getText().toString().trim());
                    visitor.setLogin(textoLoginRegistro.getText().toString().trim());
                    VisitorInterface visitorInterface = VisitorRestClient.getVisitor();
                    Call<Void> call = visitorInterface.create(visitor);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Usuario registrado",Toast.LENGTH_SHORT).show();
                                layoutRegistro.setVisibility(View.GONE);
                                layoutInicioSesion.setVisibility(View.VISIBLE);
                            }else{
                                Toast.makeText(getApplicationContext(),"Registro incorrecto",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            textoLoginRegistro.setText("");
                            textoPasswordRegistro.setText("");
                            textoDNIRegistro.setText("");
                            textoEmailRegistro.setText("");
                            textoNombreRegistro.setText("");
                            aceptarCondicionesRegistro.setChecked(false);
                            Toast.makeText(getApplicationContext(),"Registro denegado. Se ha producido un error"+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
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

    private String cifradoPassword(String password){
        String hexText = "";
        try{
            int fileId = getResources().getIdentifier("public_key","raw",getPackageName());
            InputStream inputStream = getResources().openRawResource(fileId);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int leidos;
            byte data [] = new byte[1024];
            while((leidos = inputStream.read(data,0,data.length))!=-1){
                byteArrayOutputStream.write(data,0,leidos);
            }
            byteArrayOutputStream.flush();
            byte[] fileKey = byteArrayOutputStream.toByteArray();
            for (int i = 0; i < fileKey.length; i++) {
                String h = Integer.toHexString(fileKey[i] & 0xFF);
                if (h.length() == 1) {
                    hexText += "0";
                }
                hexText += h;
            }
        }catch(IOException e){
            Toast.makeText(getApplicationContext(),"Error al cifrar password "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return hexText.toUpperCase();
    }
}