package com.brkomrs.sttopla.data;

public class FarmInf {
    private boolean isTankClean;
    private boolean isFarmClean;
    private boolean isEnvClean;
    private boolean isMilkClean;
    private String farmName;
    private int farmID;


    public FarmInf(boolean isTankClean, boolean isFarmClean, boolean isEnvClean, boolean isMilkClean, String farmName, int farmID) {
        this.isTankClean = isTankClean;
        this.isFarmClean = isFarmClean;
        this.isEnvClean = isEnvClean;
        this.isMilkClean = isMilkClean;
        this.farmName = farmName;
        this.farmID = farmID;
    }

    public boolean isTankClean() {
        return isTankClean;
    }

    public void setTankClean(boolean tankClean) {
        isTankClean = tankClean;
    }

    public boolean isFarmClean() {
        return isFarmClean;
    }

    public void setFarmClean(boolean farmClean) {
        isFarmClean = farmClean;
    }

    public boolean isEnvClean() {
        return isEnvClean;
    }

    public void setEnvClean(boolean envClean) {
        isEnvClean = envClean;
    }

    public boolean isMilkClean() {
        return isMilkClean;
    }

    public void setMilkClean(boolean milkClean) {
        isMilkClean = milkClean;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public int getFarmID() {
        return farmID;
    }

    public void setFarmID(int farmID) {
        this.farmID = farmID;
    }




}
