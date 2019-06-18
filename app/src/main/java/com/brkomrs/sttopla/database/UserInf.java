package com.brkomrs.sttopla.database;

import org.greenrobot.greendao.annotation.Entity;

import java.util.LinkedList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "kullanici_tbl")
public class UserInf {
    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long userId;
    private String userName;
    private String userSurname;
    private String userPhone;
    private String userEmail;
    @Generated(hash = 691148707)
    public UserInf(Long userId, String userName, String userSurname,
            String userPhone, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }
    @Generated(hash = 965704295)
    public UserInf() {
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserSurname() {
        return this.userSurname;
    }
    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
    public String getUserPhone() {
        return this.userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getUserEmail() {
        return this.userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


}
