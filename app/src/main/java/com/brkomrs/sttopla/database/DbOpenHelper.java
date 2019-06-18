package com.brkomrs.sttopla.database;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbOpenHelper extends DaoMaster.OpenHelper {
    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
