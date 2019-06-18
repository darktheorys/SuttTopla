package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.brkomrs.sttopla.database.DaoSession;
import com.brkomrs.sttopla.database.DutyInf;
import com.brkomrs.sttopla.database.DutyInfDao;
import com.brkomrs.sttopla.database.FarmInf;
import com.brkomrs.sttopla.database.FarmInfDao;
import com.brkomrs.sttopla.database.MilkInf;
import com.brkomrs.sttopla.database.TankInf;
import com.brkomrs.sttopla.database.TankInfDao;
import com.brkomrs.sttopla.database.TruckInf;
import com.brkomrs.sttopla.database.TruckInfDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class FormScreen extends AppCompatActivity {
    private Spinner alcohol_type;
    private Spinner milktype;
    private CheckBox c1,c2,c3,c4,c5;
    private Spinner milktemp_spin,refracTemp_spin;
    private String duty_id_str = "";
    private long duty_id;
    private RadioButton antibiotic,leaveMilk;
    private RadioGroup detail,taken_amount,alcohol_radio;
    private LinearLayout testLayout;
    private List<CheckBox> check_boxes_truck;
    private DaoSession ses;
    private long farmid,userid,truck_id;
    private int tank_n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);
        //getting duty id from previous page
        duty_id_str = getIntent().getStringExtra("duty_id");
        if(duty_id_str != null && !duty_id_str.equals("")){
            duty_id = Long.parseLong(duty_id_str);
        }

        ses = ((dbHelper) getApplication()).getDaoSession();

        QueryBuilder<DutyInf> dt = ses.getDutyInfDao().queryBuilder();
        List<DutyInf> ls = dt.where(DutyInfDao.Properties.DutyId.eq(duty_id)).list();
        if(ls.size() > 0){
            userid = ls.get(0).getUser();
            farmid = ls.get(0).getFarm_id();
        }

        QueryBuilder<TruckInf> other = ses.getTruckInfDao().queryBuilder();
        List<TruckInf> truck = other.where(TruckInfDao.Properties.User.eq(userid)).list();
        if(truck.size() > 0){
            tank_n = truck.get(0).getN_tank();
            truck_id = truck.get(0).getTruckId();
        }

        if(tank_n == 4);
        List<CheckBox> check_boxes_truck = new ArrayList<>();
        c1 = findViewById(R.id.tank1_chk);
        c2 = findViewById(R.id.tank2_chk);
        c3 = findViewById(R.id.tank3_chk);
        c4 = findViewById(R.id.tank4_chk);
        c5 = findViewById(R.id.tank5_chk);
        check_boxes_truck.add(c1);
        check_boxes_truck.add(c2);
        check_boxes_truck.add(c3);
        check_boxes_truck.add(c4);
        check_boxes_truck.add(c5);

        for(int i = 0; i < tank_n ; i++){
            check_boxes_truck.get(i).setVisibility(View.VISIBLE);
        }




        TextView tw = findViewById(R.id.duty_id_tv);
        String text = "Görev id: "+ duty_id_str;
        tw.setText(text);


        testLayout = findViewById(R.id.tests_lay);
        antibiotic = findViewById(R.id.antibiotic_e_radio);
        leaveMilk = findViewById(R.id.leave_milk_radio);

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
        milktemp_spin = findViewById(R.id.temp_spin);
        ArrayAdapter<String> dataAdapterMilkTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, milktemps);
        dataAdapterMilkTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milktemp_spin.setAdapter(dataAdapterMilkTemps);

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


        refracTemp_spin = findViewById(R.id.refrac_temp_spin);
        ArrayAdapter<String> dataAdapterRefracTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, refracTemps);
        dataAdapterRefracTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refracTemp_spin.setAdapter(dataAdapterRefracTemps);


        final RadioGroup temp_radio = findViewById(R.id.radiotemp_lay);
        temp_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if(checked.getText().toString().equalsIgnoreCase(getString(R.string.more6_str))){
                    milktemp_spin.setVisibility(View.VISIBLE);
                }else{
                    milktemp_spin.setVisibility(View.INVISIBLE);
                }
            }
        });

        final RadioGroup temp_refrac_radio = findViewById(R.id.refrac_radio_lay);
        temp_refrac_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if(checked.getText().toString().equalsIgnoreCase(getString(R.string.below95_str))){
                    refracTemp_spin.setVisibility(View.VISIBLE);
                }else{
                    refracTemp_spin.setVisibility(View.INVISIBLE);
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
                if((i == 0 || i == 1 || i == 2 ) && !leaveMilk.isChecked()) {
                    testLayout.setVisibility(View.VISIBLE);
                }
                else{
                    testLayout.setVisibility(View.INVISIBLE);
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

        alcohol_radio = findViewById(R.id.radioalcohol_lay);
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
        detail = findViewById(R.id.amount_detail_lay);
        taken_amount = findViewById(R.id.taken_amount_lay);
        taken_amount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if(checked.getText().toString().equalsIgnoreCase(getString(R.string.leave_milk_str))){
                    testLayout.setVisibility(View.INVISIBLE);
                    detail.setVisibility(View.VISIBLE);
                }else{
                    detail.setVisibility(View.INVISIBLE);
                    int pos = milktype.getSelectedItemPosition();
                    if(pos == 1 || pos == 0 || pos == 2){
                        testLayout.setVisibility(View.VISIBLE);
                    }
                }


            }
        });


        Button submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


    }

    private void makeSubmission() {


        MilkInf milk = new MilkInf();
        RadioButton alc = findViewById(R.id.alcohol_e_radio);
        if(alc.isChecked()){
            milk.setAlcohol(true);
            milk.setAlcoholtype(alcohol_type.getSelectedItem().toString());
        }
        TextView tv = findViewById(R.id.comment_tv);
        TextView tvL = findViewById(R.id.liter_input);
        milk.setComment(tv.getText().toString());
        milk.setAntibiotic_inf(antibiotic.isChecked());
        milk.setDuty(duty_id);
        milk.setFarm(farmid);

        if(!tvL.getText().toString().equals("")){
            milk.setLiter(Integer.parseInt(tvL.getText().toString()));
        }

        milk.setMilkId(null);
        milk.setMilktype(milktype.getSelectedItem().toString());
        RadioButton milktemp = findViewById(R.id.deg46_radio);
        if(milktemp.isChecked()){
            milk.setTemp("4-6 Derece");
        }else{
            milk.setTemp(milktemp_spin.getSelectedItem().toString());
        }
        RadioButton refractemp = findViewById(R.id.below95_radio);
        if(refractemp.isChecked()){
            milk.setR_temp(refracTemp_spin.getSelectedItem().toString());
        }else{
            milk.setR_temp("9.5'dan fazla");
        }





        QueryBuilder<TankInf> fortanks = ses.getTankInfDao().queryBuilder();
        List<TankInf> tanks = fortanks.where(TankInfDao.Properties.Truck.eq(truck_id)).list();
        for (TankInf tank : tanks){
            if (tank.getTankN() == 1){
                if(c1.isChecked()){
                    milk.setTank1(tank.getTankId());
                }
            }else if(tank.getTankN() == 2){
                if(c2.isChecked()){
                    milk.setTank2(tank.getTankId());
                }
            }else if(tank.getTankN() == 3){
                if(c3.isChecked()){
                    milk.setTank3(tank.getTankId());
                }
            }else if(tank.getTankN() == 4){
                if(c4.isChecked()){
                    milk.setTank4(tank.getTankId());
                }
            }else if(tank.getTankN() == 5){
                if(c5.isChecked()){
                    milk.setTank5(tank.getTankId());
                }
            }
        }



    }

    private void openDialog() {
        if(antibiotic.isChecked() && !leaveMilk.isChecked() ){
            Toast.makeText(FormScreen.this, "Antibiyotikli bir ürünü alamazsınız.", Toast.LENGTH_LONG).show();
        }else{
            final Dialog beforesub = new Dialog(FormScreen.this);
            beforesub.setContentView(R.layout.before_submission);
            beforesub.setTitle("Göndermeden önce..");
            beforesub.show();
            QueryBuilder<FarmInf> farm_q = ses.getFarmInfDao().queryBuilder();
            final List<FarmInf> taken_farm = farm_q.where(FarmInfDao.Properties.FarmID.eq(farmid)).list();
            final CheckBox isClean1 = beforesub.findViewById(R.id.isClean1_chk);
            final CheckBox isClean2 = beforesub.findViewById(R.id.isClean2_chk);
            final CheckBox isClean3 = beforesub.findViewById(R.id.isClean3_chk);
            final CheckBox isClean4 = beforesub.findViewById(R.id.isClean4_chk);
            Button last_sub = beforesub.findViewById(R.id.last_submission_button);
            last_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeSubmission();

                    if(taken_farm.size() > 0){
                        taken_farm.get(0).setIsEnvClean(isClean4.isChecked());
                        taken_farm.get(0).setIsPumpClean(isClean2.isChecked());
                        taken_farm.get(0).setIsWeighterClean(isClean3.isChecked());
                        taken_farm.get(0).setIsTankClean(isClean1.isChecked());
                    }
                    beforesub.hide();

                    QueryBuilder<DutyInf> dt = ses.getDutyInfDao().queryBuilder();
                    List<DutyInf> dutlist = dt.where(DutyInfDao.Properties.DutyId.eq(duty_id)).list();
                    if(dutlist.size() > 0){
                        dutlist.get(0).setDone(true);
                    }
                    Intent i = new Intent(FormScreen.this, MissionSelectScreen.class);
                    startActivity(i);
                    finish();
                }
            });

        }
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
