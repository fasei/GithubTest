package com.zt.wc.githubtest.bean;

/**
 * Created by 王超 on 2017/1/9.
 */
public class IPInfo {
    private String id;
    private String ip;
    private long time;

    public IPInfo() {
    }

    public IPInfo(String id, String ip, long time) {
        this.id = id;
        this.ip = ip;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "IPInfo{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", time=" + time +
                '}';
    }
}
