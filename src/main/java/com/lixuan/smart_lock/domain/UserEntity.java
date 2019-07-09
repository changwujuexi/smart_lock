package com.lixuan.smart_lock.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String identity;



    private boolean rent;

    private boolean proxy;

    private boolean applyRent;

    private boolean applyProxy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public boolean isRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    public boolean isApplyRent() {
        return applyRent;
    }

    public void setApplyRent(boolean applyRent) {
        this.applyRent = applyRent;
    }

    public boolean isApplyProxy() {
        return applyProxy;
    }

    public void setApplyProxy(boolean applyProxy) {
        this.applyProxy = applyProxy;
    }
}
