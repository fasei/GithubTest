package com.zt.wc.collectcrashinfo.bean;

/**
 * Created by 王超 on 2016/12/15.
 */

public class PhoneInfo {
    private String mType;//手机型号
    private String mTypeBranch;//手机品牌
    private String mSdkVersion;//SDK版本
    private String mSystemVersion;//系统版本
    private String mAppVersion; //App版本信息
    private String mAppVersionName;//App版本名称
    private String mPackage;//App包名

    public PhoneInfo() {
    }

    public PhoneInfo(String mType, String mTypeBranch, String mSdkVersion, String mSystemVersion, String mAppVersion, String mAppVersionName, String mPackage) {
        this.mType = mType;
        this.mTypeBranch = mTypeBranch;
        this.mSdkVersion = mSdkVersion;
        this.mSystemVersion = mSystemVersion;
        this.mAppVersion = mAppVersion;
        this.mAppVersionName = mAppVersionName;
        this.mPackage = mPackage;
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

    public String getmSdkVersion() {
        return mSdkVersion;
    }

    public void setmSdkVersion(String mSdkVersion) {
        this.mSdkVersion = mSdkVersion;
    }

    public String getmSystemVersion() {
        return mSystemVersion;
    }

    public void setmSystemVersion(String mSystemVersion) {
        this.mSystemVersion = mSystemVersion;
    }

    public String getmAppVersion() {
        return mAppVersion;
    }

    public void setmAppVersion(String mAppVersion) {
        this.mAppVersion = mAppVersion;
    }

    public String getmPackage() {
        return mPackage;
    }

    public void setmPackage(String mPackage) {
        this.mPackage = mPackage;
    }

    public String getmAppVersionName() {
        return mAppVersionName;
    }

    public void setmAppVersionName(String mAppVersionName) {
        this.mAppVersionName = mAppVersionName;
    }
    public String format(){
        StringBuffer mBuffer=new StringBuffer();
        mBuffer.append("/********Phone TypeBranch:"+mTypeBranch+"********/\n");
        mBuffer.append("/********Phone Type:"+mType+"********/\n");
        mBuffer.append("/********Phone SdkVersion:"+mSdkVersion+"********/\n");
        mBuffer.append("/********Phone SystemVersion:"+mSystemVersion+"********/\n");
        mBuffer.append("/********App Package:"+mPackage+"********/\n");
        mBuffer.append("/********App VersionName:"+mAppVersionName+"********/\n");
        mBuffer.append("/********App Version:"+mAppVersion+"********/\n");
        return mBuffer.toString();
    }

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "mType='" + mType + '\'' +
                ", mTypeBranch='" + mTypeBranch + '\'' +
                ", mSdkVersion='" + mSdkVersion + '\'' +
                ", mSystemVersion='" + mSystemVersion + '\'' +
                ", mAppVersion='" + mAppVersion + '\'' +
                ", mAppVersionName='" + mAppVersionName + '\'' +
                ", mPackage='" + mPackage + '\'' +
                '}';
    }
}
