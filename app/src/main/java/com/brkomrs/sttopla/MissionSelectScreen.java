package com.brkomrs.sttopla;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.brkomrs.sttopla.database.DaoSession;
import com.brkomrs.sttopla.database.DutyInf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.brkomrs.sttopla.database.MilkInf;
import com.brkomrs.sttopla.database.TankInf;
import com.brkomrs.sttopla.database.TruckInf;
import com.brkomrs.sttopla.necessary.helperFunctions;

public class MissionSelectScreen extends AppCompatActivity{
    private List<DutyInf>  duties;
    private Spinner spin;
    private int user_id;
    private Button goSelected, showPrev;
    private SwipeRefreshLayout swipes;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionselect);

        //if service is not running start
        if(!helperFunctions.serviceWorking(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(),PushService.class);
            startService(intent);
        }

        //db session
        daoSession = ((dbHelper)getApplication()).getDaoSession();

        //getters for widgets
        showPrev = findViewById(R.id.prevsubs_btn);
        swipes = findViewById(R.id.swipe);
        goSelected = findViewById(R.id.go_duty_btn);
        spin = findViewById(R.id.mission_spin);

        //local buton is enough
        Button changeID = findViewById(R.id.changeid_btn);
        //id change button send to login screen
        changeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //arrangement of config file
                File configfile = new File(MissionSelectScreen.this.getFilesDir(),getString(R.string.configfilename_str));
                goChangeId(configfile);
            }
        });

        //getting user id from previous activity to get true duties from database
        String userid_temp_str = getIntent().getStringExtra(getString(R.string.user_id_extra_str));
        if (userid_temp_str != null  && !userid_temp_str.equals("")) {
            user_id = Integer.parseInt(userid_temp_str);
        }


        //getting duties for entrance
        getUndoneDutiesToSpinner(helperFunctions.haveConnection(MissionSelectScreen.this));

        //going next activity with selected duty
        goSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MissionSelectScreen.this, FormScreen.class);
                i.putExtra(getString(R.string.duty_id_extra_str), duties.get(spin.getSelectedItemPosition()).getId().toString());
                startActivity(i);
            }
        });



        helperFunctions.sendPost(daoSession);

        //swipe arrangement
        swipes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                helperFunctions.sendPost(daoSession);
                getUndoneDutiesToSpinner(helperFunctions.haveConnection(MissionSelectScreen.this));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipes.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        showPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goShowPrevs();
            }
        });
    }

    private void goShowPrevs() {
        try{

            ArrayList<MilkInf> prevMilks = new ArrayList<>();
            TruckInf truck = helperFunctions.getUser (daoSession, user_id).getTruck();
            truck.resetTanks();
            List<TankInf> allTanks = truck.getTanks();
            for (TankInf each : allTanks){
                prevMilks.addAll(each.getMilks());
            }
            final Dialog prevs = new Dialog(MissionSelectScreen.this);
            prevs.setContentView(R.layout.show_prevs);
            prevs.setTitle("Eski gönderilenler..");
            ListView lv = prevs.findViewById(R.id.prev_subs_lv);
            ArrayAdapter<MilkInf> veriAdaptoru=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, prevMilks);
            lv.setAdapter(veriAdaptoru);
            prevs.setCancelable(true);
            prevs.setCanceledOnTouchOutside(true);
            prevs.show();
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }

    }

    //duty getter fuction
    private void getUndoneDutiesToSpinner(boolean isConnected) {
        if(!isConnected){
            Toast.makeText(MissionSelectScreen.this,getString(R.string.connection_err_str), Toast.LENGTH_LONG).show();
        }
        duties = new ArrayList<>();
        ArrayAdapter<DutyInf> dataAdapterMissions;
        //all duties for given id
        List<DutyInf> temp = helperFunctions.getAllDuties(daoSession,user_id);
        for (DutyInf each : temp){
            //adding undone duties
            if (!each.getDone()) duties.add(each);
        }
        //setting spinner
        dataAdapterMissions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, duties);
        dataAdapterMissions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapterMissions);

        //if no duty remaining
        if(duties.size() == 0){
            spin.setEnabled(false);
            goSelected.setEnabled(false);
        }

    }


    private void goChangeId(File f){
        //resetting saved id
        helperFunctions.writeToFile(f, "", false);
        //setting autologin disabled
        helperFunctions.writeToFile(f, "false", true);
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
        }, 2000);

    }





}
