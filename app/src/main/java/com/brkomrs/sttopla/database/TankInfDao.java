package com.brkomrs.sttopla.database;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "dbo.Tanks".
*/
public class TankInfDao extends AbstractDao<TankInf, Long> {

    public static final String TABLENAME = "dbo.Tanks";

    /**
     * Properties of entity TankInf.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "Id", true, "_id");
        public final static Property TruckId = new Property(1, long.class, "TruckId", false, "TRUCK_ID");
        public final static Property NTank = new Property(2, int.class, "NTank", false, "NTANK");
        public final static Property Limit = new Property(3, int.class, "Limit", false, "LIMIT");
        public final static Property Fullness = new Property(4, int.class, "Fullness", false, "FULLNESS");
    }

    private DaoSession daoSession;

    private Query<TankInf> truckInf_TanksQuery;

    public TankInfDao(DaoConfig config) {
        super(config);
    }
    
    public TankInfDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"dbo.Tanks\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: Id
                "\"TRUCK_ID\" INTEGER NOT NULL ," + // 1: TruckId
                "\"NTANK\" INTEGER NOT NULL ," + // 2: NTank
                "\"LIMIT\" INTEGER NOT NULL ," + // 3: Limit
                "\"FULLNESS\" INTEGER NOT NULL );"); // 4: Fullness
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"dbo.Tanks\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TankInf entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
        stmt.bindLong(2, entity.getTruckId());
        stmt.bindLong(3, entity.getNTank());
        stmt.bindLong(4, entity.getLimit());
        stmt.bindLong(5, entity.getFullness());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TankInf entity) {
        stmt.clearBindings();
 
        Long Id = entity.getId();
        if (Id != null) {
            stmt.bindLong(1, Id);
        }
        stmt.bindLong(2, entity.getTruckId());
        stmt.bindLong(3, entity.getNTank());
        stmt.bindLong(4, entity.getLimit());
        stmt.bindLong(5, entity.getFullness());
    }

    @Override
    protected final void attachEntity(TankInf entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TankInf readEntity(Cursor cursor, int offset) {
        TankInf entity = new TankInf( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Id
            cursor.getLong(offset + 1), // TruckId
            cursor.getInt(offset + 2), // NTank
            cursor.getInt(offset + 3), // Limit
            cursor.getInt(offset + 4) // Fullness
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TankInf entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTruckId(cursor.getLong(offset + 1));
        entity.setNTank(cursor.getInt(offset + 2));
        entity.setLimit(cursor.getInt(offset + 3));
        entity.setFullness(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TankInf entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TankInf entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TankInf entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "Tanks" to-many relationship of TruckInf. */
    public List<TankInf> _queryTruckInf_Tanks(long TruckId) {
        synchronized (this) {
            if (truckInf_TanksQuery == null) {
                QueryBuilder<TankInf> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TruckId.eq(null));
                queryBuilder.orderRaw("T.'NTANK' ASC");
                truckInf_TanksQuery = queryBuilder.build();
            }
        }
        Query<TankInf> query = truckInf_TanksQuery.forCurrentThread();
        query.setParameter(0, TruckId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTruckInfDao().getAllColumns());
            builder.append(" FROM dbo.Tanks T");
            builder.append(" LEFT JOIN dbo.Trucks T0 ON T.\"TRUCK_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected TankInf loadCurrentDeep(Cursor cursor, boolean lock) {
        TankInf entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TruckInf Truck = loadCurrentOther(daoSession.getTruckInfDao(), cursor, offset);
         if(Truck != null) {
            entity.setTruck(Truck);
        }

        return entity;    
    }

    public TankInf loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<TankInf> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TankInf> list = new ArrayList<TankInf>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<TankInf> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TankInf> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
