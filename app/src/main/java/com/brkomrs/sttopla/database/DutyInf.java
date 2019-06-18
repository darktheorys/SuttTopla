package com.brkomrs.sttopla.database;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;


//out class now can be table of greendao
@Entity (nameInDb = "duty_tbl")
public class DutyInf{

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long dutyId;             // out unique key for farms
    private long user;
    private long farm_id;
    private boolean done;

    @Generated(hash = 2100249364)
    public DutyInf(Long dutyId, long user, long farm_id, boolean done) {
        this.dutyId = dutyId;
        this.user = user;
        this.farm_id = farm_id;
        this.done = done;
    }
    @Generated(hash = 1782686538)
    public DutyInf() {
    }
    @Override
    public String toString() {
        return "Görev ID: " + getDutyId();
    }
    public Long getDutyId() {
        return this.dutyId;
    }
    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }
    public long getUser() {
        return this.user;
    }
    public void setUser(long user) {
        this.user = user;
    }
    public long getFarm_id() {
        return this.farm_id;
    }
    public void setFarm_id(long farm_id) {
        this.farm_id = farm_id;
    }
    public boolean getDone() {
        return this.done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }
}
