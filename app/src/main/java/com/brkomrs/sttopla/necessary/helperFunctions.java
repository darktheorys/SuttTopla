package com.brkomrs.sttopla.necessary;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.brkomrs.sttopla.PushService;
import com.brkomrs.sttopla.R;
import com.brkomrs.sttopla.database.*;

import org.apache.http.HttpStatus;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     */
    public static void addUser(DaoSession daoSession, UserInf user){
        QueryBuilder<UserInf> q = daoSession.getUserInfDao().queryBuilder();
        List<UserInf> list = q.where(UserInfDao.Properties.Id.eq(user.getId())).list();
        if(!(list.size() > 0)){
            daoSession.getUserInfDao().insert(user);
        }
    }

    /**
     * Adding new farm to database
     * @param daoSession current db session
     */
    public static void addFarm(DaoSession daoSession, FarmInf farm){

        QueryBuilder<FarmInf> q = daoSession.getFarmInfDao().queryBuilder();
        List<FarmInf> list = q.where(FarmInfDao.Properties.Id.eq(farm.getId())).list();
        if(!(list.size() > 0)){
            daoSession.getFarmInfDao().insert(farm);
        }
    }

    /**
     * Adding new duty to database
     * @param daoSession current db session
     */
    public static void addDuty(DaoSession daoSession, DutyInf duty){
        QueryBuilder<DutyInf> q = daoSession.getDutyInfDao().queryBuilder();
        List<DutyInf> list = q.where(DutyInfDao.Properties.Id.eq(duty.getId())).list();
        if(!(list.size() > 0)){
            Log.e("Added ",  duty.getId() + " added");
            daoSession.getDutyInfDao().insert(duty);
        }
        }


    /**
     * Adding new tank to database
     * @param daoSession current db session
     */
    public static void addTank(DaoSession daoSession, TankInf tank){
        QueryBuilder<TankInf> q = daoSession.getTankInfDao().queryBuilder();
        List<TankInf> list = q.where(TankInfDao.Properties.Id.eq(tank.getId())).list();
        if(!(list.size() > 0)){
            daoSession.getTankInfDao().insert(tank);
        }
    }

    /**
     * Adding new track to database
     * @param daoSession current db session
     */
    public static void addTruck(DaoSession daoSession, TruckInf truck){
        QueryBuilder<TruckInf> q = daoSession.getTruckInfDao().queryBuilder();
        List<TruckInf> list = q.where(TruckInfDao.Properties.Id.eq(truck.getId())).list();
        if(!(list.size() > 0)){
            daoSession.getTruckInfDao().insert(truck);
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
        List<UserInf> list = q.where(UserInfDao.Properties.Id.eq(user_id)).list();
        if (list.size() > 0 ){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * To get truck from database for a given id
     * @param ses
     * @param truckid
     * @return
     */
    public static TruckInf getTruck(DaoSession ses, long truckid){
        QueryBuilder<TruckInf> q = ses.getTruckInfDao().queryBuilder();
        List<TruckInf> list = q.where(TruckInfDao.Properties.Id.eq(truckid)).list();
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
        List<FarmInf> list = q.where(FarmInfDao.Properties.Id.eq(farm_id)).list();
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
        List<DutyInf> list = q.where(DutyInfDao.Properties.Id.eq(duty_id)).list();
        if (list.size() > 0 ){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * To get duties from database for a given id
     * @param ses
     * @param userid
     * @return
     */
    public static List<DutyInf> getAllDuties(DaoSession ses, long userid){
        QueryBuilder<UserInf> q = ses.getUserInfDao().queryBuilder();
        List<UserInf> list = q.where(UserInfDao.Properties.Id.eq(userid)).list();

        if(list.size() > 0){
           TruckInf tr = list.get(0).getTruck();
            tr.resetDuties();
            return tr.getDuties();
        }
        return new ArrayList<>();
    }


    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(5000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        urlConnection.disconnect();
        String jsonString = sb.toString();
        if(jsonString.equalsIgnoreCase("")){
            return null;
        }

        return new JSONObject(jsonString);
    }

    public static UserInf parseUser(JSONObject jo) throws JSONException {
        if(jo != null){
            UserInf user = new UserInf();
            user.setId(Long.parseLong(jo.get("Id").toString()));
            user.setName(jo.get("Name").toString());
            user.setSurname(jo.get("Surname").toString());
            user.setPhone(jo.get("Phone").toString());
            user.setEmail(jo.get("Email").toString());
            JSONObject jo2 = new JSONObject(jo.get("Truck").toString());
            user.setTruckId(Long.parseLong(jo2.get("Id").toString()));
            return user;
        }
        return null;
    }

    private static TruckInf parseTruck(JSONObject jo, DaoSession ses) throws Exception {
        if(jo != null){

            TruckInf truck = new TruckInf();
            truck.setId(Long.parseLong(jo.get("Id").toString()));
            truck.setTankNumber(Integer.parseInt(jo.get("TankNumber").toString()));
            truck.setPlate(jo.get("Plate").toString());
            JSONArray ja = new JSONArray(jo.get("Duties").toString());

            parseDuties(ja, truck.getId(), ses);

            return truck;
        }
        return null;

    }

    public static void parseDuties(JSONArray ja, long truck_id, DaoSession ses) throws Exception {
        if(ja != null){
            for(int i = 0; i < ja.length(); i++){
                DutyInf duty = new DutyInf();
                JSONObject jo = (JSONObject) ja.get(i);
                duty.setId(Long.parseLong(jo.get("Id").toString()));
                duty.setFarmId(Long.parseLong(((JSONObject)jo.get("Farm")).get("Id").toString()));
                parseAddFarm(((JSONObject)jo.get("Farm")), ses);
                duty.setTruckId(truck_id);
                duty.setSync(true);
                duty.setDone(jo.get("Done").toString().equals("true"));
                addDuty(ses, duty);

            }
        }
    }

    private static void parseAddFarm(JSONObject jo, DaoSession ses) throws Exception{

        FarmInf farm = new FarmInf();
        farm.setId(Long.parseLong(jo.get("Id").toString()));
        farm.setFarmName(jo.get("FarmName").toString());
        addFarm(ses, farm);
    }


    public static void getDatasFromServer(DaoSession daoSession, String id_str ) throws Exception {
        String url = "http://192.168.182.225/sserver/api/trucks/";
        long id = Long.parseLong(id_str);
        QueryBuilder<UserInf> q = daoSession.getUserInfDao().queryBuilder();
        List<UserInf> users = q.where(UserInfDao.Properties.Id.eq(id)).list();
        UserInf user = null;
        JSONObject jo = null;
        if(users.size() > 0){
            user = users.get(0);
        }
        if(user != null){
            jo = getJSONObjectFromURL(url + user.getTruckId() + "");
        }
        if(jo != null){

            TruckInf truck = parseTruck(jo,daoSession);
            parseTanks(jo, daoSession, truck.getId());
            addTruck(daoSession,truck);
        }
    }

    /**
     * To send unsynced milks to server from local database
     * @param ses   daosession
     */
    public static void sendPost(final DaoSession ses){
        final String urlAdress = "http://192.168.182.225/sserver/api/milks/add";
        List<MilkInf> milks = getAllUnsyncMilks(ses);
        for(final MilkInf each : milks) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(urlAdress);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("TankFilled", each.getTankFilled());
                        jsonParam.put("Liter", each.getLiter());
                        jsonParam.put("MilkType", each.getMilkType());
                        jsonParam.put("LeaveMilk", each.getLeaveMilk());
                        jsonParam.put("AntibioticInf", each.getAlcoholInf());

                        jsonParam.put("Temp", each.getTemp());
                        jsonParam.put("RTemp",each.getRTemp());
                        jsonParam.put("AlcoholInf", each.getAlcoholInf());
                        jsonParam.put("AlcoholType", each.getAlcoholType());
                        jsonParam.put("Comment", each.getComment());
                        jsonParam.put("TankId", each.getTankId());

                        jsonParam.put("IsEnvClean", each.getIsEnvClean());
                        jsonParam.put("IsTankClean", each.getIsTankClean());
                        jsonParam.put("IsPumpClean", each.getIsPumpClean());
                        jsonParam.put("IsWeighterClean", each.getIsWeighterClean());
                        //Log.i("JSON_MILK", jsonParam.toString());

                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                        os.writeBytes(jsonParam.toString());

                        os.flush();
                        os.close();
                        if(conn.getResponseCode() == 200){
                            each.setSync(true);
                            ses.getMilkInfDao().update(each);
                            updateDutyAndTank(ses, each.getTankId());
                        }
                        Log.i("STATUS_MILK", String.valueOf(conn.getResponseCode()));
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }
    }

    /**
     * Sycnronization of duty and tank with serve
     * @param ses daosession
     * @param tankid some tank's id
     */
    private static void updateDutyAndTank(final DaoSession ses, long tankid){
        QueryBuilder<TankInf> tankq = ses.getTankInfDao().queryBuilder();
        List<TankInf> list = tankq.where(TankInfDao.Properties.Id.eq(tankid)).list();
        TruckInf truck = list.get(0).getTruck();
        //to sync unsynced duties with server
        truck.resetDuties();
        for ( final DutyInf each : truck.getDuties()){
            if(!each.getSync()){
                final String urlAdress = "http://192.168.182.225/sserver/api/duties/" + each.getId();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlAdress);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("Id", each.getId());
                            jsonParam.put("Done", each.getDone());
                            DateFormat dateFormat = DateFormat.getDateInstance();
                            Date date = new Date();
                            jsonParam.put("Date", dateFormat.format(date));
                            jsonParam.put("TruckId", each.getTruckId());

                            Log.i("JSON_DUTY", jsonParam.toString());

                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());
                            os.flush();
                            os.close();
                            if(conn.getResponseCode() == 200){
                                each.setSync(true);
                                ses.getDutyInfDao().update(each);
                            }
                            Log.i("STATUS_DUTY", String.valueOf(conn.getResponseCode()));
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }

        //to sync unsynced tanks with server
        truck.resetTanks();
        for ( final TankInf each : truck.getTanks()){
            if(!each.getSync()){
                final String urlAdress = "http://192.168.182.225/sserver/api/tanks/" + each.getId();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlAdress);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("Id", each.getId());
                            jsonParam.put("NTank", each.getNTank());
                            jsonParam.put("Limit", each.getLimit());
                            jsonParam.put("Fullness", each.getFullness());
                            jsonParam.put("TruckId", each.getTruckId());

                            //Log.i("JSON_TANK", jsonParam.toString());

                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                            os.writeBytes(jsonParam.toString());
                            os.flush();
                            os.close();
                            if(conn.getResponseCode() == 200){
                                each.setSync(true);
                                ses.getTankInfDao().update(each);
                            }
                            Log.i("STATUS_TANK", String.valueOf(conn.getResponseCode()));
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }


        }


    private static List<MilkInf> getAllUnsyncMilks(DaoSession ses) {
        QueryBuilder<MilkInf> q = ses.getMilkInfDao().queryBuilder();
        return q.where(MilkInfDao.Properties.Sync.eq(false)).list();
    }

    private static void parseTanks(JSONObject jo, DaoSession ses, long truck_id) throws Exception {
        JSONArray ja = new JSONArray(jo.get("Tanks").toString());
        for(int i = 0; i < ja.length(); i++){
            TankInf tank = new TankInf();
            tank.setSync(true);
            JSONObject jo_temp = (JSONObject) ja.get(i);
            tank.setId(Long.parseLong(jo_temp.get("Id").toString()));
            tank.setTruckId(truck_id);
            tank.setLimit(Integer.parseInt(jo_temp.get("Limit").toString()));
            tank.setFullness(Integer.parseInt(jo_temp.get("Fullness").toString()));
            tank.setNTank(Integer.parseInt(jo_temp.get("NTank").toString()));

            addTank(ses, tank);
        }
    }



    public static boolean serviceWorking(Context con){//Servis Çalışıyor mu kontrol eden fonksiyon

        ActivityManager manager = (ActivityManager) con.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ( PushService.class.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }


}
