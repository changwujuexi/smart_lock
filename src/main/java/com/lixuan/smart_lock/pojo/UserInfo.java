package com.lixuan.smart_lock.pojo;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NotNull
public class UserInfo {

    @NotEmpty(message = "用户名为空")
    private String userName;

    @NotEmpty(message = "密码为空")
    private String password;

    private String tel;

    @NotEmpty(message = "姓名为空")
    private String name;

    private String idNumber;


    public UserInfo(@NotEmpty(message = "用户名为空") String userName,
                    @NotEmpty(message = "密码为空") String password,
                    String tel,
                    @NotEmpty(message = "姓名为空") String name,
                    String idNumber) {
        this.userName = userName;
        this.password = password;
        this.tel = tel;
        this.name = name;
        this.idNumber = idNumber;
    }

    public UserInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }
}
