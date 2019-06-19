package com.brkomrs.sttopla.necessary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.brkomrs.sttopla.R;
import com.brkomrs.sttopla.database.*;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class helperFunctions {

    helperFunctions(){

    }


    /**
     *  Connection checker
     * @return Return if android can access internet or not
     * @param con context of caller
     */
    public static boolean haveConnection(Context con) {

        boolean wifi = false;
        boolean mobile = false;
        ConnectivityManager cn = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
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
     * Adding new user to database
     * @param daoSession current db session
     * @param userName
     * @param userSurname
     * @param email
     * @param phone
     */
    public static void addUser(DaoSession daoSession, String userName, String userSurname, String email, String phone){
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
     * @param daoSession current db session
     * @param farmname
     */
    public static void addFarm(DaoSession daoSession, String farmname){
        FarmInf farm = new FarmInf();
        farm.setFarmID(null);
        farm.setFarmName(farmname);
        daoSession.getFarmInfDao().insert(farm);
    }

    /**
     * Adding new duty to database
     * @param daoSession current db session
     * @param farmid
     * @param userid
     */
    public static void addDuty(DaoSession daoSession, long farmid, long userid, boolean bool){
        DutyInf duty = new DutyInf();
        duty.setDutyId(null);
        duty.setFarm_id(farmid);
        duty.setUser(userid);
        duty.setDone(bool);
        daoSession.getDutyInfDao().insert(duty);
    }

    /**
     * Adding new tank to database
     * @param daoSession current db session
     * @param limit
     * @param tankn
     * @param truck_id
     */
    public static void addTank(DaoSession daoSession, int limit, int tankn, long truck_id){
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
     * @param daoSession current db session
     * @param tankn
     * @param plate
     * @param user_id
     */
    public static void addTruck(DaoSession daoSession, int tankn, String plate, long user_id){
        TruckInf truck = new TruckInf();
        truck.setN_tank(tankn);
        truck.setPlate(plate);
        truck.setTruckId(null);
        truck.setUser(user_id);
        daoSession.getTruckInfDao().insert(truck);
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
     * To get user from database for a given id
     * @param ses
     * @param user_id
     * @return
     */
    public static UserInf getUser(DaoSession ses, long user_id){
        QueryBuilder<UserInf> q = ses.getUserInfDao().queryBuilder();
        List<UserInf> list = q.where(UserInfDao.Properties.UserId.eq(user_id)).list();
        if (list.size() > 0 ){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * To get truck from database for a given id
     * @param ses
     * @param user_id
     * @return
     */
    public static TruckInf getTruck(DaoSession ses, long user_id){
        QueryBuilder<TruckInf> q = ses.getTruckInfDao().queryBuilder();
        List<TruckInf> list = q.where(TruckInfDao.Properties.User.eq(user_id)).list();
        if (list.size() > 0 ){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * To get farm from database for a given id
     * @param ses
     * @param farm_id
     * @return
     */
    public static FarmInf getFarm(DaoSession ses, long farm_id){
        QueryBuilder<FarmInf> q = ses.getFarmInfDao().queryBuilder();
        List<FarmInf> list = q.where(FarmInfDao.Properties.FarmID.eq(farm_id)).list();
        if (list.size() > 0 ){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * To get tank info database for a given id
     * @param ses
     * @param truck_id
     * @return
     */
    public static TankInf getTank(DaoSession ses, long truck_id){
        QueryBuilder<TankInf> q = ses.getTankInfDao().queryBuilder();
        List<TankInf> list = q.where(TankInfDao.Properties.Truck.eq(truck_id)).list();
        if (list.size() > 0 ){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * To get duty from database for a given id
     * @param ses
     * @param duty_id
     * @return
     */
    public static DutyInf getDuty(DaoSession ses, long duty_id){
        QueryBuilder<DutyInf> q = ses.getDutyInfDao().queryBuilder();
        List<DutyInf> list = q.where(DutyInfDao.Properties.DutyId.eq(duty_id)).list();
        if (list.size() > 0 ){
            return list.get(0);
        }else{
            return null;
        }
    }




}
