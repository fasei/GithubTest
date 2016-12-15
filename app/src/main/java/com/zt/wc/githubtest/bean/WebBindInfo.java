package com.zt.wc.githubtest.bean;

/**
 * Created by 王超 on 2016/12/8.
 */

public class WebBindInfo {
    private String OpenId;
    private String ClientId;
    private String Token;

    public WebBindInfo() {
    }

    public WebBindInfo(String openId, String clientId, String token) {
        OpenId = openId;
        ClientId = clientId;
        Token = token;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String cliendId) {
        ClientId = cliendId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @Override
    public String toString() {
        return "WebBindInfo{" +
                "OpenId='" + OpenId + '\'' +
                ", CliendId='" + ClientId + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }
}
