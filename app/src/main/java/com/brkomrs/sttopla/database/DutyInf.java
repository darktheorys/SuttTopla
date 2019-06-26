package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;


//out class now can be table of greendao
@Entity (nameInDb = "dbo.Duties")
public class DutyInf {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long Id;             // out unique key for farms

    private long TruckId;
    private long FarmId;

    @ToOne(joinProperty = "FarmId")
    private FarmInf Farm;

    @ToOne(joinProperty = "TruckId")
    private TruckInf Truck;


    private  boolean Sync;
    private boolean Done;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 470608906)
    private transient DutyInfDao myDao;


    @Generated(hash = 19522674)
    public DutyInf(Long Id, long TruckId, long FarmId, boolean Sync, boolean Done) {
        this.Id = Id;
        this.TruckId = TruckId;
        this.FarmId = FarmId;
        this.Sync = Sync;
        this.Done = Done;
    }


    @Generated(hash = 1782686538)
    public DutyInf() {
    }


    public Long getId() {
        return this.Id;
    }


    public void setId(Long Id) {
        this.Id = Id;
    }


    public long getTruckId() {
        return this.TruckId;
    }


    public void setTruckId(long TruckId) {
        this.TruckId = TruckId;
    }


    public long getFarmId() {
        return this.FarmId;
    }


    public void setFarmId(long FarmId) {
        this.FarmId = FarmId;
    }


    public boolean getDone() {
        return this.Done;
    }


    public void setDone(boolean Done) {
        this.Done = Done;
    }


    @Generated(hash = 1879370395)
    private transient Long Farm__resolvedKey;


    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1520341475)
    public FarmInf getFarm() {
        long __key = this.FarmId;
        if (Farm__resolvedKey == null || !Farm__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FarmInfDao targetDao = daoSession.getFarmInfDao();
            FarmInf FarmNew = targetDao.load(__key);
            synchronized (this) {
                Farm = FarmNew;
                Farm__resolvedKey = __key;
            }
        }
        return Farm;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1498369134)
    public void setFarm(@NotNull FarmInf Farm) {
        if (Farm == null) {
            throw new DaoException(
                    "To-one property 'FarmId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.Farm = Farm;
            FarmId = Farm.getId();
            Farm__resolvedKey = FarmId;
        }
    }


    @Generated(hash = 305190359)
    private transient Long Truck__resolvedKey;


    /** To-one relationship, resolved on first access. */
    @Generated(hash = 939853255)
    public TruckInf getTruck() {
        long __key = this.TruckId;
        if (Truck__resolvedKey == null || !Truck__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TruckInfDao targetDao = daoSession.getTruckInfDao();
            TruckInf TruckNew = targetDao.load(__key);
            synchronized (this) {
                Truck = TruckNew;
                Truck__resolvedKey = __key;
            }
        }
        return Truck;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1604153056)
    public void setTruck(@NotNull TruckInf Truck) {
        if (Truck == null) {
            throw new DaoException(
                    "To-one property 'TruckId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.Truck = Truck;
            TruckId = Truck.getId();
            Truck__resolvedKey = TruckId;
        }
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1643646391)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDutyInfDao() : null;
    }


    @Override
    public String toString() {
        return "Çiftlik: " +getFarm().getFarmName();
    }


    public boolean getSync() {
        return this.Sync;
    }


    public void setSync(boolean Sync) {
        this.Sync = Sync;
    }
}
