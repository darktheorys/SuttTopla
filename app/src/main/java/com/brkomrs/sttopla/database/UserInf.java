package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;

import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity (nameInDb = "dbo.Users")
public class UserInf {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long Id;

    private long TruckId;

    private String Name;
    private String Surname;

    private String Phone;
    private String Email;

    @ToOne(joinProperty = "TruckId")
    private TruckInf Truck;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1740511039)
    private transient UserInfDao myDao;

    @Generated(hash = 363117237)
    public UserInf(Long Id, long TruckId, String Name, String Surname, String Phone,
            String Email) {
        this.Id = Id;
        this.TruckId = TruckId;
        this.Name = Name;
        this.Surname = Surname;
        this.Phone = Phone;
        this.Email = Email;
    }

    @Generated(hash = 965704295)
    public UserInf() {
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

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSurname() {
        return this.Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getPhone() {
        return this.Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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
    @Generated(hash = 1190301612)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserInfDao() : null;
    }

}
