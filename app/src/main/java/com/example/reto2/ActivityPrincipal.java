package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import intefaces.UserInterface;
import model.User;
import model.Users;
import retrofit.UserRestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPrincipal extends AppCompatActivity implements Callback<User>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        UserInterface userInterface = UserRestClient.getUser();
        Call <User> callLogIn = userInterface.find("1");
        //Call <Users> usersList = userInterface.findAllEmployees();
        callLogIn.enqueue((Callback<User>) this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.isSuccessful()){
            User user = (User) response.body();

        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

    }
}