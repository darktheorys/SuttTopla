package com.brkomrs.sttopla.necessary;

import android.os.AsyncTask;

import com.brkomrs.sttopla.database.DutyInf;
import com.brkomrs.sttopla.database.FarmInf;
import com.brkomrs.sttopla.database.MilkInf;
import com.brkomrs.sttopla.database.TankInf;
import com.brkomrs.sttopla.database.TruckInf;
import com.brkomrs.sttopla.database.UserInf;

import java.util.List;


public class HttpRequestList{
/*
    public HttpRequestList() {
    }



    public class HttpRequestUser extends AsyncTask<Void, Void, List<UserInf>> {
        @Override
        protected List<UserInf> doInBackground(Void... voids) {
            ServerGetter server_getter = new ServerGetter();
            return server_getter.findAllUsers();
        }

        @Override
        protected void onPostExecute(List<UserInf> objects) {
            super.onPostExecute(objects);
        }
    }
    public class HttpRequestTruck extends AsyncTask<Void, Void, List<TruckInf>> {
        @Override
        protected List<TruckInf> doInBackground(Void... voids) {
            ServerGetter server_getter = new ServerGetter();
            return server_getter.findAllTrucks();
        }

        @Override
        protected void onPostExecute(List<TruckInf> objects) {
            super.onPostExecute(objects);
        }
    }

    public class HttpRequestFarm extends AsyncTask<Void, Void, List<FarmInf>> {
        @Override
        protected List<FarmInf> doInBackground(Void... voids) {
            ServerGetter server_getter = new ServerGetter();
            return server_getter.findAllFarms();
        }

        @Override
        protected void onPostExecute(List<FarmInf> objects) {
            super.onPostExecute(objects);
        }
    }

    public class HttpRequestTank extends AsyncTask<Void, Void, List<TankInf>> {
        @Override
        protected List<TankInf> doInBackground(Void... voids) {
            ServerGetter server_getter = new ServerGetter();
            return server_getter.findAllTanks();
        }

        @Override
        protected void onPostExecute(List<TankInf> objects) {
            super.onPostExecute(objects);
        }
    }

    public class HttpRequestDuty extends AsyncTask<Void, Void, List<DutyInf>> {
        @Override
        protected List<DutyInf> doInBackground(Void... voids) {
            ServerGetter server_getter = new ServerGetter();
            return server_getter.findAllDuties();
        }

        @Override
        protected void onPostExecute(List<DutyInf> objects) {
            super.onPostExecute(objects);
        }
    }

    /*
    public class HttpPostMilk extends AsyncTask<Void, Void, List<MilkInf>> {
        @Override
        protected List<MilkInf> doInBackground(Void... voids) {
            ServerGetter server_getter = new ServerGetter();
            return server_getter.postMilks();
        }

        @Override
        protected void onPostExecute(List<MilkInf> objects) {
            super.onPostExecute(objects);
        }
    }

*/

}