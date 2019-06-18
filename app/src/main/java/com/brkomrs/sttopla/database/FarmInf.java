package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "ciftlik_tbl")
public class FarmInf {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long farmID;

    private boolean isTankClean;
    private boolean isPumpClean;
    private boolean isEnvClean;
    private boolean isWeighterClean;
    private String farmName;
    @Generated(hash = 1895313793)
    public FarmInf(Long farmID, boolean isTankClean, boolean isPumpClean,
            boolean isEnvClean, boolean isWeighterClean, String farmName) {
        this.farmID = farmID;
        this.isTankClean = isTankClean;
        this.isPumpClean = isPumpClean;
        this.isEnvClean = isEnvClean;
        this.isWeighterClean = isWeighterClean;
        this.farmName = farmName;
    }
    @Generated(hash = 396617878)
    public FarmInf() {
    }
    public Long getFarmID() {
        return this.farmID;
    }
    public void setFarmID(Long farmID) {
        this.farmID = farmID;
    }
    public boolean getIsTankClean() {
        return this.isTankClean;
    }
    public void setIsTankClean(boolean isTankClean) {
        this.isTankClean = isTankClean;
    }
    public boolean getIsPumpClean() {
        return this.isPumpClean;
    }
    public void setIsPumpClean(boolean isPumpClean) {
        this.isPumpClean = isPumpClean;
    }
    public boolean getIsEnvClean() {
        return this.isEnvClean;
    }
    public void setIsEnvClean(boolean isEnvClean) {
        this.isEnvClean = isEnvClean;
    }
    public boolean getIsWeighterClean() {
        return this.isWeighterClean;
    }
    public void setIsWeighterClean(boolean isWeighterClean) {
        this.isWeighterClean = isWeighterClean;
    }
    public String getFarmName() {
        return this.farmName;
    }
    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }


}
