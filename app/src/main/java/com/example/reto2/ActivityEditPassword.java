package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import intefaces.UserInterface;
import intefaces.VisitorInterface;
import model.Sectores;
import model.User;
import model.Visitor;
import retrofit.UserRestClient;
import retrofit.VisitorRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditPassword extends AppCompatActivity {

    private EditText oldPass = null;
    private EditText newPAss = null;
    private EditText repeatNewPass = null;
    private Button guardar = null;
    private User visitante = null;
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
        int id = i.getIntExtra("visitorId",0);
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
                    visitante = buscarUser(id);
                    Call<Void> userCall = userInterface.editChangePassword(visitante.getEmail(),
                            cifradoPassword(oldPass.getText().toString().trim()),
                            cifradoPassword(newPAss.getText().toString().trim()));
                    userCall.enqueue(new Callback<Void>() {
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

    private User buscarUser(int id) {
        UserInterface userInterface = UserRestClient.getUser();
        Call<User> visitor = userInterface.find(id);
        visitor.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    visitante = (User) response.body();
                } else
                    Toast.makeText(getApplicationContext(), "No hay visitor", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Se ha producido un error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return visitante;
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