package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
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
        bd.execSQL("CREATE TABLE IF NOT EXISTS tableVisitor (login VARCHAR,password VARCHAR,musica INTEGER,recordar INTEGER);");
        //Rellenar el recuerdame. Si es 1 es true. Rellenar los textfields
        Cursor cursor = bd.rawQuery("SELECT * FROM tableVisitor",null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                if(cursor.getInt(3)==1){
                    textoLoginInicioSesion.setText(cursor.getString(0));
                    textoPasswordInicioSesion.setText(cursor.getString(1));
                }else{
                    textoLoginInicioSesion.setText("");
                    textoPasswordInicioSesion.setText("");
                }
            }
        }else{
            textoLoginInicioSesion.setText("");
            textoPasswordInicioSesion.setText("");
        }

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
                    VisitorInterface visitorInterface = VisitorRestClient.getVisitor();
                    Call<Visitor> call = visitorInterface.loginVisitor(textoLoginInicioSesion.getText().toString().trim(),
                            cifradoPassword(textoPasswordInicioSesion.getText().toString().trim()));
                    call.enqueue(new Callback<Visitor>() {
                        @Override
                        public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                            //Devuelve un error 400 500 o lo que sea
                            if (!response.isSuccessful()) {
                                textoLoginInicioSesion.setText("");
                                textoPasswordInicioSesion.setText("");
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                            } else {//El servidor devuelve entre un 200 y un 300
                                //Si no hace return sigue por aqui el servidor ha devuelto un 200 Ok.
                                Visitor visitorResponse = response.body();
                                //Guardar en la sqlite el login y password y si tiene el switch recuerdame poner a true para la siguiente vez.
                                if (recuerdame.isChecked())
                                    guardarDatosSQLite(visitorResponse.getLogin(), textoPasswordInicioSesion.getText().toString().trim()
                                            , true);
                                //Ya estan los datos guardados en la sqlite ahora intent para entrar en la aplicacion
                                Intent intent = new Intent(ActivityInicioSesionRegistro.this, ActivitySectores.class);
                                //si esto no funciona pasar el objeto entero, pasar el id solo por ejemplo
                                intent.putExtra("user_logged_in", visitorResponse.getId());
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<Visitor> call, Throwable t) {
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
                    visitor.setPassword(cifradoPassword(textoPasswordRegistro.getText().toString().trim()));
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

    private void guardarDatosSQLite(String login, String pass, boolean switchRecuerdoActivo) {
        Cursor cursor = bd.rawQuery("SELECT * FROM t_visitor WHERE login = '"+login+"'",null);
        if(cursor.getCount()==0){
            //si es true el switch meto 1 ssino 0 porque lo meto como integer
            if(switchRecuerdoActivo)
                bd.execSQL("Insert into tableVisitor (login,password,recordar) VALUES ('"+login+"','"+pass+"','"+1+"')");
            else
                bd.execSQL("Insert into tableVisitor (login,password,recordar) VALUES ('"+login+"','"+pass+"','"+0+"')");
        }else{
            if(switchRecuerdoActivo)
                bd.execSQL("UPDATE tableVisitor SET recordar = '"+ 1+"' WHERE login = '"+login+"'");
            else
                bd.execSQL("UPDATE tableVisitor SET recordar = '"+ 0+"' WHERE login = '"+login+"'");
        }
    }
    private String cifradoPassword(String mensaje) {
        byte[] encodedMessage = null;
        String encodedMessageHex = null;
        try {
            int fileId = getResources().getIdentifier("public_key","raw",getPackageName());
            InputStream inputStream = this.getResources().openRawResource(fileId);
            //en inputstream estael fichero ahora lo lee
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int leidos;
            byte data [] = new byte[1024];
            while((leidos = inputStream.read(data,0,data.length))!=-1){
                byteArrayOutputStream.write(data,0,leidos);
            }
            byteArrayOutputStream.flush();
            byte[] fileKey = byteArrayOutputStream.toByteArray();
            //En filekey esta el contenido del fichero
            //cifra
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipher.doFinal(mensaje.getBytes());

            encodedMessageHex = byteToHex(encodedMessage);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Error al cifrar password "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return encodedMessageHex;
    }
    /**
     * This method converts the byte array text received to hexadecimal String.
     *
     * @param byteText byte array text to convert.
     * @return converted text in hexadecimal.
     */
    private static String byteToHex(byte[] byteText) {
        String hexText = "";
        for (int i = 0; i < byteText.length; i++) {
            String h = Integer.toHexString(byteText[i] & 0xFF);
            if (h.length() == 1) {
                hexText += "0";
            }
            hexText += h;
        }
        return hexText.toUpperCase();
    }
}