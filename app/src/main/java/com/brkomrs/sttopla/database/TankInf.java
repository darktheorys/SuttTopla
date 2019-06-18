package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;

import java.util.LinkedList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "tank_tbl")
public class TankInf {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long tankId;
    private long truck;
    private int tankN;
    private int limit;
    private int fullness;
    @Generated(hash = 1389288355)
    public TankInf(Long tankId, long truck, int tankN, int limit, int fullness) {
        this.tankId = tankId;
        this.truck = truck;
        this.tankN = tankN;
        this.limit = limit;
        this.fullness = fullness;
    }
    @Generated(hash = 2128023932)
    public TankInf() {
    }
    public Long getTankId() {
        return this.tankId;
    }
    public void setTankId(Long tankId) {
        this.tankId = tankId;
    }
    public long getTruck() {
        return this.truck;
    }
    public void setTruck(long truck) {
        this.truck = truck;
    }
    public int getTankN() {
        return this.tankN;
    }
    public void setTankN(int tankN) {
        this.tankN = tankN;
    }
    public int getLimit() {
        return this.limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getFullness() {
        return this.fullness;
    }
    public void setFullness(int fullness) {
        this.fullness = fullness;
    }



}
