package com.brkomrs.sttopla;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.Toast;

import com.brkomrs.sttopla.data.DutyInf;
import com.brkomrs.sttopla.data.FarmInf;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MissionSelectScreen extends AppCompatActivity{
    private File configfile = null;
    private Spinner spin;
    ArrayAdapter<DutyInf> dataAdapterMissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionselect);

        Button goSelected = findViewById(R.id.go_duty_btn);
        spin = findViewById(R.id.mission_spin);
        Button changeID = findViewById(R.id.changeid_btn);
        configfile = new File(MissionSelectScreen.this.getFilesDir(),getString(R.string.configfilename_str));

        ArrayList<DutyInf> duties = new ArrayList<>();
        duties.add(new DutyInf("A", 123, "11.11.11"));
        duties.add(new DutyInf("B", 12333, "11.11.11"));
        duties.add(new DutyInf("C", 1233, "11.11.11"));

        //id change button send to login screen
        changeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChangeId();
            }
        });


        dataAdapterMissions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, duties);
        dataAdapterMissions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapterMissions);

        goSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MissionSelectScreen.this, FormScreen.class);
                Bundle b = new Bundle();
                b.putSerializable("duty", (Serializable) spin.getSelectedItem());
                i.putExtras(b);
                startActivity(i);
            }
        });


    }


    private void goChangeId(){

        String[] str = LoginScreen.readNLineFromFile(configfile,2);
        LoginScreen.writeToFile(configfile, str[0], false);
        LoginScreen.writeToFile(configfile, "false", true);
        finish();
        startActivity(new Intent(MissionSelectScreen.this, LoginScreen.class));

    }


    //to exit user push back button two times
    // temp value makes count
    int temp = 1;
    @Override
    public void onBackPressed() {
        if(temp == 0){
            finish();
        }
        temp--;
        Toast.makeText(MissionSelectScreen.this, getString(R.string.to_exit_str) ,Toast.LENGTH_LONG).show();

        //if in 4 seconds, user cannot press second time, it resets counter to prevent misdoings
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                temp++;
            }
        }, 4000);

    }




}
