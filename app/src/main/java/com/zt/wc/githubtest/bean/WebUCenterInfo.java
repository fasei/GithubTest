package com.zt.wc.githubtest.bean;

/**
 * Created by 王超 on 2016/12/8.
 */

public class WebUCenterInfo {
    private String UserName;
    private String Password;
    private String Email;

    public WebUCenterInfo() {
    }

    public WebUCenterInfo(String userName, String password, String email) {
        UserName = userName;
        Password = password;
        Email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "WebUCenterInfo{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
