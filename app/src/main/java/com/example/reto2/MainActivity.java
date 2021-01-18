package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView imagenLottie = null;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagenLottie = (LottieAnimationView) findViewById(R.id.imageViewLottie);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        Animation animacionBoton = AnimationUtils.loadAnimation(this,R.anim.animacion);
        imagenLottie.setVisibility(View.VISIBLE);
        imagenLottie.playAnimation();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,ActivityPortada.class);
                startActivity(intent);
            }
        }, 4100);

    }

}