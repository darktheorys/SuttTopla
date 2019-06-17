package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import android.widget.Toast;

import com.brkomrs.sttopla.data.DutyInf;

import java.util.ArrayList;
public class FormScreen extends AppCompatActivity {
    private Spinner alcohol_type;
    private Spinner milktype;
    private Spinner milktemp,refracTemp;
    private DutyInf duty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);
        Intent i = getIntent();
        duty = (DutyInf) i.getSerializableExtra("duty");

        //TextView tw = findViewById(R.id.duty_name);
        //tw.setText(duty.getDate());


        //temp spinner, if user chooses more than 6 degree this will show up
        final ArrayList<String> milktemps = new ArrayList<>();
        milktemps.add("6.5");
        milktemps.add("7");
        milktemps.add("7.5");
        milktemps.add("8");
        milktemps.add("8.5");
        milktemps.add("9");
        milktemps.add("9.5");
        milktemps.add("10");
        milktemp = findViewById(R.id.temp_spin);
        ArrayAdapter<String> dataAdapterMilkTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, milktemps);
        dataAdapterMilkTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milktemp.setAdapter(dataAdapterMilkTemps);

        //temp spinner, if user chooses more than 6 degree this will show up
        final ArrayList<String> refracTemps = new ArrayList<>();
        refracTemps.add("9");
        refracTemps.add("8.5");
        refracTemps.add("8");
        refracTemps.add("7.5");
        refracTemps.add("7");
        refracTemps.add("7.5");
        refracTemps.add("7");
        refracTemps.add("6.5");
        refracTemps.add("6");


        refracTemp = findViewById(R.id.refrac_temp_spin);
        ArrayAdapter<String> dataAdapterRefracTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, refracTemps);
        dataAdapterRefracTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refracTemp.setAdapter(dataAdapterRefracTemps);


        final RadioGroup temp_radio = findViewById(R.id.radiotemp_lay);
        temp_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if(checked.getText().toString().equalsIgnoreCase(getString(R.string.more6_str))){
                    milktemp.setVisibility(View.VISIBLE);
                }else{
                    milktemp.setVisibility(View.INVISIBLE);
                }
            }
        });

        final RadioGroup temp_refrac_radio = findViewById(R.id.refrac_radio_lay);
        temp_refrac_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if(checked.getText().toString().equalsIgnoreCase(getString(R.string.below95_str))){
                    refracTemp.setVisibility(View.VISIBLE);
                }else{
                    refracTemp.setVisibility(View.INVISIBLE);
                }
            }
        });




        //milk types spinner, if user chooses milk test inputs will be visible
        final ArrayList<String> milktypes = new ArrayList<>();
        milktypes.add("İnek");
        milktypes.add("Keçi");
        milktypes.add("Koyun");
        milktypes.add("Pastörize Süt");
        milktypes.add("Peyniraltı Suyu");
        milktypes.add("Su");
        milktypes.add("Krema");
        milktype = findViewById(R.id.milk_type_spin);
        ArrayAdapter<String> dataAdapterMilkTypes = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, milktypes);
        dataAdapterMilkTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milktype.setAdapter(dataAdapterMilkTypes);

        milktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout lay = findViewById(R.id.tests_lay);
                if(i == 0 || i == 1 || i == 2){

                    lay.setVisibility(View.VISIBLE);
                }
                else{
                    lay.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                LinearLayout lay = findViewById(R.id.tests_lay);
                lay.setVisibility(View.INVISIBLE);
            }
        });




        //if alcohol exists, app will make visible a spinner to choose color
        final ArrayList<String> colors = new ArrayList<>();
        colors.add("Mavi");
        colors.add("Yeşil");
        colors.add("Mavi - Yeşil");
        colors.add("Sarı");
        alcohol_type = findViewById(R.id.alcohol_type_spin);
        ArrayAdapter<String> dataAdapterAlcoholTypes = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, colors);
        dataAdapterAlcoholTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alcohol_type.setAdapter(dataAdapterAlcoholTypes);

        final RadioGroup alcohol_radio = findViewById(R.id.radioalcohol_lay);
        alcohol_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if(checked.getText().toString().equalsIgnoreCase(getString(R.string.alcohol_e_str))){
                    alcohol_type.setVisibility(View.VISIBLE);
                }else{
                    alcohol_type.setVisibility(View.INVISIBLE);
                }


            }
        });


        //the taken amount of milk radio buttons
        final RadioGroup detail = findViewById(R.id.amount_detail_lay);
        final RadioGroup taken_amount = findViewById(R.id.taken_amount_lay);
        taken_amount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if(checked.getText().toString().equalsIgnoreCase(getString(R.string.leave_milk_str))){
                    detail.setVisibility(View.VISIBLE);
                }else{
                    detail.setVisibility(View.INVISIBLE);
                }


            }
        });


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
