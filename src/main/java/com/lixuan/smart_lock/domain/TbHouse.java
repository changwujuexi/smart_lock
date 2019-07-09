package com.lixuan.smart_lock.domain;

import javax.persistence.*;

@Entity
public class TbHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String addr;

    String keyNumber;

    String keyName;

    //0可出租，1不可出租
    @Column(length = 1)
    String rent;

    //屋主
    Integer ownerId;

    //现在的住户
    Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public TbHouse() {
    }

    public TbHouse(String addr, String key, String keyName, String rent, Integer ownerId, Integer userId) {
        this.addr = addr;
        this.keyNumber = key;
        this.keyName = keyName;
        this.rent = rent;
        this.ownerId = ownerId;
        this.userId = userId;
    }
}
