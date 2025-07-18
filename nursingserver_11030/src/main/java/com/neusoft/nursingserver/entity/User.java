package com.neusoft.nursingserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user")
public class User {
    @TableId(value="user_id", type=IdType.AUTO)
    private Integer userId;

    private String account; //账号，也可以认为是用户名

    private String password;

    private String name; //真实姓名

    private String phoneNumber;

    private Integer gender; // 0-女性  1-男性

    private String email;

    private Integer userType; // 0-管理员  1-护工

    public User() {}

    public User(int userId, String account, String password, String name, String phoneNumber, int gender, String email, int userType) {
        this.userId = userId;
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.userType = userType;
    }

//    public User(UserView userView) {
//        this.userId = userView.getUserId();
//        this.account = userView.getAccount();
//        this.password = "";
//        this.name = userView.getName();
//        this.phoneNumber = userView.getPhoneNumber();
//        this.gender = userView.getGender();
//        this.email = userView.getEmail();
//        this.userType = userView.getUserType();
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                '}';
    }
}
