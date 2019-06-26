package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity (nameInDb = "dbo.Farms")
public class FarmInf{

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long Id;

    private String FarmName;

    @Generated(hash = 1506879889)
    public FarmInf(Long Id, String FarmName) {
        this.Id = Id;
        this.FarmName = FarmName;
    }

    @Generated(hash = 396617878)
    public FarmInf() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getFarmName() {
        return this.FarmName;
    }

    public void setFarmName(String FarmName) {
        this.FarmName = FarmName;
    }


}
