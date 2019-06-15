package com.brkomrs.sttopla.data;

import com.brkomrs.sttopla.data.TankInf;

public class TruckInf {
    private String id ;
    private String plate;
    private int n_tank;
    private TankInf[] tanks;




    public TruckInf() {
    }

    public TruckInf(String id ,String plate, int n) {
        this.id = id;
        this.plate = plate;
        this.n_tank = n;
        this.tanks = new TankInf[n];
    }

    public TankInf[] getTanks() {
        return tanks;
    }

    public void setTanks(TankInf[] tanks) {
        this.tanks = tanks;
    }


    public String getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public int getN_tank() {
        return n_tank;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setN_tank(int n_tank) {
        this.n_tank = n_tank;
    }


}
