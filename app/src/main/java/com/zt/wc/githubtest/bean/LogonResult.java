package com.zt.wc.githubtest.bean;

/**
 * Created by 王超 on 2016/12/6.
 */

public class LogonResult {


    /**
     * ecode : 0
     * emsg : 成功
     * access_token : fe294524-1dd8-4b8d-9d6f-8e599e1e03a7
     * expires_in : 172800
     * refresh_token : 1c5a3163-83f9-4aa5-a441-cb3da8f8c752
     * openid : 2f489c27-0e6e-40e9-aba1-d19d1ec06c39
     * scope : SCOPE,
     */

    private int ecode;
    private String emsg="";
    private String access_token="";
    private int expires_in;
    private String refresh_token="";
    private String openid="";
    private String scope="";

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public String getEmsg() {
        return emsg;
    }

    public void setEmsg(String emsg) {
        this.emsg = emsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "LogonResult{" +
                "ecode=" + ecode +
                ", emsg='" + emsg + '\'' +
                ", access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
