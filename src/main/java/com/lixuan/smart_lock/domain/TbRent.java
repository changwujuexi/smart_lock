package com.lixuan.smart_lock.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TbRent {

    @Id
    @GeneratedValue
    Integer id;

    Integer houseId;

    //房客
    Integer tenantId;

    //屋主
    Integer ownerId;

    //0失效，1在租
    @Column(length = 1)
    String status;

    //0，1，2权限等级vip,close,normal
    @Column(length = 1)
    String power;

    //转租次数0，1，2
    @Column(length = 1)
    String sublet;

    //从哪一条TbRent转租过来的
    Integer lastId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getSublet() {
        return sublet;
    }

    public void setSublet(String sublet) {
        this.sublet = sublet;
    }

    public Integer getLastId() {
        return lastId;
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }

    public TbRent() {
    }
}
