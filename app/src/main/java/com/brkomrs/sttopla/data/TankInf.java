package com.brkomrs.sttopla.data;

public class TankInf {
    public TankInf(int tankN, int limit, int fullness, MilkInf contains) {
        this.tankN = tankN;
        this.limit = limit;
        this.fullness = fullness;
        this.contains = contains;
    }

    public int getTankN() {
        return tankN;
    }

    public void setTankN(int tankN) {
        this.tankN = tankN;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getFullness() {
        return fullness;
    }

    public void setFullness(int fullness) {
        this.fullness = fullness;
    }

    public MilkInf getContains() {
        return contains;
    }

    public void setContains(MilkInf contains) {
        this.contains = contains;
    }

    private int tankN;
    private int limit;
    private int fullness;
    private MilkInf contains;



}
