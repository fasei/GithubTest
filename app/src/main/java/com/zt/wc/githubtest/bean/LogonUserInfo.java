package com.zt.wc.githubtest.bean;

/**
 * Created by 王超 on 2016/12/7.
 */

public class LogonUserInfo {


    /**
     * ClientId : 13693054056
     * Password : 555555**************
     */

    private String ClientId;
    private String Password;

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String toString() {
        return "LogonUserInfo{" +
                "ClientId='" + ClientId + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
