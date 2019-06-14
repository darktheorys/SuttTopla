package com.brkomrs.sttopla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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


import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.File;

public class LoginScreen extends AppCompatActivity {
    private String id = "";
    private boolean autolog = false;
    public File configfile = null;
    private CheckBox remember_chk,autolog_chk;
    private Button submit_btn;
    private EditText id_inp;
    private SwipeRefreshLayout swipe;

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
        File path = LoginScreen.this.getFilesDir();
        configfile = new File(path, getString(R.string.configfilename_str));
        //if file doesnot exists, it creates
        if (!configfile.exists()) {
            try{
                configfile.createNewFile();
            }catch (Exception e){
                Toast.makeText(LoginScreen.this, getString(R.string.error_file_str) ,Toast.LENGTH_SHORT).show();
            }

        }
        //getting saved id from file
        id_inp = findViewById(R.id.id_input);
        String[] fromFile = readNLineFromFile(configfile, 2);
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
            moveForward(haveConnection());
            //Swipe refresh initialization, and its delay
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moveForward(haveConnection());
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
     */
    public boolean haveConnection() {

        boolean wifi = false;
        boolean mobile = false;
        ConnectivityManager cn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] ni_arr = null;
        try {
            ni_arr = cn.getAllNetworkInfo();
        } catch (Exception e) {
            Toast.makeText(LoginScreen.this, getString(R.string.connection_err_str), Toast.LENGTH_SHORT).show();
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
                                id = id_inp.getText().toString();
                                writeToFile(configfile,id,false);
                            }else{
                                writeToFile(configfile,"",false);
                            }

                            //if autolog checkbox is checked, then it writes true to the second line of file
                            // else it writes false
                            autolog = autolog_chk.isChecked();
                            writeToFile(configfile,autolog? "true" : "false", true);
                            finish();
                            startActivity(new Intent(LoginScreen.this, MissionSelectScreen.class));
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




        if(id_inp.getText().toString().equals( "123456")){
            return true;
        }
        else{
            return false;
        }

    }
    /**
     *  It writes given string to given file in given mode
     * @param f File to write
     * @param data String to write
     * @param append    Write mode, if true doesnot clear file
     */
    public static void writeToFile(File f, String data, boolean append) {
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
    }

