package com.lixuan.smart_lock.pojo;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NotNull
public class HouseInfo {

    @NotEmpty(message = "地址为空")
    private String addr;

    @NotEmpty(message = "锁权限为空")
    private String key;

    @NotEmpty(message = "锁为空")
    private String keyName;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HouseInfo(@NotEmpty(message = "地址为空") String addr, @NotEmpty(message = "锁权限为空") String key, @NotEmpty(message = "锁为空") String keyName) {
        this.addr = addr;
        this.key = key;
        this.keyName = keyName;
    }

    public HouseInfo() {
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String toString() {
        return "HouseInfo{" +
                "addr='" + addr + '\'' +
                ", key='" + key + '\'' +
                ", keyName='" + keyName + '\'' +
                '}';
    }
}
