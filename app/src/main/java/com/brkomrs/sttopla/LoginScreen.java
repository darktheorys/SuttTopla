package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.EditText;
import com.brkomrs.sttopla.database.DaoSession;
import com.brkomrs.sttopla.database.UserInf;
import com.brkomrs.sttopla.database.UserInfDao;
import org.greenrobot.greendao.query.QueryBuilder;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.concurrent.ExecutionException;

import com.brkomrs.sttopla.necessary.HttpRequestList;
import com.brkomrs.sttopla.necessary.helperFunctions;

public class LoginScreen extends AppCompatActivity {
    public File configfile,db;
    private CheckBox remember_chk,autolog_chk;
    private Button submit_btn;
    private EditText id_inp;
    private SwipeRefreshLayout swipe;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*
        HttpRequestList temp = new HttpRequestList();
        try {
            List<UserInf> users = temp.new HttpRequestUser().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //connection of database session
        daoSession = ((dbHelper) getApplication()).getDaoSession();

        //widget getters
        id_inp = findViewById(R.id.id_input);
        swipe = findViewById(R.id.swipe);
        submit_btn = findViewById(R.id.submit_id);
        remember_chk = findViewById(R.id.remember_chk);
        autolog_chk = findViewById(R.id.autologin_chk);
        //button is disabled at first
        submit_btn.setEnabled(false);
        //construction of id.txt file, to save
        //path of current installation
        File path = LoginScreen.this.getFilesDir();
        configfile = new File(path, getString(R.string.configfilename_str));

        //creation of helper db file, it is for understanding of existent database
        db = new File(path,"db");

        //if db is not existing, then we create one, usually after clean installation
        if(!db.exists()){
            try {
                if(db.createNewFile()) helperFunctions.writeToFile(db,getString(R.string.connected_str), false);

            } catch (IOException e) {
                e.printStackTrace();
            }
            // setting database from scratch after installation

            //adding users
            helperFunctions.addUser(daoSession,"burak","ömür", "email1", "phone1");
            helperFunctions.addUser(daoSession,"mazlum","yıldırım", "email2", "phone2");
            helperFunctions.addUser(daoSession,"ramazan","yurt", "email3", "phone3");
            helperFunctions.addUser(daoSession,"doğukan","yıldırım", "email4", "phone4");
            helperFunctions.addUser(daoSession,"temp","user","sdf","fsld");

            //adding trucks
            helperFunctions.addTruck(daoSession,4,"16abc32",1);
            helperFunctions.addTruck(daoSession,5,"34asf78",2);
            helperFunctions.addTruck(daoSession,4,"26vgd77",3);
            helperFunctions.addTruck(daoSession,5,"67asd54",4);

            //adding farms
            helperFunctions.addFarm(daoSession,"Alibaba");
            helperFunctions.addFarm(daoSession,"Sütaş Ana Çiftlik");
            helperFunctions.addFarm(daoSession,"Samsun Merkez");
            helperFunctions.addFarm(daoSession,"Karacabey");

            //addding tanks
            helperFunctions.addTank(daoSession,200,1, 1);
            helperFunctions.addTank(daoSession,200,2, 1);
            helperFunctions.addTank(daoSession,200,3, 1);
            helperFunctions.addTank(daoSession,200,4, 1);

            helperFunctions.addTank(daoSession,200,1, 2);
            helperFunctions.addTank(daoSession,200,2, 2);
            helperFunctions.addTank(daoSession,200,3, 2);
            helperFunctions.addTank(daoSession,200,4, 2);
            helperFunctions.addTank(daoSession,200,5, 2);

            helperFunctions.addTank(daoSession,200,1, 3);
            helperFunctions.addTank(daoSession,200,2, 3);
            helperFunctions.addTank(daoSession,200,3, 3);
            helperFunctions.addTank(daoSession,200,4, 3);

            helperFunctions.addTank(daoSession,200,1, 4);
            helperFunctions.addTank(daoSession,200,2, 4);
            helperFunctions.addTank(daoSession,200,3, 4);
            helperFunctions.addTank(daoSession,200,4, 4);
            helperFunctions.addTank(daoSession,200,5, 4);


            //duty adding
            helperFunctions.addDuty(daoSession,1,1,false);
            helperFunctions.addDuty(daoSession,1,2,false);
            helperFunctions.addDuty(daoSession,2,1,false);
            helperFunctions.addDuty(daoSession,2,2,false);
            helperFunctions.addDuty(daoSession,2,3,false);
            helperFunctions.addDuty(daoSession,3,1,false);
            helperFunctions.addDuty(daoSession,3,2,false);
            helperFunctions.addDuty(daoSession,3,3,false);
            helperFunctions.addDuty(daoSession,1,3,false);
            helperFunctions.addDuty(daoSession,4,4,false);
            helperFunctions.addDuty(daoSession,4,2,false);


        }
        //retrieving data from configfile
        checkConfigFile(configfile);

        //at the beginning of app
        //it automatically checks for connection, and moves forward
        loginActions(helperFunctions.haveConnection(LoginScreen.this));
        //Swipe refresh initialization, and its delay
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loginActions(helperFunctions.haveConnection(LoginScreen.this));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        }


    private void checkConfigFile(File config) {
        //getting saved id from file
        String[] fromFile = helperFunctions.readNLineFromFile(config, 2);
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
    }


    /**
     * takes a boolean and if true it makes enable submit button
     * it also checks buttons and input box and writes it to out configfile, in every login
     * also autologs if checkbox is checked
     * @param conn boolean to know if connection exists
     */
    private void loginActions(boolean conn) {
             if (!conn) {
                Toast.makeText(LoginScreen.this, getString(R.string.connection_err_str), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginScreen.this, getString(R.string.connected_str), Toast.LENGTH_SHORT).show();
                //enabling button
                submit_btn.setEnabled(true);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //saving entered id
                        String id_str = id_inp.getText().toString();

                        // after clicking submit button, the followings happen
                        //if valid id is entered, it goes to mission page
                        //if not valit it shows an error and waits for another id
                        if(getValidity(id_str)){
                            Toast.makeText(LoginScreen.this, getString(R.string.log_making_str) ,Toast.LENGTH_SHORT).show();
                            //if remember checkbox is checked, then we save id in the textEdit to the file.
                            // if not checked then, it saved empty string to file, replaces previous savings

                            if (remember_chk.isChecked()) {
                                helperFunctions.writeToFile(configfile,id_str,false);
                            }else{
                                //writing empty to file as flag
                                helperFunctions.writeToFile(configfile,"",false);
                            }

                            //if autolog checkbox is checked, then it writes true to the second line of file
                            // else it writes false
                            helperFunctions.writeToFile(configfile,autolog_chk.isChecked()? "true" : "false", true);
                            //finishing activity
                            finish();

                            //sending information to next page
                            Intent i = new Intent(LoginScreen.this, MissionSelectScreen.class);
                            i.putExtra(getString(R.string.user_id_extra_str), id_str);
                            startActivity(i);
                        }else{
                            Toast.makeText(LoginScreen.this, getString(R.string.id_match_error) ,Toast.LENGTH_LONG).show();
                            id_inp.setText("");
                        }
                    }
                });
                //making autologin
                 if(autolog_chk.isChecked()){
                     submit_btn.setEnabled(true);
                     submit_btn.performClick();
                 }
            }

    }


    //database getter place
    public boolean getValidity(String id){
        QueryBuilder<UserInf> query = daoSession.getUserInfDao().queryBuilder();
        List<UserInf> c_user = query.where(UserInfDao.Properties.UserId.eq(id)).list();

        //if a user exists it returns true
        if(c_user.size() > 0){
            return true;
        }
        else{
            return false;
        }
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
        Toast.makeText(LoginScreen.this, getString(R.string.to_exit_str) ,Toast.LENGTH_SHORT).show();
        //if in 4 seconds, user cannot press second time, it resets counter to prevent misdoings
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                temp++;
            }
        }, 2000);
    }
    }

