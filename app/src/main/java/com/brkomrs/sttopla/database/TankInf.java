package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.NotNull;

@Entity (nameInDb = "dbo.Tanks")
public class TankInf {


    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long Id;
    private long TruckId;
    private int NTank;
    private int Limit;
    private int Fullness;
    private boolean Sync;

    @ToOne(joinProperty = "TruckId")
    private TruckInf Truck;

    @ToMany(referencedJoinProperty  = "TankId")
    @OrderBy("TankFilled ASC")
    private  List<MilkInf> Milks;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2111422486)
    private transient TankInfDao myDao;

    @Generated(hash = 761301450)
    public TankInf(Long Id, long TruckId, int NTank, int Limit, int Fullness, boolean Sync) {
        this.Id = Id;
        this.TruckId = TruckId;
        this.NTank = NTank;
        this.Limit = Limit;
        this.Fullness = Fullness;
        this.Sync = Sync;
    }

    @Generated(hash = 2128023932)
    public TankInf() {
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

    public int getNTank() {
        return this.NTank;
    }

    public void setNTank(int NTank) {
        this.NTank = NTank;
    }

    public int getLimit() {
        return this.Limit;
    }

    public void setLimit(int Limit) {
        this.Limit = Limit;
    }

    public int getFullness() {
        return this.Fullness;
    }

    public void setFullness(int Fullness) {
        this.Fullness = Fullness;
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
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1711884585)
    public List<MilkInf> getMilks() {
        if (Milks == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MilkInfDao targetDao = daoSession.getMilkInfDao();
            List<MilkInf> MilksNew = targetDao._queryTankInf_Milks(Id);
            synchronized (this) {
                if (Milks == null) {
                    Milks = MilksNew;
                }
            }
        }
        return Milks;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 104281509)
    public synchronized void resetMilks() {
        Milks = null;
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
    @Generated(hash = 119267728)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTankInfDao() : null;
    }

    public boolean getSync() {
        return this.Sync;
    }

    public void setSync(boolean Sync) {
        this.Sync = Sync;
    }




}
