package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity (nameInDb = "dbo.Milks")
public class MilkInf {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long Id;

    private long TankId;
    private int TankFilled;

    private String MilkType;
    private int Liter;
    private boolean LeaveMilk;
    private boolean AntibioticInf;
    private double Temp;
    private double RTemp;
    private boolean AlcoholInf;
    private String AlcoholType;
    private String Comment;
    private boolean Sync;

    private int IsTankClean;
    private int IsPumpClean;
    private int IsEnvClean;
    private int IsWeighterClean;


    @Generated(hash = 1621190220)
    public MilkInf(Long Id, long TankId, int TankFilled, String MilkType, int Liter,
            boolean LeaveMilk, boolean AntibioticInf, double Temp, double RTemp,
            boolean AlcoholInf, String AlcoholType, String Comment, boolean Sync,
            int IsTankClean, int IsPumpClean, int IsEnvClean, int IsWeighterClean) {
        this.Id = Id;
        this.TankId = TankId;
        this.TankFilled = TankFilled;
        this.MilkType = MilkType;
        this.Liter = Liter;
        this.LeaveMilk = LeaveMilk;
        this.AntibioticInf = AntibioticInf;
        this.Temp = Temp;
        this.RTemp = RTemp;
        this.AlcoholInf = AlcoholInf;
        this.AlcoholType = AlcoholType;
        this.Comment = Comment;
        this.Sync = Sync;
        this.IsTankClean = IsTankClean;
        this.IsPumpClean = IsPumpClean;
        this.IsEnvClean = IsEnvClean;
        this.IsWeighterClean = IsWeighterClean;
    }


    @Generated(hash = 1049425218)
    public MilkInf() {
    }


    @Override
    public String toString() {
        return
                "Tank=" + TankFilled +
                ", Süt Tipi='" + MilkType + '\'' +
                ", Miktar=" + Liter +
                ", Bıraktım =" + (LeaveMilk?"E":"H") +
                ", İlaç=" + (AntibioticInf?"E":"H") +
                ", Sıcaklık=" + Temp +
                ", RSıcaklık=" + RTemp +
                ", Alkol=" + (AlcoholInf?"E":"H");
    }


    public Long getId() {
        return this.Id;
    }


    public void setId(Long Id) {
        this.Id = Id;
    }


    public long getTankId() {
        return this.TankId;
    }


    public void setTankId(long TankId) {
        this.TankId = TankId;
    }


    public int getTankFilled() {
        return this.TankFilled;
    }


    public void setTankFilled(int TankFilled) {
        this.TankFilled = TankFilled;
    }


    public String getMilkType() {
        return this.MilkType;
    }


    public void setMilkType(String MilkType) {
        this.MilkType = MilkType;
    }


    public int getLiter() {
        return this.Liter;
    }


    public void setLiter(int Liter) {
        this.Liter = Liter;
    }


    public boolean getLeaveMilk() {
        return this.LeaveMilk;
    }


    public void setLeaveMilk(boolean LeaveMilk) {
        this.LeaveMilk = LeaveMilk;
    }


    public boolean getAntibioticInf() {
        return this.AntibioticInf;
    }


    public void setAntibioticInf(boolean AntibioticInf) {
        this.AntibioticInf = AntibioticInf;
    }


    public double getTemp() {
        return this.Temp;
    }


    public void setTemp(double Temp) {
        this.Temp = Temp;
    }


    public double getRTemp() {
        return this.RTemp;
    }


    public void setRTemp(double RTemp) {
        this.RTemp = RTemp;
    }


    public boolean getAlcoholInf() {
        return this.AlcoholInf;
    }


    public void setAlcoholInf(boolean AlcoholInf) {
        this.AlcoholInf = AlcoholInf;
    }


    public String getAlcoholType() {
        return this.AlcoholType;
    }


    public void setAlcoholType(String AlcoholType) {
        this.AlcoholType = AlcoholType;
    }


    public String getComment() {
        return this.Comment;
    }


    public void setComment(String Comment) {
        this.Comment = Comment;
    }


    public boolean getSync() {
        return this.Sync;
    }


    public void setSync(boolean Sync) {
        this.Sync = Sync;
    }


    public int getIsTankClean() {
        return this.IsTankClean;
    }


    public void setIsTankClean(int IsTankClean) {
        this.IsTankClean = IsTankClean;
    }


    public int getIsPumpClean() {
        return this.IsPumpClean;
    }


    public void setIsPumpClean(int IsPumpClean) {
        this.IsPumpClean = IsPumpClean;
    }


    public int getIsEnvClean() {
        return this.IsEnvClean;
    }


    public void setIsEnvClean(int IsEnvClean) {
        this.IsEnvClean = IsEnvClean;
    }


    public int getIsWeighterClean() {
        return this.IsWeighterClean;
    }


    public void setIsWeighterClean(int IsWeighterClean) {
        this.IsWeighterClean = IsWeighterClean;
    }
}
