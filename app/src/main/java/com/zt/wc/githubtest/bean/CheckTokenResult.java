package com.zt.wc.githubtest.bean;

/**
 * Created by 王超 on 2016/12/13.
 */

public class CheckTokenResult {
    private int ecode;
    private String emsg;

    public CheckTokenResult() {
    }

    public CheckTokenResult(int ecode, String emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

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

    @Override
    public String toString() {
        return "CheckTokenResult{" +
                "ecode=" + ecode +
                ", emsg='" + emsg + '\'' +
                '}';
    }
}
