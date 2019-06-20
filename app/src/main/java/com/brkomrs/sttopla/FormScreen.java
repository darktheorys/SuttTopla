package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.brkomrs.sttopla.database.DaoSession;
import com.brkomrs.sttopla.database.DutyInf;
import com.brkomrs.sttopla.database.FarmInf;
import com.brkomrs.sttopla.database.MilkInf;
import com.brkomrs.sttopla.database.TankInf;
import com.brkomrs.sttopla.database.TruckInf;
import com.brkomrs.sttopla.database.UserInf;
import com.brkomrs.sttopla.necessary.helperFunctions;
import java.util.ArrayList;

public class FormScreen extends AppCompatActivity {
    private Spinner alcohol_type;
    private Spinner milktype;
    private RadioButton c1, c2, c3, c4, c5;
    private Spinner milktemp_spin, refracTemp_spin;
    private long duty_id;
    private RadioButton antibiotic_e_radio;
    private RadioButton leaveMilk_radio;
    private RadioGroup detail_radiogroup;
    //private RadioGroup tempR_radiogroup;
    private LinearLayout testLayout;
    private DaoSession ses;
    private long farmid, userid, truck_id;
    private int tank_filled, tank_number;
    private TextView tankInp;
    private boolean leavingMilk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);

        //widged declarations
        c1 = findViewById(R.id.tank1_radio);
        c2 = findViewById(R.id.tank2_radio);
        c3 = findViewById(R.id.tank3_radio);
        c4 = findViewById(R.id.tank4_radio);
        c5 = findViewById(R.id.tank5_radio);

        //RadioButton takeAllMilk_radio = findViewById(R.id.all_milk_radio);
        //RadioButton addMoreMilk_radio = findViewById(R.id.some_milk_radio);
        testLayout = findViewById(R.id.tests_lay);
        antibiotic_e_radio = findViewById(R.id.antibiotic_e_radio);
        leaveMilk_radio = findViewById(R.id.leave_milk_radio);
        milktemp_spin = findViewById(R.id.temp_spin);
        refracTemp_spin = findViewById(R.id.refrac_temp_spin);
        milktype = findViewById(R.id.milk_type_spin);
        alcohol_type = findViewById(R.id.alcohol_type_spin);
        RadioGroup alcohol_radiogroup = findViewById(R.id.radioalcohol_lay);
        detail_radiogroup = findViewById(R.id.amount_detail_lay);
        RadioGroup taken_amount_radiogroup = findViewById(R.id.taken_amount_lay);
        RadioGroup temp_radiogroup = findViewById(R.id.radiotemp_lay);
        //RadioGroup antibiotic_radiogroup = findViewById(R.id.antibiotic_radio_lay);
        tankInp = findViewById(R.id.tankLiter_input);
        RadioGroup temp_refrac_radiogroup = findViewById(R.id.radiotempR_lay);
        RadioGroup truck_radiogroup = findViewById(R.id.truck_chk_radio);

        //getting duty id from previous page
        String duty_id_temp_str = getIntent().getStringExtra(getString(R.string.duty_id_extra_str));
        if (duty_id_temp_str != null && !duty_id_temp_str.equals("")) {
            duty_id = Long.parseLong(duty_id_temp_str);
        }

        //session getter
        ses = ((dbHelper) getApplication()).getDaoSession();

        DutyInf duty_obj = helperFunctions.getDuty(ses, duty_id);
        UserInf user_obj = helperFunctions.getUser(ses, duty_obj.getUser());
        TruckInf truck_obj = helperFunctions.getTruck(ses, user_obj.getUserId());

        userid = duty_obj.getUser();
        farmid = duty_obj.getFarm_id();
        tank_number = truck_obj.getN_tank();
        truck_id = truck_obj.getTruckId();

        tankInp.setVisibility(View.VISIBLE);
        truck_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.tank1_radio){
                    tankInp.setHint("Tank 1");
                    tank_filled = 1;
                }else if(i == R.id.tank2_radio){
                    tankInp.setHint("Tank 2");
                    tank_filled = 2;
                }else if(i == R.id.tank3_radio){
                    tankInp.setHint("Tank 3");
                    tank_filled = 3;
                }else if(i == R.id.tank4_radio){
                    tankInp.setHint("Tank 4");
                    tank_filled = 4;
                }else if(i == R.id.tank5_radio){
                    tankInp.setHint("Tank 5");
                    tank_filled = 5;
                }
                if(tank_number >= 1){
                    findViewById(R.id.tank1_radio).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tank1_radio).setVisibility(View.INVISIBLE);
                }
                if(tank_number >= 2){
                    findViewById(R.id.tank2_radio).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tank2_radio).setVisibility(View.INVISIBLE);
                }
                if(tank_number >= 3){
                    findViewById(R.id.tank3_radio).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tank3_radio).setVisibility(View.INVISIBLE);
                }
                if(tank_number >= 4){
                    findViewById(R.id.tank4_radio).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tank4_radio).setVisibility(View.INVISIBLE);
                }
                if(tank_number >= 5){
                    findViewById(R.id.tank5_radio).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tank5_radio).setVisibility(View.INVISIBLE);
                }
            }
        });
        //setting tank1 selected to make above work
        truck_radiogroup.check(R.id.tank1_radio);


        taken_amount_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.leave_milk_radio){
                    ConstraintLayout lay = findViewById(R.id.truck_lay);
                    findViewById(R.id.amount_tv).setVisibility(View.GONE);
                    tankInp.setVisibility(View.GONE);
                    tankInp.setText("flag");
                    lay.setVisibility(View.GONE);
                    detail_radiogroup.setVisibility(View.VISIBLE);
                    leavingMilk = true;
                }else{
                    ConstraintLayout lay = findViewById(R.id.truck_lay);
                    lay.setVisibility(View.VISIBLE);
                    findViewById(R.id.amount_tv).setVisibility(View.VISIBLE);
                    tankInp.setVisibility(View.VISIBLE);
                    tankInp.setText("");
                    detail_radiogroup.setVisibility(View.GONE);
                }
            }
        });


        //arrangin tw on screen to write duty id
        TextView tw = findViewById(R.id.duty_id_tv);
        String text = getString(R.string.duty_id_str) + duty_id_temp_str;
        tw.setText(text);


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

        ArrayAdapter<String> dataAdapterMilkTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, milktemps);
        dataAdapterMilkTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milktemp_spin.setAdapter(dataAdapterMilkTemps);


        //if special radio button enabled then above spinner will be visible
        temp_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                if (checked.getText().toString().equalsIgnoreCase(getString(R.string.more6_str))) {
                    milktemp_spin.setVisibility(View.VISIBLE);
                } else {
                    milktemp_spin.setVisibility(View.INVISIBLE);
                }
            }
        });

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
        ArrayAdapter<String> dataAdapterRefracTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, refracTemps);
        dataAdapterRefracTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refracTemp_spin.setAdapter(dataAdapterRefracTemps);


        //if special radio button enabled then above spinner will be visible
        temp_refrac_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.below95_radio) {
                    refracTemp_spin.setVisibility(View.VISIBLE);
                } else {
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

        ArrayAdapter<String> dataAdapterMilkTypes = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, milktypes);
        dataAdapterMilkTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milktype.setAdapter(dataAdapterMilkTypes);

        //milk some special milks selected then, menu will be invisible, and leave milk if checked will be invisible
        milktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if ((i == 0 || i == 1 || i == 2)) {
                    testLayout.setVisibility(View.VISIBLE);
                } else {
                    testLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                testLayout.setVisibility(View.GONE);
            }
        });


        //if alcohol exists, app will make visible a spinner to choose color
        final ArrayList<String> colors = new ArrayList<>();
        colors.add("Mavi");
        colors.add("Yeşil");
        colors.add("Mavi - Yeşil");
        colors.add("Sarı");

        ArrayAdapter<String> dataAdapterAlcoholTypes = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, colors);
        dataAdapterAlcoholTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alcohol_type.setAdapter(dataAdapterAlcoholTypes);

        //milk types spinner, if user chooses milk test inputs will be visible
        alcohol_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.alcohol_e_radio) {
                    alcohol_type.setVisibility(View.VISIBLE);
                } else {
                    alcohol_type.setVisibility(View.INVISIBLE);
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
        if (alc.isChecked()) {
            milk.setAlcohol(true);
            milk.setAlcoholtype(alcohol_type.getSelectedItem().toString());
        }
        TextView tv = findViewById(R.id.comment_tv);

        milk.setComment(tv.getText().toString());
        milk.setAntibiotic_inf(antibiotic_e_radio.isChecked());
        milk.setDuty(duty_id);
        milk.setFarm(farmid);
        milk.setMilktype(milktype.getSelectedItem().toString());
        RadioButton milktemp = findViewById(R.id.deg46_radio);
        if (milktemp.isChecked()) {
            milk.setTemp("4-6");
        } else {
            milk.setTemp(milktemp_spin.getSelectedItem().toString());
        }
        RadioButton refractemp = findViewById(R.id.below95_radio);
        if (refractemp.isChecked()) {
            milk.setR_temp(refracTemp_spin.getSelectedItem().toString());
        } else {
            milk.setR_temp(">9.5");
        }


        TankInf tank = helperFunctions.getTank(ses, truck_id, tank_filled);
        if(leavingMilk){
            milk.setTank_id(0);
            milk.setLeave_milk(true);
        }else{
            milk.setTank_id(tank.getTankId());
        }

        milk.setTankN(tank_filled);

        int total = 0;
        if (tankInp.getText().toString().equalsIgnoreCase("flag")){
            total = -1;
        }else{
            total = Integer.parseInt("0" + tankInp.getText().toString());
            //getting tank inputs
            milk.setTank_liter(total);
        }





        if(total > 0){
            ses.getMilkInfDao().insert(milk);
            updateTank();
            Toast.makeText(FormScreen.this, "Kaydedildi.!", Toast.LENGTH_SHORT).show();
        }else if(leavingMilk){
            ses.getMilkInfDao().insert(milk);
            Toast.makeText(FormScreen.this,"Süt göndermediniz. Kaydedildi.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(FormScreen.this,"Süt eklenmedi.",Toast.LENGTH_SHORT).show();
        }



    }


    private void openDialog() {
        RadioButton other_comm = findViewById(R.id.other_radio);
        RadioButton bad_milk = findViewById(R.id.bad_milk_radio);
        RadioButton someMilkRem = findViewById(R.id.some_milk_radio);
        TextView comment = findViewById(R.id.comment_tv);
        if (antibiotic_e_radio.isChecked() && !leavingMilk) {
            Toast.makeText(FormScreen.this, getString(R.string.antibiotic_err), Toast.LENGTH_LONG).show();
        } else if ( ( c1.isChecked() || c2.isChecked() || c3.isChecked() || c4.isChecked()|| c5.isChecked()) && tankInp.getText().toString().equals("") && !leavingMilk) {
            Toast.makeText(FormScreen.this, getString(R.string.empty_area_err_str), Toast.LENGTH_LONG).show();
        } else if ((!(c1.isChecked() || c2.isChecked() || c3.isChecked() || c4.isChecked() || c5.isChecked()) && !leavingMilk) || (leavingMilk && (!other_comm.isChecked() && !bad_milk.isChecked()))
                || (other_comm.isChecked() && comment.getText().toString().equals(""))) {
            Toast.makeText(FormScreen.this, getString(R.string.empty_area_err_str), Toast.LENGTH_LONG).show();
        } else if (!bad_milk.isChecked() && !checkTankLimits() ) {
            Toast.makeText(FormScreen.this, getString(R.string.tank_limit_exceeded_str), Toast.LENGTH_LONG).show();
        } else if(!bad_milk.isChecked() && !checkMilkTypes()){
            Toast.makeText(FormScreen.this, getString(R.string.milk_not_comp_str), Toast.LENGTH_LONG).show();
        }else if(someMilkRem.isChecked()){
            makeSubmission();
            Toast.makeText(FormScreen.this, getString(R.string.some_milk_sent_str), Toast.LENGTH_SHORT).show();
        }else {
            final Dialog beforesub = new Dialog(FormScreen.this);
            beforesub.setContentView(R.layout.before_submission);
            beforesub.setTitle("Göndermeden önce..");
            beforesub.show();
            final FarmInf farm_taken_milk = helperFunctions.getFarm(ses, farmid);
            final CheckBox isClean1 = beforesub.findViewById(R.id.isClean1_chk);
            final CheckBox isClean2 = beforesub.findViewById(R.id.isClean2_chk);
            final CheckBox isClean3 = beforesub.findViewById(R.id.isClean3_chk);
            final CheckBox isClean4 = beforesub.findViewById(R.id.isClean4_chk);
            Button last_sub = beforesub.findViewById(R.id.last_submission_button);
            last_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeSubmission();
                    FarmInf farm =  new FarmInf();
                    farm.setFarmID(farm_taken_milk.getFarmID());
                    farm.setFarmName(farm_taken_milk.getFarmName());
                    farm.setIsEnvClean(isClean4.isChecked());
                    farm.setIsPumpClean(isClean2.isChecked());
                    farm.setIsWeighterClean(isClean3.isChecked());
                    farm.setIsTankClean(isClean1.isChecked());
                    ses.update(farm);

                    beforesub.cancel();

                    //updating duty as done
                    DutyInf duty = new DutyInf();
                    duty.setDutyId(duty_id);
                    duty.setDone(true);
                    duty.setFarm_id(farmid);
                    duty.setUser(userid);
                    ses.update(duty);
                    finish();
                }
            });

        }
    }


    //checker method for milktypes
    private boolean checkMilkTypes() {
        int tankN = c1.isChecked()?1:c2.isChecked()?2:c3.isChecked()?3:c4.isChecked()?4:c5.isChecked()?5:0;
        TankInf tank= helperFunctions.getTank(ses, truck_id, tankN);
        RadioButton alcohol_e = findViewById(R.id.alcohol_e_radio);
        RadioButton above95 = findViewById(R.id.above95_radio);
        RadioButton bw46 = findViewById(R.id.deg46_radio);
        MilkInf milk = helperFunctions.getMilk(ses, tank.getTankId());
        if (milk != null) {
            if((milk.getAlcoholtype().equalsIgnoreCase(alcohol_type.getSelectedItem().toString()) || (!milk.getAlcohol() && !alcohol_e.isChecked())) && milk.getMilktype().equalsIgnoreCase(milktype.getSelectedItem().toString()) &&
                    (milk.getR_temp().equalsIgnoreCase(refracTemp_spin.getSelectedItem().toString()) || (milk.getR_temp().equalsIgnoreCase(">9.5") && above95.isChecked())) &&
                    (milk.getTemp().equalsIgnoreCase(milktemp_spin.getSelectedItem().toString()) || (milk.getTemp().equalsIgnoreCase("4-6") && bw46.isChecked()))){
                return true;
            }else return false;

            }else return true;
    }


    //a checker method for tanks
    private boolean checkTankLimits() {
        if(leavingMilk) return true;

        TankInf each = helperFunctions.getTank(ses, truck_id, tank_filled);
        int total = Integer.parseInt("0" + tankInp.getText().toString());
        if (total>=0 && each.getLimit() >= each.getFullness() + total) {
                return true;
        }else return false;
    }


    private void updateTank(){
        int tankN = c1.isChecked()?1:c2.isChecked()?2:c3.isChecked()?3:c4.isChecked()?4:c5.isChecked()?5:0;
        TankInf each = helperFunctions.getTank(ses, truck_id, tankN);
        TankInf tank = new TankInf();
        tank.setTankId(each.getTankId());
        tank.setTruck(truck_id);
        tank.setLimit(each.getLimit());
        tank.setTankN(each.getTankN());
        int total = Integer.parseInt("0" + tankInp.getText().toString());
        if (total>=0 && each.getLimit() >= each.getFullness() + total) {
            tank.setFullness(each.getFullness() +total);
        }
        ses.update(tank);
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
