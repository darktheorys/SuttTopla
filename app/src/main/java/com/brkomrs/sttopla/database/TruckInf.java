package com.brkomrs.sttopla.database;

import android.widget.ListView;

import org.greenrobot.greendao.annotation.Entity;

import java.util.LinkedList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "tir_tbl")
public class TruckInf {
    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long truckId ;
    private long user;
    private int n_tank;
    private String plate;
    @Generated(hash = 181253969)
    public TruckInf(Long truckId, long user, int n_tank, String plate) {
        this.truckId = truckId;
        this.user = user;
        this.n_tank = n_tank;
        this.plate = plate;
    }
    @Generated(hash = 1559682025)
    public TruckInf() {
    }
    public Long getTruckId() {
        return this.truckId;
    }
    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }
    public long getUser() {
        return this.user;
    }
    public void setUser(long user) {
        this.user = user;
    }
    public int getN_tank() {
        return this.n_tank;
    }
    public void setN_tank(int n_tank) {
        this.n_tank = n_tank;
    }
    public String getPlate() {
        return this.plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }



}
