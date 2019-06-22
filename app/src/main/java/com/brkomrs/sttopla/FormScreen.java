package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.brkomrs.sttopla.database.*;
import com.brkomrs.sttopla.necessary.*;

import java.text.Normalizer;
import java.util.ArrayList;

public class FormScreen extends AppCompatActivity {
    private Spinner alcohol_type,milktype,milktemp_spin, refracTemp_spin, basket_spin;
    private LinearLayout testLayout;
    private DaoSession ses;
    private long farmid, userid, truck_id,duty_id;
    private int tank_filled = 0, tank_number = 0, liter_input = 0;
    private TextView tankInp,commentInp;
    private boolean leavingMilk = false, addMoreMilk = false, noTestRequired = false, test_antibiotic = false,detail_badmilk = false;
    private double test_Rtemperature, test_temperature;
    private String test_alcohol = "", milk_type_str = "",comment = "";
    private boolean[] clicked;
    private ArrayList<MilkInf> basket_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);

        //clicked flag for tests
        clicked = new boolean[4];
        clicked[0] = false;
        clicked[1] =false;
        clicked[2] = false;
        clicked[3] = false;


        //getting duty id from previous page
        String duty_id_temp_str = getIntent().getStringExtra(getString(R.string.duty_id_extra_str));
        if (duty_id_temp_str != null && !duty_id_temp_str.equals("")) {
            duty_id = Long.parseLong(duty_id_temp_str);
        }

        //session getter
        ses = ((dbHelper) getApplication()).getDaoSession();

        //widged declarations
        basket_spin = findViewById(R.id.basket_spin);
        testLayout = findViewById(R.id.tests_lay);
        milktemp_spin = findViewById(R.id.temp_spin);
        refracTemp_spin = findViewById(R.id.refrac_temp_spin);
        milktype = findViewById(R.id.milk_type_spin);
        alcohol_type = findViewById(R.id.alcohol_type_spin);
        tankInp = findViewById(R.id.tankLiter_input);
        commentInp = findViewById(R.id.comment_tv);

        //localWidgets
        final RadioGroup alcohol_radiogroup = findViewById(R.id.radioalcohol_lay);
        final RadioGroup detail_radiogroup = findViewById(R.id.amount_detail_lay);
        final RadioGroup taken_amount_radiogroup = findViewById(R.id.taken_amount_lay);
        final RadioGroup temp_radiogroup = findViewById(R.id.radiotemp_lay);
        final RadioGroup temp_refrac_radiogroup = findViewById(R.id.radiotempR_lay);
        final RadioGroup truck_radiogroup = findViewById(R.id.truck_chk_radio);
        final RadioGroup antibiotic_radiogroup = findViewById(R.id.antibiotic_radio_lay);

        //db info getters

        try{
            DutyInf duty_obj = helperFunctions.getDuty(ses, duty_id);
            if(duty_obj.getDone()){
                Toast.makeText(FormScreen.this, getString(R.string.already_done_str),Toast.LENGTH_SHORT).show();
                finish();
            }
            userid = duty_obj.getUser();
            farmid = duty_obj.getFarm_id();
            TruckInf truck_obj = helperFunctions.getTruck(ses, userid);
            tank_number = truck_obj.getN_tank();
            truck_id = truck_obj.getTruckId();
        }catch (Exception e){
            showErrorAndExit();
            Log.e("@@@@@@@@", "oncreate");
        }

        //tank selection radio buttons are arranged here
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


        //taken amoun radiogroup
        taken_amount_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ConstraintLayout lay = findViewById(R.id.truck_lay);
                if(i == R.id.leave_milk_radio){
                    lay.setVisibility(View.GONE);
                    testLayout.setVisibility(View.GONE);
                    findViewById(R.id.amount_tv).setVisibility(View.GONE);
                    tankInp.setText("");
                    tankInp.setVisibility(View.GONE);
                    detail_radiogroup.setVisibility(View.VISIBLE);
                    leavingMilk = true;
                    addMoreMilk = false;
                }else if(i == R.id.some_milk_radio){
                    testLayout.setVisibility(View.VISIBLE);
                    addMoreMilk = true;
                    leavingMilk = false;
                    lay.setVisibility(View.VISIBLE);
                    findViewById(R.id.amount_tv).setVisibility(View.VISIBLE);
                    tankInp.setVisibility(View.VISIBLE);
                    detail_radiogroup.setVisibility(View.GONE);
                }else{
                    testLayout.setVisibility(View.VISIBLE);
                    lay.setVisibility(View.VISIBLE);
                    findViewById(R.id.amount_tv).setVisibility(View.VISIBLE);
                    tankInp.setVisibility(View.VISIBLE);
                    detail_radiogroup.setVisibility(View.GONE);
                    addMoreMilk = false;
                    leavingMilk = false;
                }
            }
        });
        //detail radiogroup
        detail_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.bad_milk_radio){
                    detail_badmilk = true;
                }else{
                    detail_badmilk = false;
                }
            }
        });



        //arrangin tv on screen to write duty id
        TextView tw = findViewById(R.id.duty_id_tv);
        String text = getString(R.string.duty_id_str) + duty_id_temp_str;
        tw.setText(text);


        //temp spinner, if user chooses more than 6 degree this will show up
        ArrayList<String> milktemps = new ArrayList<>();
        for(double i = 6.0; i <= 10.0; i+= 0.5 ){
            milktemps.add(i + "");
        }
        ArrayAdapter<String> dataAdapterMilkTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, milktemps);
        dataAdapterMilkTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milktemp_spin.setAdapter(dataAdapterMilkTemps);
        //if special radio button enabled then above spinner will be visible
        temp_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                clicked[0] = true;
                if (i == R.id.more6_radio) {
                    test_temperature = Double.parseDouble(milktemp_spin.getSelectedItem().toString());
                    milktemp_spin.setVisibility(View.VISIBLE);
                } else {
                    milktemp_spin.setVisibility(View.INVISIBLE);
                    test_temperature = 5.0;
                }
            }
        });
        milktemp_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                test_temperature =  Double.parseDouble(milktemp_spin.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                test_temperature =  Double.parseDouble(milktemp_spin.getItemAtPosition(0).toString());
            }
        });

        //temp spinner, if user chooses more than 6 degree this will show up
        ArrayList<String> refracTemps = new ArrayList<>();
        for(double i = 9.0; i >= 5.0; i -=0.5 ){
            refracTemps.add(i + "");
        }
        ArrayAdapter<String> dataAdapterRefracTemps = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item, refracTemps);
        dataAdapterRefracTemps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refracTemp_spin.setAdapter(dataAdapterRefracTemps);

        //if special radio button enabled then above spinner will be visible
        temp_refrac_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                clicked[1] = true;
                if (i == R.id.below95_radio) {
                    test_Rtemperature = Double.parseDouble(refracTemp_spin.getSelectedItem().toString());
                    refracTemp_spin.setVisibility(View.VISIBLE);

                } else {
                    refracTemp_spin.setVisibility(View.INVISIBLE);
                    test_Rtemperature = 10.0;
                }
            }
        });
        refracTemp_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                test_Rtemperature = Double.parseDouble(refracTemp_spin.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                test_Rtemperature = Double.parseDouble(refracTemp_spin.getItemAtPosition(0).toString());
            }
        });

        //milk types spinner, if user chooses milk test inputs will be visible
        ArrayList<String> milktypes = new ArrayList<>();
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
                    milk_type_str = milktype.getItemAtPosition(i).toString();
                    noTestRequired = false;
                } else {
                    testLayout.setVisibility(View.GONE);
                    milk_type_str = milktype.getItemAtPosition(i).toString();
                    noTestRequired = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                testLayout.setVisibility(View.VISIBLE);
                milk_type_str = milktype.getItemAtPosition(0).toString();
                noTestRequired = false;
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
                clicked[2] = true;
                if (i == R.id.alcohol_e_radio) {
                    test_alcohol = alcohol_type.getSelectedItem().toString();
                    alcohol_type.setVisibility(View.VISIBLE);
                } else {
                    test_alcohol = "";
                    alcohol_type.setVisibility(View.INVISIBLE);
                }
            }
        });

        alcohol_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                test_alcohol = alcohol_type.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                test_alcohol = alcohol_type.getItemAtPosition(0).toString();
            }
        });



        antibiotic_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                clicked[3] = true;
                if(i == R.id.antibiotic_e_radio){
                    test_antibiotic = true;
                }else{
                    test_antibiotic = false;
                }
            }
        });


        //tankInp
        tankInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!tankInp.getText().toString().equalsIgnoreCase("")){
                    liter_input = Integer.parseInt(tankInp.getText().toString());
                }else{
                    liter_input = 0;
                }
            }
        });

        //commentInp
        commentInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                comment = commentInp.getText().toString();
            }
        });

        //saved milks spinner
        basket_list= new ArrayList<>();
        basket_list.add(new milk_spin());
        ArrayAdapter<MilkInf> dataAdapterBasket = new ArrayAdapter<>(FormScreen.this, android.R.layout.simple_spinner_item,basket_list);
        dataAdapterBasket.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        basket_spin.setAdapter(dataAdapterBasket);


        Button submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void makeSubmission() {
        try{
            MilkInf milk = new MilkInf();


            milk.setDuty(duty_id);
            milk.setFarm(farmid);
            milk.setComment(comment);
            milk.setMilktype(milk_type_str);

            if(noTestRequired || leavingMilk){
                milk.setAlcoholtype("");
                milk.setAlcohol(false);
                milk.setAntibiotic_inf(false);
                milk.setTemp(0);
                milk.setR_temp(0);
            }else{
                if (!test_alcohol.equalsIgnoreCase("")) {
                    milk.setAlcohol(true);

                }
                milk.setAlcoholtype(test_alcohol);
                milk.setAntibiotic_inf(test_antibiotic);
                milk.setTemp(test_temperature);
                milk.setR_temp(test_Rtemperature);
            }

            TankInf tank = helperFunctions.getTank(ses, truck_id, tank_filled);
            if(leavingMilk){
                milk.setTank_id(0);
                milk.setLeave_milk(true);
                milk.setTank_liter(0);
                milk.setTankN(0);
            }else{
                milk.setTank_id(tank.getTankId());
                milk.setTank_liter(liter_input);
                milk.setTankN(tank_filled);
            }

            basket_list.add(milk);
            ses.getMilkInfDao().insert(milk);
            updateTank();
            Toast.makeText(FormScreen.this, "Kaydedildi.!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            showErrorAndExit();
            Log.e("@@@@@@@@", "makesubmit");
        }
    }

    private void showErrorAndExit() {
        Toast.makeText(FormScreen.this, getString(R.string.error_file_str), Toast.LENGTH_LONG).show();
        finish();
    }


    private void openDialog() {

        if((liter_input == 0 && ( !leavingMilk && addMoreMilk))){
            Toast.makeText(FormScreen.this, "0 Litre Olamaz, Tekrar Deneyin.", Toast.LENGTH_LONG).show();
        }else if (test_antibiotic && !leavingMilk) {
            Toast.makeText(FormScreen.this, getString(R.string.antibiotic_err), Toast.LENGTH_LONG).show();
        } else if ((tankInp.getText().toString().equals("") && !leavingMilk) || (comment.equalsIgnoreCase("") && leavingMilk && !detail_badmilk) ||  (!leavingMilk && !clicked[0] && !clicked[1] && !clicked[2] && !clicked[3])) {
            Toast.makeText(FormScreen.this, getString(R.string.empty_area_err_str), Toast.LENGTH_LONG).show();
        } else if (!leavingMilk && !checkTankLimits()) {
            Toast.makeText(FormScreen.this, getString(R.string.tank_limit_exceeded_str), Toast.LENGTH_LONG).show();
        } else if(!leavingMilk && !checkMilkTypes()){
            Toast.makeText(FormScreen.this, getString(R.string.milk_not_comp_str), Toast.LENGTH_LONG).show();
        }else if(addMoreMilk){
            makeSubmission();
            final Dialog addMoreMilk = new Dialog(FormScreen.this);
            addMoreMilk.setContentView(R.layout.add_milk);
            addMoreMilk.setCancelable(true);
            addMoreMilk.setCanceledOnTouchOutside(false);
            addMoreMilk.setTitle("Başarılı, sütün bir kısmı kaydedildi.");
            addMoreMilk.findViewById(R.id.understood_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addMoreMilk.cancel();
                }
            });
            addMoreMilk.show();

            //checking "tüm sütü aldım" radio button, to make things easy
            RadioGroup taken_amount_radiogroup = findViewById(R.id.taken_amount_lay);
            taken_amount_radiogroup.check(R.id.all_milk_radio);
        }else {
            final Dialog beforesub = new Dialog(FormScreen.this);
            beforesub.setContentView(R.layout.before_submission);
            beforesub.setTitle("Göndermeden önce..");
            beforesub.setCancelable(true);
            beforesub.setCanceledOnTouchOutside(false);
            final FarmInf farm_taken_milk = helperFunctions.getFarm(ses, farmid);
            final CheckBox isClean1 = beforesub.findViewById(R.id.isClean1_chk);
            final CheckBox isClean2 = beforesub.findViewById(R.id.isClean2_chk);
            final CheckBox isClean3 = beforesub.findViewById(R.id.isClean3_chk);
            final CheckBox isClean4 = beforesub.findViewById(R.id.isClean4_chk);
            Button last_sub = beforesub.findViewById(R.id.last_submission_button);
            last_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        FarmInf farm =  new FarmInf();
                        farm.setFarmID(farm_taken_milk.getFarmID());
                        farm.setFarmName(farm_taken_milk.getFarmName());
                        farm.setIsEnvClean(isClean4.isChecked());
                        farm.setIsPumpClean(isClean2.isChecked());
                        farm.setIsWeighterClean(isClean3.isChecked());
                        farm.setIsTankClean(isClean1.isChecked());
                        ses.update(farm);
                        if(liter_input > 0){
                            Toast.makeText(FormScreen.this, "Eklendi ve görev bitirildi.", Toast.LENGTH_LONG).show();
                            makeSubmission();
                        }else{
                            Toast.makeText(FormScreen.this, "Görev bitirildi.", Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        showErrorAndExit();
                        Log.e("@@@@@@@@", "isClean");
                    }
                    beforesub.cancel();

                    updateDutyAsDone();
                    finish();
                }
            });
            beforesub.show();

        }
    }

    private void updateDutyAsDone() {
        //updating duty as done
        DutyInf duty = new DutyInf();
        duty.setDutyId(duty_id);
        duty.setDone(true);
        duty.setFarm_id(farmid);
        duty.setUser(userid);
        ses.update(duty);
    }


    //checker method for milktypes
    private boolean checkMilkTypes() {
        try{
            TankInf tank= helperFunctions.getTank(ses, truck_id, tank_filled);

            MilkInf milk = helperFunctions.getMilk(ses, tank.getTankId());

            if(milk != null) {
                if((milk.getAlcoholtype().equalsIgnoreCase(test_alcohol) || (!milk.getAlcohol() && test_alcohol.equalsIgnoreCase(""))) && milk.getMilktype().equalsIgnoreCase(milk_type_str) &&
                        (milk.getR_temp() == test_Rtemperature) && (milk.getTemp() == test_temperature)){
                    return true;
                }else return false;
            }else{
                return true;
            }

        }catch (Exception e){
            showErrorAndExit();
            Log.e("@@@@@@@@", "milktype");
         }
        return false;
    }

    //a checker method for tanks
    private boolean checkTankLimits() {
        if(leavingMilk) return true;
        try {
            TankInf each = helperFunctions.getTank(ses, truck_id, tank_filled);
            if (liter_input >= 0 && each.getLimit() >= each.getFullness() + liter_input) {
                return true;
            } else return false;
        }catch (Exception e){
            showErrorAndExit();
            Log.e("@@@@@@@@", "tanklimit");
        }
        return false;
    }


    private void updateTank(){
        try{
            TankInf each = helperFunctions.getTank(ses, truck_id, tank_filled);
            TankInf tank = new TankInf();
            tank.setTankId(each.getTankId());
            tank.setTruck(truck_id);
            tank.setLimit(each.getLimit());
            tank.setTankN(each.getTankN());
            if (liter_input >=0 && each.getLimit() >= each.getFullness() + liter_input) {
                tank.setFullness(each.getFullness() + liter_input);
            }
            ses.update(tank);
        }catch (Exception e){
            showErrorAndExit();
            Log.e("@@@@@@@@", "updatetank");
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
