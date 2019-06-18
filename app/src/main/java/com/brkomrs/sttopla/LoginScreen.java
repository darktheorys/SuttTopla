package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.EditText;


import com.brkomrs.sttopla.database.DaoMaster;
import com.brkomrs.sttopla.database.DaoSession;
import com.brkomrs.sttopla.database.DbOpenHelper;
import com.brkomrs.sttopla.database.DutyInf;
import com.brkomrs.sttopla.database.DutyInfDao;
import com.brkomrs.sttopla.database.FarmInf;
import com.brkomrs.sttopla.database.FarmInfDao;
import com.brkomrs.sttopla.database.MilkInfDao;
import com.brkomrs.sttopla.database.TankInf;
import com.brkomrs.sttopla.database.TankInfDao;
import com.brkomrs.sttopla.database.TruckInf;
import com.brkomrs.sttopla.database.UserInf;
import com.brkomrs.sttopla.database.UserInfDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class LoginScreen extends AppCompatActivity {
    private String id_str;
    private boolean autolog = false;
    public File configfile,db;
    private CheckBox remember_chk,autolog_chk;
    private Button submit_btn;
    private EditText id_inp;
    private SwipeRefreshLayout swipe;
    private DaoSession daoSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        swipe = findViewById(R.id.swipe);
        submit_btn = findViewById(R.id.submit_id);
        remember_chk = findViewById(R.id.remember_chk);
        autolog_chk = findViewById(R.id.autologin_chk);
        submit_btn.setEnabled(false);
        //construction of id.txt file, to save
        //path of current installation
        File path = LoginScreen.this.getFilesDir();
        configfile = new File(path, getString(R.string.configfilename_str));

        //creation of helper db file, it is for understanding of existent database
        db = new File(path,"db.txt");

        //connection of database session
        daoSession = ((dbHelper) getApplication()).getDaoSession();

        //if db.txt is not existing, then we create one, usually after clean installation
        if(!db.exists()){
            try {
                if(db.createNewFile()) writeToFile(db,"db_created", false);

            } catch (IOException e) {
                e.printStackTrace();
            }
            // setting database from scratch after installation

            //adding users
            addUser("burak","ömür", "email1", "phone1");
            addUser("mazlum","yıldırım", "email2", "phone2");
            addUser("ramazan","yurt", "email3", "phone3");
            addUser("doğukan","yıldırım", "email4", "phone4");
            addUser("temp","user","sdf","fsld");

            //adding trucks
            addTruck(4,"16abc32",1);
            addTruck(5,"34asf78",2);
            addTruck(4,"26vgd77",3);
            addTruck(5,"67asd54",4);

            //adding farms
            addFarm("Alibaba");
            addFarm("Sütaş Ana Çiftlik");
            addFarm("Samsun Merkez");
            addFarm("Karacabey");

            //addding tanks
            addTank(200,1, 1);
            addTank(200,2, 1);
            addTank(200,3, 1);
            addTank(200,4, 1);

            addTank(200,1, 2);
            addTank(200,2, 2);
            addTank(200,3, 2);
            addTank(200,4, 2);
            addTank(200,5, 2);

            addTank(200,1, 3);
            addTank(200,2, 3);
            addTank(200,3, 3);
            addTank(200,4, 3);

            addTank(200,1, 4);
            addTank(200,2, 4);
            addTank(200,3, 4);
            addTank(200,4, 4);
            addTank(200,5, 4);


            //duty adding
            addDuty(1,1);
            addDuty(1,2);
            addDuty(2,1);
            addDuty(2,2);
            addDuty(2,3);
            addDuty(3,1);
            addDuty(3,2);
            addDuty(3,3);
            addDuty(1,3);
            addDuty(4,4);
            addDuty(4,2);



            // end of database
            Toast.makeText(LoginScreen.this,"DB Created", Toast.LENGTH_LONG).show();

        }


        //getting saved id from file
        id_inp = findViewById(R.id.id_input);
        String[] fromFile = readNLineFromFile(configfile, 3);
        //if saved text is not "", then we must have saved an id.
        if(!fromFile[0].equalsIgnoreCase("")){
            remember_chk.setChecked(true);
            id_inp.setText(fromFile[0]);
            //if autolog enabled, it checks
            if(fromFile[1].equalsIgnoreCase("true")){
                autolog_chk.setChecked(true);
            }else{
                autolog_chk.setChecked(false);
            }
        }else{
            remember_chk.setChecked(false);
        }

            //at the beginning of app
            //it automatically checks for connection, and moves forward
            moveForward(haveConnection(LoginScreen.this));
            //Swipe refresh initialization, and its delay
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moveForward(haveConnection(LoginScreen.this));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 4000);
            }
        });
        }

    /**
     *  Connection checker
     * @return Return if android can access internet or not
     * @param con context of caller
     */
    public static boolean haveConnection(Context con) {

        boolean wifi = false;
        boolean mobile = false;
        ConnectivityManager cn = (ConnectivityManager) con.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] ni_arr = null;
        try {
            ni_arr = cn.getAllNetworkInfo();
        } catch (Exception e) {
            Toast.makeText(con, con.getString(R.string.connection_err_str), Toast.LENGTH_SHORT).show();
        }
        for (NetworkInfo inf : ni_arr) {
            if (inf.getTypeName().equalsIgnoreCase("WIFI")) {
                if (inf.isConnected()) wifi = true;
            } else if (inf.getTypeName().equalsIgnoreCase("Mobile")) {
                if (inf.isConnected()) mobile = true;
            }
        }

        return wifi || mobile;
    }


    /**
     * takes a boolean and if true it makes enable submit button
     * it also checks buttons and input box and writes it to out configfile, in every login
     * also autologs if checkbox is checked
     * @param conn boolean to know if connection exists
     */
    private void moveForward(boolean conn) {
             if (!conn) {
                Toast.makeText(LoginScreen.this, getString(R.string.connection_err_str), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginScreen.this, getString(R.string.connected_str), Toast.LENGTH_SHORT).show();
                submit_btn.setEnabled(true);

                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // after clicking submit button, the followings happen
                        //if valid id is entered, it goes to mission page
                        //if not valit it shows an error and waits for another id
                        if(getValidity()){
                            Toast.makeText(LoginScreen.this, getString(R.string.log_making_str) ,Toast.LENGTH_LONG).show();
                            //if remember checkbox is checked, then we save id in the textEdit to the file.
                            // if not checked then, it saved empty string to file, replaces previous savings
                            if (remember_chk.isChecked()) {
                                id_str = id_inp.getText().toString();
                                writeToFile(configfile,id_str,false);
                            }else{
                                writeToFile(configfile,"",false);
                            }

                            //if autolog checkbox is checked, then it writes true to the second line of file
                            // else it writes false
                            autolog = autolog_chk.isChecked();
                            writeToFile(configfile,autolog? "true" : "false", true);
                            finish();

                            //sending information to next page
                            Intent i = new Intent(LoginScreen.this, MissionSelectScreen.class);
                            i.putExtra("user_id",id_str);
                            startActivity(i);
                        }else{
                            Toast.makeText(LoginScreen.this, getString(R.string.id_match_error) ,Toast.LENGTH_LONG).show();
                            id_inp.setText("");
                        }

                    }
                });
                 if(autolog_chk.isChecked()){
                     submit_btn.performClick();
                 }
            }

    }


    //database getter place
    private boolean getValidity(){
        QueryBuilder<UserInf> query = daoSession.getUserInfDao().queryBuilder();
        List<UserInf> c_user = query.where(UserInfDao.Properties.UserId.eq(id_inp.getText().toString())).list();
        if(c_user.size() == 0){
            return false;
        }
        else{
            return true;
        }

    }
    /**
     *  It writes given string to given file in given mode
     * @param f File to write
     * @param data String to write
     * @param append    Write mode, if true doesnot clear file
     */
    public static void writeToFile(File f, Object data, boolean append) {
        PrintStream stream;
        if(append){
            try {
                stream = new PrintStream(new FileOutputStream(f, true));
                stream.println(data);
                stream.close();
            } catch (Exception e) {

            }
        }else{
            try {
                stream = new PrintStream(f);
                stream.println(data);
                stream.close();
            } catch (Exception e) {

            }
        }


    }

    /**
     * Reads N distinct line from given file
     * @param f  File to read lines
     * @param n  Line Number that we read from file
     * @return  array with n elements that has strings
     */
    public static String[] readNLineFromFile(File f, int n) {
        String[] toRet = new String[n];
        Scanner scanfile = null;
        try{
            scanfile = new Scanner(f);
        }catch (Exception e){

        }
        for(int i = 0; i < n ; i++){
            boolean hasNext = false;
            try{
                hasNext = scanfile.hasNextLine();

            }catch (Exception e){

            }
            if(hasNext){
                toRet[i] = scanfile.nextLine();
            }else{
                toRet[i] = "";
            }
        }
        try{
            scanfile.close();
        }catch (Exception e){

        }

        return toRet;
    }

    //to exit user push back button two times
    // temp value makes count
    int temp = 1;
    public void onBackPressed() {
        //if in 4 seconds user presses second time, temp will be 0 and program will finish.
        if(temp == 0){
            finish();
        }
        temp--;
        Toast.makeText(LoginScreen.this, getString(R.string.to_exit_str) ,Toast.LENGTH_LONG).show();
        //if in 4 seconds, user cannot press second time, it resets counter to prevent misdoings
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                temp++;
            }
        }, 4000);
    }


    /**
     * Adding new user to database
     * @param userName
     * @param userSurname
     * @param email
     * @param phone
     */
    public void addUser(String userName, String userSurname, String email, String phone){
        UserInf user = new UserInf();
        user.setUserId(null);
        user.setUserPhone(phone);
        user.setUserEmail(email);
        user.setUserName(userName);
        user.setUserSurname(userSurname);
        daoSession.getUserInfDao().insert(user);

    }

    /**
     * Adding new farm to database
     * @param farmname
     */
    public void addFarm(String farmname){
        FarmInf farm = new FarmInf();
        farm.setFarmID(null);
        farm.setFarmName(farmname);
        daoSession.getFarmInfDao().insert(farm);
    }

    /**
     * Adding new duty to database
     * @param farmid
     * @param userid
     */
    public void addDuty(long farmid, long userid){
        DutyInf duty = new DutyInf();
        duty.setDutyId(null);
        duty.setFarm_id(farmid);
        duty.setUser(userid);
        daoSession.getDutyInfDao().insert(duty);
    }

    /**
     * Adding new tank to database
     * @param limit
     * @param tankn
     * @param truck_id
     */
    public void addTank(int limit, int tankn, long truck_id){
        TankInf tank = new TankInf();
        tank.setFullness(0);
        tank.setLimit(limit);
        tank.setTankId(null);
        tank.setTankN(tankn);
        tank.setTruck(truck_id);
        daoSession.getTankInfDao().insert(tank);
    }

    /**
     * Adding new track to database
     * @param tankn
     * @param plate
     * @param user_id
     */
    public void addTruck(int tankn, String plate, long user_id){
        TruckInf truck = new TruckInf();
        truck.setN_tank(tankn);
        truck.setPlate(plate);
        truck.setTruckId(null);
        truck.setUser(user_id);
        daoSession.getTruckInfDao().insert(truck);
    }



    }

