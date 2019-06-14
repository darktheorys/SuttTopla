package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.animation.ObjectAnimator;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.Animation;

import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

ConstraintLayout img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.zoomOutLayout);


        ScaleAnimation sa = new ScaleAnimation(1.0f, 0.6f, 1.0f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setFillAfter(true);
        sa.setDuration(1500);

        img.startAnimation(sa);
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
