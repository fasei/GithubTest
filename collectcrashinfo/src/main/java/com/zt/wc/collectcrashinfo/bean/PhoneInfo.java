package com.zt.wc.collectcrashinfo.bean;

/**
 * Created by 王超 on 2016/12/15.
 */

public class PhoneInfo {
    private String mType;//型号
    private String mTypeBranch;//品牌

    public PhoneInfo() {
    }

    public PhoneInfo(String mType, String mTypeBranch) {
        this.mType = mType;
        this.mTypeBranch = mTypeBranch;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmTypeBranch() {
        return mTypeBranch;
    }

    public void setmTypeBranch(String mTypeBranch) {
        this.mTypeBranch = mTypeBranch;
    }

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "mType='" + mType + '\'' +
                ", mTypeBranch='" + mTypeBranch + '\'' +
                '}';
    }
}
