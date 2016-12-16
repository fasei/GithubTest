package com.zt.wc.collectcrashinfo.bean;

import com.zt.wc.collectcrashinfo.bean.PhoneInfo;
import com.zt.wc.collectcrashinfo.utils.TimeUtils;

/**
 * Created by 王超 on 2016/12/15.
 */

public class ErrorMessage {
    private PhoneInfo mPhoneInfo;
    private String mErrorMessage;

    public ErrorMessage(PhoneInfo mPhoneInfo, String mErrorMessage) {
        this.mPhoneInfo = mPhoneInfo;
        this.mErrorMessage = mErrorMessage;
    }

    public PhoneInfo getmPhoneInfo() {
        return mPhoneInfo;
    }

    public void setmPhoneInfo(PhoneInfo mPhoneInfo) {
        this.mPhoneInfo = mPhoneInfo;
    }

    public String getmErrorMessage() {
        return mErrorMessage;
    }

    public void setmErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }

    public String toErrirMessage() {
        StringBuffer mBuffer=new StringBuffer();
        mBuffer.append("/**************************Golog***************************/\n");
        mBuffer.append("/********Time:"+ TimeUtils.getTime()+"\n");
        if(mPhoneInfo!=null){
            mBuffer.append(mPhoneInfo.format());
        }
        mBuffer.append(mErrorMessage+"\n");
        mBuffer.append("/**************************Endlog**************************/");
        mBuffer.append("\r\n\r\n\r\n");
       return mBuffer.toString();
    }
}
