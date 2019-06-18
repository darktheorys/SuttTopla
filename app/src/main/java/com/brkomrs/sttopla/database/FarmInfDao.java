package com.brkomrs.sttopla.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ciftlik_tbl".
*/
public class FarmInfDao extends AbstractDao<FarmInf, Long> {

    public static final String TABLENAME = "ciftlik_tbl";

    /**
     * Properties of entity FarmInf.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FarmID = new Property(0, Long.class, "farmID", true, "_id");
        public final static Property IsTankClean = new Property(1, boolean.class, "isTankClean", false, "IS_TANK_CLEAN");
        public final static Property IsPumpClean = new Property(2, boolean.class, "isPumpClean", false, "IS_PUMP_CLEAN");
        public final static Property IsEnvClean = new Property(3, boolean.class, "isEnvClean", false, "IS_ENV_CLEAN");
        public final static Property IsWeighterClean = new Property(4, boolean.class, "isWeighterClean", false, "IS_WEIGHTER_CLEAN");
        public final static Property FarmName = new Property(5, String.class, "farmName", false, "FARM_NAME");
    }


    public FarmInfDao(DaoConfig config) {
        super(config);
    }
    
    public FarmInfDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ciftlik_tbl\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: farmID
                "\"IS_TANK_CLEAN\" INTEGER NOT NULL ," + // 1: isTankClean
                "\"IS_PUMP_CLEAN\" INTEGER NOT NULL ," + // 2: isPumpClean
                "\"IS_ENV_CLEAN\" INTEGER NOT NULL ," + // 3: isEnvClean
                "\"IS_WEIGHTER_CLEAN\" INTEGER NOT NULL ," + // 4: isWeighterClean
                "\"FARM_NAME\" TEXT);"); // 5: farmName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ciftlik_tbl\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FarmInf entity) {
        stmt.clearBindings();
 
        Long farmID = entity.getFarmID();
        if (farmID != null) {
            stmt.bindLong(1, farmID);
        }
        stmt.bindLong(2, entity.getIsTankClean() ? 1L: 0L);
        stmt.bindLong(3, entity.getIsPumpClean() ? 1L: 0L);
        stmt.bindLong(4, entity.getIsEnvClean() ? 1L: 0L);
        stmt.bindLong(5, entity.getIsWeighterClean() ? 1L: 0L);
 
        String farmName = entity.getFarmName();
        if (farmName != null) {
            stmt.bindString(6, farmName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FarmInf entity) {
        stmt.clearBindings();
 
        Long farmID = entity.getFarmID();
        if (farmID != null) {
            stmt.bindLong(1, farmID);
        }
        stmt.bindLong(2, entity.getIsTankClean() ? 1L: 0L);
        stmt.bindLong(3, entity.getIsPumpClean() ? 1L: 0L);
        stmt.bindLong(4, entity.getIsEnvClean() ? 1L: 0L);
        stmt.bindLong(5, entity.getIsWeighterClean() ? 1L: 0L);
 
        String farmName = entity.getFarmName();
        if (farmName != null) {
            stmt.bindString(6, farmName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FarmInf readEntity(Cursor cursor, int offset) {
        FarmInf entity = new FarmInf( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // farmID
            cursor.getShort(offset + 1) != 0, // isTankClean
            cursor.getShort(offset + 2) != 0, // isPumpClean
            cursor.getShort(offset + 3) != 0, // isEnvClean
            cursor.getShort(offset + 4) != 0, // isWeighterClean
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // farmName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FarmInf entity, int offset) {
        entity.setFarmID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIsTankClean(cursor.getShort(offset + 1) != 0);
        entity.setIsPumpClean(cursor.getShort(offset + 2) != 0);
        entity.setIsEnvClean(cursor.getShort(offset + 3) != 0);
        entity.setIsWeighterClean(cursor.getShort(offset + 4) != 0);
        entity.setFarmName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FarmInf entity, long rowId) {
        entity.setFarmID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FarmInf entity) {
        if(entity != null) {
            return entity.getFarmID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FarmInf entity) {
        return entity.getFarmID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}