package com.brkomrs.sttopla;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.brkomrs.sttopla.database.DutyInf;
import com.brkomrs.sttopla.database.DutyInfDao;
import org.greenrobot.greendao.query.QueryBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionSelectScreen extends AppCompatActivity{
    private File configfile = null;
    List<DutyInf>  duties;
    private Spinner spin;
    private long id;
    private Button goSelected;
    private SwipeRefreshLayout swipes;
    private ArrayAdapter<DutyInf> dataAdapterMissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionselect);

        swipes = findViewById(R.id.swipe);

        //getting user id from previous activity to get true duties from database
        String user = getIntent().getStringExtra("user_id");
        if (user != null  && !user.equals("")) {
            id = Long.parseLong(user);
        }else{
            Log.e("aslfkşsakf","@@@@@@@@@@@@@@@@qq");
        }


        goSelected = findViewById(R.id.go_duty_btn);
        spin = findViewById(R.id.mission_spin);
        Button changeID = findViewById(R.id.changeid_btn);
        configfile = new File(MissionSelectScreen.this.getFilesDir(),getString(R.string.configfilename_str));

        //getting duties for entrance
        getDuties();

        //id change button send to login screen
        changeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChangeId();
            }
        });



        //going next activity with selected duty
        goSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MissionSelectScreen.this, FormScreen.class);
                i.putExtra("duty_id", duties.get(spin.getSelectedItemPosition()).getDutyId().toString());
                startActivity(i);
            }
        });


        //swipe arrangement
        swipes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDuties();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipes.setRefreshing(false);
                    }
                }, 4000);
            }
        });



    }

    //duty getter fuction
    private void getDuties() {
        duties = new ArrayList<>();
        if(LoginScreen.haveConnection(MissionSelectScreen.this)){
            QueryBuilder<DutyInf> qb = ((dbHelper)getApplication()).getDaoSession().getDutyInfDao().queryBuilder();
            List<DutyInf> temp = qb.where(DutyInfDao.Properties.User.eq(id)).list();
            for (DutyInf each : temp){
                if (!each.getDone()) duties.add(each);
            }
            dataAdapterMissions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, duties);
            dataAdapterMissions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(dataAdapterMissions);
        }else{
            Toast.makeText(MissionSelectScreen.this, getString(R.string.connection_err_str), Toast.LENGTH_SHORT).show();
        }

        if(duties.size() == 0){
            goSelected.setEnabled(false);
        }

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
