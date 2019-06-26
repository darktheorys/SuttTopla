package com.brkomrs.sttopla.database;

import android.widget.ListView;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.DaoException;

@Entity (nameInDb = "dbo.Trucks")
public class TruckInf {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long Id ;

    @ToMany(referencedJoinProperty  = "TruckId")
    @OrderBy("NTank ASC")
    private List<TankInf> Tanks;

    @ToMany(referencedJoinProperty  = "TruckId")
    @OrderBy("Id ASC")
    private List<DutyInf> Duties;


    private int TankNumber;
    private String Plate;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 749637881)
    private transient TruckInfDao myDao;
    @Generated(hash = 1283283254)
    public TruckInf(Long Id, int TankNumber, String Plate) {
        this.Id = Id;
        this.TankNumber = TankNumber;
        this.Plate = Plate;
    }
    @Generated(hash = 1559682025)
    public TruckInf() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public int getTankNumber() {
        return this.TankNumber;
    }
    public void setTankNumber(int TankNumber) {
        this.TankNumber = TankNumber;
    }
    public String getPlate() {
        return this.Plate;
    }
    public void setPlate(String Plate) {
        this.Plate = Plate;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 64296210)
    public List<TankInf> getTanks() {
        if (Tanks == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TankInfDao targetDao = daoSession.getTankInfDao();
            List<TankInf> TanksNew = targetDao._queryTruckInf_Tanks(Id);
            synchronized (this) {
                if (Tanks == null) {
                    Tanks = TanksNew;
                }
            }
        }
        return Tanks;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1422764443)
    public synchronized void resetTanks() {
        Tanks = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1182037437)
    public List<DutyInf> getDuties() {
        if (Duties == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DutyInfDao targetDao = daoSession.getDutyInfDao();
            List<DutyInf> DutiesNew = targetDao._queryTruckInf_Duties(Id);
            synchronized (this) {
                if (Duties == null) {
                    Duties = DutiesNew;
                }
            }
        }
        return Duties;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 851658272)
    public synchronized void resetDuties() {
        Duties = null;
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
    @Generated(hash = 657155411)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTruckInfDao() : null;
    }


}
