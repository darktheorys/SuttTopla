package com.brkomrs.sttopla.data;

public class MilkInf {

    public MilkInf(int liter, String milktype, boolean antibiotic_inf, int temp, int r_temp, int alcohol) {
        this.liter = liter;
        this.milktype = milktype;
        this.antibiotic_inf = antibiotic_inf;
        this.temp = temp;
        this.r_temp = r_temp;
        this.alcohol = alcohol;
    }

    public int getLiter() {
        return liter;
    }

    public String getMilktype() {
        return milktype;
    }

    public boolean isAntibiotic_inf() {
        return antibiotic_inf;
    }

    public int getTemp() {
        return temp;
    }

    public int getR_temp() {
        return r_temp;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public void setLiter(int liter) {
        this.liter = liter;
    }

    public void setMilktype(String milktype) {
        this.milktype = milktype;
    }

    public void setAntibiotic_inf(boolean antibiotic_inf) {
        this.antibiotic_inf = antibiotic_inf;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setR_temp(int r_temp) {
        this.r_temp = r_temp;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    private int liter;
    private String milktype;
    private boolean antibiotic_inf;
    private int temp;
    private int r_temp;
    private int alcohol;

}
