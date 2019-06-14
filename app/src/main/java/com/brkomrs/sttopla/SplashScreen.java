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
AnimationSet as;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.zoomOutLayout);



        as = new AnimationSet(true);
        as.setFillEnabled(true);
        as.setFillAfter(true);


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        TranslateAnimation ta = new TranslateAnimation(0, -1 * metrics.widthPixels + metrics.widthPixels/2, 0, -1 * metrics.heightPixels + metrics.heightPixels/2);
        ta.setDuration(1500);
        as.addAnimation(ta);

        ScaleAnimation sa = new ScaleAnimation(1.0f, 0.6f, 1.0f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(1500);
        as.addAnimation(sa);




         //Animation zoomMoveAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out_move_up);

        img.startAnimation(as);
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
