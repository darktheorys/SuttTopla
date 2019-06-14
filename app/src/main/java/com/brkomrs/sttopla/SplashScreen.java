package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;

import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

ConstraintLayout img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.zoomOutLayout);

         Animation zoomMoveAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out_move_up);

        img.startAnimation(zoomMoveAnim);
        Toast.makeText(SplashScreen.this, getString(R.string.connection_str), Toast.LENGTH_SHORT).show();

        // we move to the next page after animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                finish();
            }
        }, 2000);
    }



}
