package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.brkomrs.sttopla.data.DutyInf;

public class FormScreen extends AppCompatActivity {

    private DutyInf duty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);
        Intent i = getIntent();
        duty = (DutyInf) i.getSerializableExtra("duty");

        //TextView tw = findViewById(R.id.duty_name);

        //tw.setText(duty.getDate());


    }


    int temp = 1;
    @Override
    public void onBackPressed() {
        if(temp == 0){
            finish();
        }else{
            Toast.makeText(FormScreen.this, getString(R.string.not_submit_str), Toast.LENGTH_LONG).show();
            temp--;

            //if in 4 seconds, user cannot press second time, it resets counter to prevent misdoings
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    temp++;
                }
            }, 4000);
        }


    }
}
