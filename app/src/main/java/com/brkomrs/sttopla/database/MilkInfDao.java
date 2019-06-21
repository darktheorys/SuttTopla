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
 * DAO for table "sut_tbl".
*/
public class MilkInfDao extends AbstractDao<MilkInf, Long> {

    public static final String TABLENAME = "sut_tbl";

    /**
     * Properties of entity MilkInf.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property MilkId = new Property(0, Long.class, "milkId", true, "_id");
        public final static Property Farm = new Property(1, long.class, "farm", false, "FARM");
        public final static Property Tank_id = new Property(2, long.class, "tank_id", false, "TANK_ID");
        public final static Property TankN = new Property(3, int.class, "tankN", false, "TANK_N");
        public final static Property Duty = new Property(4, long.class, "duty", false, "DUTY");
        public final static Property Milktype = new Property(5, String.class, "milktype", false, "MILKTYPE");
        public final static Property Tank_liter = new Property(6, int.class, "tank_liter", false, "TANK_LITER");
        public final static Property Leave_milk = new Property(7, boolean.class, "leave_milk", false, "LEAVE_MILK");
        public final static Property Antibiotic_inf = new Property(8, boolean.class, "antibiotic_inf", false, "ANTIBIOTIC_INF");
        public final static Property Temp = new Property(9, double.class, "temp", false, "TEMP");
        public final static Property R_temp = new Property(10, double.class, "r_temp", false, "R_TEMP");
        public final static Property Alcohol = new Property(11, boolean.class, "alcohol", false, "ALCOHOL");
        public final static Property Alcoholtype = new Property(12, String.class, "alcoholtype", false, "ALCOHOLTYPE");
        public final static Property Comment = new Property(13, String.class, "comment", false, "COMMENT");
    }


    public MilkInfDao(DaoConfig config) {
        super(config);
    }
    
    public MilkInfDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"sut_tbl\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: milkId
                "\"FARM\" INTEGER NOT NULL ," + // 1: farm
                "\"TANK_ID\" INTEGER NOT NULL ," + // 2: tank_id
                "\"TANK_N\" INTEGER NOT NULL ," + // 3: tankN
                "\"DUTY\" INTEGER NOT NULL ," + // 4: duty
                "\"MILKTYPE\" TEXT," + // 5: milktype
                "\"TANK_LITER\" INTEGER NOT NULL ," + // 6: tank_liter
                "\"LEAVE_MILK\" INTEGER NOT NULL ," + // 7: leave_milk
                "\"ANTIBIOTIC_INF\" INTEGER NOT NULL ," + // 8: antibiotic_inf
                "\"TEMP\" REAL NOT NULL ," + // 9: temp
                "\"R_TEMP\" REAL NOT NULL ," + // 10: r_temp
                "\"ALCOHOL\" INTEGER NOT NULL ," + // 11: alcohol
                "\"ALCOHOLTYPE\" TEXT," + // 12: alcoholtype
                "\"COMMENT\" TEXT);"); // 13: comment
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"sut_tbl\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MilkInf entity) {
        stmt.clearBindings();
 
        Long milkId = entity.getMilkId();
        if (milkId != null) {
            stmt.bindLong(1, milkId);
        }
        stmt.bindLong(2, entity.getFarm());
        stmt.bindLong(3, entity.getTank_id());
        stmt.bindLong(4, entity.getTankN());
        stmt.bindLong(5, entity.getDuty());
 
        String milktype = entity.getMilktype();
        if (milktype != null) {
            stmt.bindString(6, milktype);
        }
        stmt.bindLong(7, entity.getTank_liter());
        stmt.bindLong(8, entity.getLeave_milk() ? 1L: 0L);
        stmt.bindLong(9, entity.getAntibiotic_inf() ? 1L: 0L);
        stmt.bindDouble(10, entity.getTemp());
        stmt.bindDouble(11, entity.getR_temp());
        stmt.bindLong(12, entity.getAlcohol() ? 1L: 0L);
 
        String alcoholtype = entity.getAlcoholtype();
        if (alcoholtype != null) {
            stmt.bindString(13, alcoholtype);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(14, comment);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MilkInf entity) {
        stmt.clearBindings();
 
        Long milkId = entity.getMilkId();
        if (milkId != null) {
            stmt.bindLong(1, milkId);
        }
        stmt.bindLong(2, entity.getFarm());
        stmt.bindLong(3, entity.getTank_id());
        stmt.bindLong(4, entity.getTankN());
        stmt.bindLong(5, entity.getDuty());
 
        String milktype = entity.getMilktype();
        if (milktype != null) {
            stmt.bindString(6, milktype);
        }
        stmt.bindLong(7, entity.getTank_liter());
        stmt.bindLong(8, entity.getLeave_milk() ? 1L: 0L);
        stmt.bindLong(9, entity.getAntibiotic_inf() ? 1L: 0L);
        stmt.bindDouble(10, entity.getTemp());
        stmt.bindDouble(11, entity.getR_temp());
        stmt.bindLong(12, entity.getAlcohol() ? 1L: 0L);
 
        String alcoholtype = entity.getAlcoholtype();
        if (alcoholtype != null) {
            stmt.bindString(13, alcoholtype);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(14, comment);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MilkInf readEntity(Cursor cursor, int offset) {
        MilkInf entity = new MilkInf( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // milkId
            cursor.getLong(offset + 1), // farm
            cursor.getLong(offset + 2), // tank_id
            cursor.getInt(offset + 3), // tankN
            cursor.getLong(offset + 4), // duty
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // milktype
            cursor.getInt(offset + 6), // tank_liter
            cursor.getShort(offset + 7) != 0, // leave_milk
            cursor.getShort(offset + 8) != 0, // antibiotic_inf
            cursor.getDouble(offset + 9), // temp
            cursor.getDouble(offset + 10), // r_temp
            cursor.getShort(offset + 11) != 0, // alcohol
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // alcoholtype
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // comment
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MilkInf entity, int offset) {
        entity.setMilkId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFarm(cursor.getLong(offset + 1));
        entity.setTank_id(cursor.getLong(offset + 2));
        entity.setTankN(cursor.getInt(offset + 3));
        entity.setDuty(cursor.getLong(offset + 4));
        entity.setMilktype(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTank_liter(cursor.getInt(offset + 6));
        entity.setLeave_milk(cursor.getShort(offset + 7) != 0);
        entity.setAntibiotic_inf(cursor.getShort(offset + 8) != 0);
        entity.setTemp(cursor.getDouble(offset + 9));
        entity.setR_temp(cursor.getDouble(offset + 10));
        entity.setAlcohol(cursor.getShort(offset + 11) != 0);
        entity.setAlcoholtype(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setComment(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MilkInf entity, long rowId) {
        entity.setMilkId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MilkInf entity) {
        if(entity != null) {
            return entity.getMilkId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MilkInf entity) {
        return entity.getMilkId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
