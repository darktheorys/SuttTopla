package com.brkomrs.sttopla.data;

import java.io.Serializable;

public class DutyInf  implements Serializable {
    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DutyInf(String farmName, int farmId, String date) {
        this.farmName = farmName;
        this.farmId = farmId;
        this.date = date;
    }

    @Override
    public String toString() {
        return farmName;
    }

    private String farmName;
    private int farmId;
    private String date;

}
