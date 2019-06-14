package com.brkomrs.sttopla;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;


import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;






public class MissionSelectScreen extends AppCompatActivity {
    private String[] missions = {"Görev1", "Görev2", "Görev3"};

    private File configfile = null;
    private Button changeID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionselect);


        changeID = findViewById(R.id.changeid_btn);
        configfile = new File(MissionSelectScreen.this.getFilesDir(),getString(R.string.configfilename_str));

        changeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChangeId();
            }
        });

        ArrayAdapter<String> dataAdapterMissions;
        Spinner spin = findViewById(R.id.mission_spin);

        dataAdapterMissions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, missions);
        dataAdapterMissions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapterMissions);


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
