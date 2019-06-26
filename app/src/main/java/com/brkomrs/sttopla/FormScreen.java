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
    private long farmid, truck_id,duty_id;
    private int tank_filled = 0, tank_number = 0, liter_input = 0;
    private TextView tankInp,commentInp;
    private boolean leavingMilk = false, addMoreMilk = false, noTestRequired = false, test_antibiotic = false,detail_badmilk = false;
    private double test_Rtemperature, test_temperature;
    private String test_alcohol = "", milk_type_str = "",comment = "";
    private boolean[] clicked;
    private ArrayList<MilkInf> basket_list;
    private boolean just_poll = false;
    private TruckInf truck_obj;
    private  DutyInf duty_obj;
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
            duty_id = Integer.parseInt(duty_id_temp_str);
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
            duty_obj = helperFunctions.getDuty(ses, duty_id);
            if(duty_obj.getDone()){
                Toast.makeText(FormScreen.this, getString(R.string.already_done_str),Toast.LENGTH_SHORT).show();
                finish();
            }
            farmid = duty_obj.getFarmId();
            truck_obj = duty_obj.getTruck();
            tank_number = truck_obj.getTankNumber();
            truck_id = truck_obj.getId();

        }catch (Exception e){
            showErrorAndExit();
            Log.e("@@@@@@@@", "oncreate");
        }

        //tank selection radio buttons are arranged here
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
                int r = milktype.getSelectedItemPosition();
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

                    if(r == 0 || r == 1 || r == 2){
                        testLayout.setVisibility(View.VISIBLE);
                    }

                    addMoreMilk = true;
                    leavingMilk = false;
                    lay.setVisibility(View.VISIBLE);
                    findViewById(R.id.amount_tv).setVisibility(View.VISIBLE);
                    tankInp.setVisibility(View.VISIBLE);
                    detail_radiogroup.setVisibility(View.GONE);
                }else{
                    if(r == 0 || r == 1 || r == 2){
                        testLayout.setVisibility(View.VISIBLE);
                    }
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
        milktypes.add("Inek");
        milktypes.add("Keci");
        milktypes.add("Koyun");
        milktypes.add("Pastorize Sut");
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
                if (i == 0 || i == 1 || i == 2){
                    if (!leavingMilk ){
                        testLayout.setVisibility(View.VISIBLE);
                    }
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

    private void makeSubmission(int a, int b, int c, int d) {
        try{
            MilkInf milk = new MilkInf();

            milk.setComment(comment);
            milk.setMilkType(milk_type_str);
            milk.setSync(false);
            milk.setIsEnvClean(a);
            milk.setIsPumpClean(b);
            milk.setIsTankClean(c);
            milk.setIsWeighterClean(d);

            if(noTestRequired || leavingMilk){
                milk.setAlcoholType("");
                milk.setAlcoholInf(false);
                milk.setAntibioticInf(false);
                milk.setTemp(0);
                milk.setRTemp(0);
            }else{
                if (!test_alcohol.equalsIgnoreCase("")) {
                    milk.setAlcoholInf(true);
                }else milk.setAlcoholInf(false);
                milk.setAlcoholType(test_alcohol);
                milk.setAntibioticInf(test_antibiotic);
                milk.setTemp(test_temperature);
                milk.setRTemp(test_Rtemperature);
            }

            TankInf tank = truck_obj.getTanks().get(tank_filled-1);
            if(leavingMilk){
                milk.setTankId(0);
                milk.setLeaveMilk(true);
                milk.setLiter(0);
                milk.setTankFilled(0);
            }else{
                milk.setTankId(tank.getId());
                milk.setLiter(liter_input);
                milk.setLeaveMilk(false);
                milk.setTankFilled(tank_filled);
            }

            basket_list.add(milk);
            ses.getMilkInfDao().insert(milk);
            updateTank();
            Toast.makeText(FormScreen.this, "Kaydedildi, tank güncellendi!", Toast.LENGTH_SHORT).show();
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

        if((liter_input == 0 && !leavingMilk)){       //one that take all the milk can pass with 0 liter
            Toast.makeText(FormScreen.this, "0 Litre Olamaz, Tekrar Deneyin.", Toast.LENGTH_LONG).show();
        }else if (test_antibiotic && !leavingMilk) {
            Toast.makeText(FormScreen.this, getString(R.string.antibiotic_err), Toast.LENGTH_LONG).show();
        } else if ((comment.equalsIgnoreCase("") && leavingMilk && !detail_badmilk) ||  (!leavingMilk && (!clicked[0] || !clicked[1] || !clicked[2] || !clicked[3]))) {
            Toast.makeText(FormScreen.this, getString(R.string.empty_area_err_str), Toast.LENGTH_LONG).show();
        } else if (!leavingMilk && !checkTankLimits()) {
            Toast.makeText(FormScreen.this, getString(R.string.tank_limit_exceeded_str), Toast.LENGTH_LONG).show();
        } else if(!leavingMilk && !checkMilkTypes()){
            Toast.makeText(FormScreen.this, getString(R.string.milk_not_comp_str), Toast.LENGTH_LONG).show();
        }else if(addMoreMilk){
            makeSubmission(2,2,2,2);
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
            pollAndFinish();

        }
    }

    private void pollAndFinish() {
        final Dialog beforesub = new Dialog(FormScreen.this);
        beforesub.setContentView(R.layout.before_submission);
        beforesub.setTitle("Çiftlik oylaması");
        beforesub.setCancelable(true);
        beforesub.setCanceledOnTouchOutside(false);
        final CheckBox isClean1 = beforesub.findViewById(R.id.isClean1_chk);
        final CheckBox isClean2 = beforesub.findViewById(R.id.isClean2_chk);
        final CheckBox isClean3 = beforesub.findViewById(R.id.isClean3_chk);
        final CheckBox isClean4 = beforesub.findViewById(R.id.isClean4_chk);
        Button last_sub = beforesub.findViewById(R.id.last_submission_button);
        last_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(!just_poll){
                        Toast.makeText(FormScreen.this, "Eklendi ve görev bitirildi.", Toast.LENGTH_LONG).show();
                        makeSubmission(isClean1.isChecked()?1:0, isClean2.isChecked()?1:0, isClean3.isChecked()?1:0, isClean4.isChecked()?1:0);
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

    private void updateDutyAsDone() {
        //updating duty as done
        DutyInf duty = new DutyInf();
        duty.setId(duty_id);
        duty.setDone(true);
        duty.setTruckId(truck_id);
        duty.setFarmId(farmid);
        ses.update(duty);
    }


    //checker method for milktypes
    private boolean checkMilkTypes() {
        try{
            truck_obj.resetTanks();
            TankInf tank= truck_obj.getTanks().get(tank_filled-1);
            tank.resetMilks();
            if(tank.getMilks().size() > 0 && !(liter_input == 0 && !addMoreMilk && !leavingMilk)){
                MilkInf milk = tank.getMilks().get(0);
                if((milk.getAlcoholType().equalsIgnoreCase(test_alcohol) || (!milk.getAlcoholInf() && test_alcohol.equalsIgnoreCase(""))) && milk.getMilkType().equalsIgnoreCase(milk_type_str) &&
                        (milk.getRTemp() == test_Rtemperature) && (milk.getTemp() == test_temperature)){
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
            truck_obj.resetTanks();
            TankInf each = truck_obj.getTanks().get(tank_filled-1);
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
            truck_obj.resetTanks();
            TankInf each = truck_obj.getTanks().get(tank_filled-1);
            TankInf tank = new TankInf();
            tank.setId(each.getId());
            tank.setTruckId(truck_id);
            tank.setLimit(each.getLimit());
            tank.setNTank(each.getNTank());
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
        if(basket_list.size() > 1){
            if(temp == 0){
                temp--;
                just_poll = true;
                pollAndFinish();
            }else{
                Toast.makeText(FormScreen.this, "Başka göndermemek için bir daha basın.", Toast.LENGTH_LONG).show();
                temp--;
                //if in 4 seconds, user cannot press second time, it resets counter to prevent misdoings
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(temp == 0){
                            just_poll = false;
                        }
                        temp++;
                    }
                }, 2000);
            }
        }else{
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
                }, 2000);
            }
        }




    }

}
