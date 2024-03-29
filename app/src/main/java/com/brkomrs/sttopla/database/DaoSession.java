package com.brkomrs.sttopla.database;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.brkomrs.sttopla.database.DutyInf;
import com.brkomrs.sttopla.database.FarmInf;
import com.brkomrs.sttopla.database.MilkInf;
import com.brkomrs.sttopla.database.TankInf;
import com.brkomrs.sttopla.database.TruckInf;
import com.brkomrs.sttopla.database.UserInf;

import com.brkomrs.sttopla.database.DutyInfDao;
import com.brkomrs.sttopla.database.FarmInfDao;
import com.brkomrs.sttopla.database.MilkInfDao;
import com.brkomrs.sttopla.database.TankInfDao;
import com.brkomrs.sttopla.database.TruckInfDao;
import com.brkomrs.sttopla.database.UserInfDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dutyInfDaoConfig;
    private final DaoConfig farmInfDaoConfig;
    private final DaoConfig milkInfDaoConfig;
    private final DaoConfig tankInfDaoConfig;
    private final DaoConfig truckInfDaoConfig;
    private final DaoConfig userInfDaoConfig;

    private final DutyInfDao dutyInfDao;
    private final FarmInfDao farmInfDao;
    private final MilkInfDao milkInfDao;
    private final TankInfDao tankInfDao;
    private final TruckInfDao truckInfDao;
    private final UserInfDao userInfDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dutyInfDaoConfig = daoConfigMap.get(DutyInfDao.class).clone();
        dutyInfDaoConfig.initIdentityScope(type);

        farmInfDaoConfig = daoConfigMap.get(FarmInfDao.class).clone();
        farmInfDaoConfig.initIdentityScope(type);

        milkInfDaoConfig = daoConfigMap.get(MilkInfDao.class).clone();
        milkInfDaoConfig.initIdentityScope(type);

        tankInfDaoConfig = daoConfigMap.get(TankInfDao.class).clone();
        tankInfDaoConfig.initIdentityScope(type);

        truckInfDaoConfig = daoConfigMap.get(TruckInfDao.class).clone();
        truckInfDaoConfig.initIdentityScope(type);

        userInfDaoConfig = daoConfigMap.get(UserInfDao.class).clone();
        userInfDaoConfig.initIdentityScope(type);

        dutyInfDao = new DutyInfDao(dutyInfDaoConfig, this);
        farmInfDao = new FarmInfDao(farmInfDaoConfig, this);
        milkInfDao = new MilkInfDao(milkInfDaoConfig, this);
        tankInfDao = new TankInfDao(tankInfDaoConfig, this);
        truckInfDao = new TruckInfDao(truckInfDaoConfig, this);
        userInfDao = new UserInfDao(userInfDaoConfig, this);

        registerDao(DutyInf.class, dutyInfDao);
        registerDao(FarmInf.class, farmInfDao);
        registerDao(MilkInf.class, milkInfDao);
        registerDao(TankInf.class, tankInfDao);
        registerDao(TruckInf.class, truckInfDao);
        registerDao(UserInf.class, userInfDao);
    }
    
    public void clear() {
        dutyInfDaoConfig.clearIdentityScope();
        farmInfDaoConfig.clearIdentityScope();
        milkInfDaoConfig.clearIdentityScope();
        tankInfDaoConfig.clearIdentityScope();
        truckInfDaoConfig.clearIdentityScope();
        userInfDaoConfig.clearIdentityScope();
    }

    public DutyInfDao getDutyInfDao() {
        return dutyInfDao;
    }

    public FarmInfDao getFarmInfDao() {
        return farmInfDao;
    }

    public MilkInfDao getMilkInfDao() {
        return milkInfDao;
    }

    public TankInfDao getTankInfDao() {
        return tankInfDao;
    }

    public TruckInfDao getTruckInfDao() {
        return truckInfDao;
    }

    public UserInfDao getUserInfDao() {
        return userInfDao;
    }

}
