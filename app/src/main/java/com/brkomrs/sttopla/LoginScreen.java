package com.brkomrs.sttopla;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.brkomrs.sttopla.database.DaoSession;
import com.brkomrs.sttopla.database.UserInf;
import com.brkomrs.sttopla.necessary.helperFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class LoginScreen extends AppCompatActivity {
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

        //connection of database session
        daoSession = ((dbHelper) getApplication()).getDaoSession();

        //widget getters
        id_inp = findViewById(R.id.id_input);
        swipe = findViewById(R.id.swipe);
        submit_btn = findViewById(R.id.submit_id);
        remember_chk = findViewById(R.id.remember_chk);
        autolog_chk = findViewById(R.id.autologin_chk);
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
             }
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //saving entered id
                        String id_str = id_inp.getText().toString();

                        // after clicking submit button, the followings happen
                        //if valid id is entered, it goes to mission page
                        //if not valit it shows an error and waits for another id
                        if(getValidity(id_str)){

                            try {
                                helperFunctions.getDatasFromServer(daoSession, id_str);
                            } catch (Exception e) {
                                Toast.makeText(LoginScreen.this, getString(R.string.connection_err_str), Toast.LENGTH_SHORT).show();
                                finish();
                            }

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


    //database getter place
    public boolean getValidity(String id){
        if(id.equalsIgnoreCase("")) return false;
        //if a local user exists it returns directly true
        UserInf user = helperFunctions.getUser(daoSession, Long.parseLong(id));
        if(user != null){
            return true;
        }


        //if no id is in local db, then app checks for server
        String url = helperFunctions.prefix + "sserver/api/users/" + id;

        try {
            //gets specicific user from server
            JSONObject jo = helperFunctions.getJSONObjectFromURL(url);
            user = helperFunctions.parseUser(jo);
            //if user is null then it means we dont have a record
            if(user != null){
                helperFunctions.addUser(daoSession, user);
                return true;
            }else return false;
        } catch (IOException e) {
            Toast.makeText(LoginScreen.this, getString(R.string.connection_err_str), Toast.LENGTH_SHORT).show();
            return false;
        } catch (JSONException e) {
            Toast.makeText(LoginScreen.this, getString(R.string.connection_err_str), Toast.LENGTH_SHORT).show();
            return false;
        }

/*
        QueryBuilder<UserInf> query = daoSession.getUserInfDao().queryBuilder();
        List<UserInf> c_user = query.where(UserInfDao.Properties.Id.eq(id)).list();

        //if a user exists it returns true
        if(c_user.size() > 0){
            return true;
        }
        else{
            return false;
        }
        */
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

