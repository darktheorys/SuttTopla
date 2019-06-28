package com.brkomrs.sttopla;

import android.app.Application;

import com.brkomrs.sttopla.database.DaoMaster;
import com.brkomrs.sttopla.database.DaoSession;
import com.brkomrs.sttopla.database.DbOpenHelper;


//helper class for session getting
public class dbHelper extends Application {
    DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //creating db file named "sut_toplama.db"
        daoSession = new DaoMaster(new DbOpenHelper(this, "sut_toplama.db").getWritableDb()).newSession();

    }


    public DaoSession getDaoSession(){
        return this.daoSession;
    }
}
