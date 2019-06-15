package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.brkomrs.sttopla.data.DutyInf;

public class FormScreen extends AppCompatActivity {

    private DutyInf duty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);
        Intent i = getIntent();
        duty = (DutyInf) i.getSerializableExtra("duty");

        TextView tw = findViewById(R.id.duty_name);

        tw.setText(duty.getDate());


    }


}
