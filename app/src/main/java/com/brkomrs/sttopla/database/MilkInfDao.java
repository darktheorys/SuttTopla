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
        public final static Property Tank1 = new Property(2, long.class, "tank1", false, "TANK1");
        public final static Property Tank2 = new Property(3, long.class, "tank2", false, "TANK2");
        public final static Property Tank3 = new Property(4, long.class, "tank3", false, "TANK3");
        public final static Property Tank4 = new Property(5, long.class, "tank4", false, "TANK4");
        public final static Property Tank5 = new Property(6, long.class, "tank5", false, "TANK5");
        public final static Property Duty = new Property(7, long.class, "duty", false, "DUTY");
        public final static Property Milktype = new Property(8, String.class, "milktype", false, "MILKTYPE");
        public final static Property Liter = new Property(9, int.class, "liter", false, "LITER");
        public final static Property Antibiotic_inf = new Property(10, boolean.class, "antibiotic_inf", false, "ANTIBIOTIC_INF");
        public final static Property Temp = new Property(11, String.class, "temp", false, "TEMP");
        public final static Property R_temp = new Property(12, String.class, "r_temp", false, "R_TEMP");
        public final static Property Alcohol = new Property(13, boolean.class, "alcohol", false, "ALCOHOL");
        public final static Property Alcoholtype = new Property(14, String.class, "alcoholtype", false, "ALCOHOLTYPE");
        public final static Property Comment = new Property(15, String.class, "comment", false, "COMMENT");
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
                "\"TANK1\" INTEGER NOT NULL ," + // 2: tank1
                "\"TANK2\" INTEGER NOT NULL ," + // 3: tank2
                "\"TANK3\" INTEGER NOT NULL ," + // 4: tank3
                "\"TANK4\" INTEGER NOT NULL ," + // 5: tank4
                "\"TANK5\" INTEGER NOT NULL ," + // 6: tank5
                "\"DUTY\" INTEGER NOT NULL ," + // 7: duty
                "\"MILKTYPE\" TEXT," + // 8: milktype
                "\"LITER\" INTEGER NOT NULL ," + // 9: liter
                "\"ANTIBIOTIC_INF\" INTEGER NOT NULL ," + // 10: antibiotic_inf
                "\"TEMP\" TEXT," + // 11: temp
                "\"R_TEMP\" TEXT," + // 12: r_temp
                "\"ALCOHOL\" INTEGER NOT NULL ," + // 13: alcohol
                "\"ALCOHOLTYPE\" TEXT," + // 14: alcoholtype
                "\"COMMENT\" TEXT);"); // 15: comment
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
        stmt.bindLong(3, entity.getTank1());
        stmt.bindLong(4, entity.getTank2());
        stmt.bindLong(5, entity.getTank3());
        stmt.bindLong(6, entity.getTank4());
        stmt.bindLong(7, entity.getTank5());
        stmt.bindLong(8, entity.getDuty());
 
        String milktype = entity.getMilktype();
        if (milktype != null) {
            stmt.bindString(9, milktype);
        }
        stmt.bindLong(10, entity.getLiter());
        stmt.bindLong(11, entity.getAntibiotic_inf() ? 1L: 0L);
 
        String temp = entity.getTemp();
        if (temp != null) {
            stmt.bindString(12, temp);
        }
 
        String r_temp = entity.getR_temp();
        if (r_temp != null) {
            stmt.bindString(13, r_temp);
        }
        stmt.bindLong(14, entity.getAlcohol() ? 1L: 0L);
 
        String alcoholtype = entity.getAlcoholtype();
        if (alcoholtype != null) {
            stmt.bindString(15, alcoholtype);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(16, comment);
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
        stmt.bindLong(3, entity.getTank1());
        stmt.bindLong(4, entity.getTank2());
        stmt.bindLong(5, entity.getTank3());
        stmt.bindLong(6, entity.getTank4());
        stmt.bindLong(7, entity.getTank5());
        stmt.bindLong(8, entity.getDuty());
 
        String milktype = entity.getMilktype();
        if (milktype != null) {
            stmt.bindString(9, milktype);
        }
        stmt.bindLong(10, entity.getLiter());
        stmt.bindLong(11, entity.getAntibiotic_inf() ? 1L: 0L);
 
        String temp = entity.getTemp();
        if (temp != null) {
            stmt.bindString(12, temp);
        }
 
        String r_temp = entity.getR_temp();
        if (r_temp != null) {
            stmt.bindString(13, r_temp);
        }
        stmt.bindLong(14, entity.getAlcohol() ? 1L: 0L);
 
        String alcoholtype = entity.getAlcoholtype();
        if (alcoholtype != null) {
            stmt.bindString(15, alcoholtype);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(16, comment);
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
            cursor.getLong(offset + 2), // tank1
            cursor.getLong(offset + 3), // tank2
            cursor.getLong(offset + 4), // tank3
            cursor.getLong(offset + 5), // tank4
            cursor.getLong(offset + 6), // tank5
            cursor.getLong(offset + 7), // duty
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // milktype
            cursor.getInt(offset + 9), // liter
            cursor.getShort(offset + 10) != 0, // antibiotic_inf
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // temp
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // r_temp
            cursor.getShort(offset + 13) != 0, // alcohol
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // alcoholtype
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // comment
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MilkInf entity, int offset) {
        entity.setMilkId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFarm(cursor.getLong(offset + 1));
        entity.setTank1(cursor.getLong(offset + 2));
        entity.setTank2(cursor.getLong(offset + 3));
        entity.setTank3(cursor.getLong(offset + 4));
        entity.setTank4(cursor.getLong(offset + 5));
        entity.setTank5(cursor.getLong(offset + 6));
        entity.setDuty(cursor.getLong(offset + 7));
        entity.setMilktype(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLiter(cursor.getInt(offset + 9));
        entity.setAntibiotic_inf(cursor.getShort(offset + 10) != 0);
        entity.setTemp(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setR_temp(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setAlcohol(cursor.getShort(offset + 13) != 0);
        entity.setAlcoholtype(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setComment(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
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