package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "sut_tbl")
public class MilkInf {
    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long milkId;

    private long farm = 0;
    private long tank1= 0;
    private long tank2= 0;
    private long tank3= 0;
    private long tank4= 0;
    private long tank5= 0;
    private long duty= 0;
    private String milktype = "NoN";
    private int tank1_liter = 0;
    private int tank2_liter = 0;
    private int tank3_liter = 0;
    private int tank4_liter = 0;
    private int tank5_liter = 0;
    private boolean leave_milk = false;
    private boolean antibiotic_inf = false;
    private String temp = "-";
    private String r_temp ="-";
    private boolean alcohol = false;
    private String alcoholtype = "NoN";
    private String comment = "NoN";
    @Generated(hash = 330444235)
    public MilkInf(Long milkId, long farm, long tank1, long tank2, long tank3,
            long tank4, long tank5, long duty, String milktype, int tank1_liter,
            int tank2_liter, int tank3_liter, int tank4_liter, int tank5_liter,
            boolean leave_milk, boolean antibiotic_inf, String temp, String r_temp,
            boolean alcohol, String alcoholtype, String comment) {
        this.milkId = milkId;
        this.farm = farm;
        this.tank1 = tank1;
        this.tank2 = tank2;
        this.tank3 = tank3;
        this.tank4 = tank4;
        this.tank5 = tank5;
        this.duty = duty;
        this.milktype = milktype;
        this.tank1_liter = tank1_liter;
        this.tank2_liter = tank2_liter;
        this.tank3_liter = tank3_liter;
        this.tank4_liter = tank4_liter;
        this.tank5_liter = tank5_liter;
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
    public long getTank1() {
        return this.tank1;
    }
    public void setTank1(long tank1) {
        this.tank1 = tank1;
    }
    public long getTank2() {
        return this.tank2;
    }
    public void setTank2(long tank2) {
        this.tank2 = tank2;
    }
    public long getTank3() {
        return this.tank3;
    }
    public void setTank3(long tank3) {
        this.tank3 = tank3;
    }
    public long getTank4() {
        return this.tank4;
    }
    public void setTank4(long tank4) {
        this.tank4 = tank4;
    }
    public long getTank5() {
        return this.tank5;
    }
    public void setTank5(long tank5) {
        this.tank5 = tank5;
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
    public boolean getAntibiotic_inf() {
        return this.antibiotic_inf;
    }
    public void setAntibiotic_inf(boolean antibiotic_inf) {
        this.antibiotic_inf = antibiotic_inf;
    }
    public String getTemp() {
        return this.temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public String getR_temp() {
        return this.r_temp;
    }
    public void setR_temp(String r_temp) {
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
    public int getTank1_liter() {
        return this.tank1_liter;
    }
    public void setTank1_liter(int tank1_liter) {
        this.tank1_liter = tank1_liter;
    }
    public int getTank2_liter() {
        return this.tank2_liter;
    }
    public void setTank2_liter(int tank2_liter) {
        this.tank2_liter = tank2_liter;
    }
    public int getTank3_liter() {
        return this.tank3_liter;
    }
    public void setTank3_liter(int tank3_liter) {
        this.tank3_liter = tank3_liter;
    }
    public int getTank4_liter() {
        return this.tank4_liter;
    }
    public void setTank4_liter(int tank4_liter) {
        this.tank4_liter = tank4_liter;
    }
    public int getTank5_liter() {
        return this.tank5_liter;
    }
    public void setTank5_liter(int tank5_liter) {
        this.tank5_liter = tank5_liter;
    }
    public boolean getLeave_milk() {
        return this.leave_milk;
    }
    public void setLeave_milk(boolean leave_milk) {
        this.leave_milk = leave_milk;
    }
   
    

}
