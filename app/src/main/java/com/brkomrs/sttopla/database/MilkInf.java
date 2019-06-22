package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "sut_tbl")
public class MilkInf {
    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long milkId;

    private long farm = 0;
    private long tank_id= 0;
    private int tankN = 0;
    private long duty= 0;
    private String milktype = "-";
    private int tank_liter = 0;
    private boolean leave_milk = false;
    private boolean antibiotic_inf = false;
    private double temp = 0;
    private double r_temp = 0;
    private boolean alcohol = false;
    private String alcoholtype = "-";
    private String comment = "-";

    @Generated(hash = 1757266661)
    public MilkInf(Long milkId, long farm, long tank_id, int tankN, long duty, String milktype, int tank_liter,
            boolean leave_milk, boolean antibiotic_inf, double temp, double r_temp, boolean alcohol,
            String alcoholtype, String comment) {
        this.milkId = milkId;
        this.farm = farm;
        this.tank_id = tank_id;
        this.tankN = tankN;
        this.duty = duty;
        this.milktype = milktype;
        this.tank_liter = tank_liter;
        this.leave_milk = leave_milk;
        this.antibiotic_inf = antibiotic_inf;
        this.temp = temp;
        this.r_temp = r_temp;
        this.alcohol = alcohol;
        this.alcoholtype = alcoholtype;
        this.comment = comment;
    }

    @Generated(hash = 1049425218)
    public MilkInf() {
    }
    
    @Override
    public String toString() {
        return "Çiftlik: " + getFarm() + "- Süt: "+ getMilktype() + "- Miktar: " + getTank_liter() + "- Aldım: " + (getLeave_milk()?"Hayır":"Evet") + "- Tank: " + getTankN();
    }

    public Long getMilkId() {
        return this.milkId;
    }

    public void setMilkId(Long milkId) {
        this.milkId = milkId;
    }

    public long getFarm() {
        return this.farm;
    }

    public void setFarm(long farm) {
        this.farm = farm;
    }

    public long getTank_id() {
        return this.tank_id;
    }

    public void setTank_id(long tank_id) {
        this.tank_id = tank_id;
    }

    public int getTankN() {
        return this.tankN;
    }

    public void setTankN(int tankN) {
        this.tankN = tankN;
    }

    public long getDuty() {
        return this.duty;
    }

    public void setDuty(long duty) {
        this.duty = duty;
    }

    public String getMilktype() {
        return this.milktype;
    }

    public void setMilktype(String milktype) {
        this.milktype = milktype;
    }

    public int getTank_liter() {
        return this.tank_liter;
    }

    public void setTank_liter(int tank_liter) {
        this.tank_liter = tank_liter;
    }

    public boolean getLeave_milk() {
        return this.leave_milk;
    }

    public void setLeave_milk(boolean leave_milk) {
        this.leave_milk = leave_milk;
    }

    public boolean getAntibiotic_inf() {
        return this.antibiotic_inf;
    }

    public void setAntibiotic_inf(boolean antibiotic_inf) {
        this.antibiotic_inf = antibiotic_inf;
    }

    public double getTemp() {
        return this.temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getR_temp() {
        return this.r_temp;
    }

    public void setR_temp(double r_temp) {
        this.r_temp = r_temp;
    }

    public boolean getAlcohol() {
        return this.alcohol;
    }

    public void setAlcohol(boolean alcohol) {
        this.alcohol = alcohol;
    }

    public String getAlcoholtype() {
        return this.alcoholtype;
    }

    public void setAlcoholtype(String alcoholtype) {
        this.alcoholtype = alcoholtype;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
