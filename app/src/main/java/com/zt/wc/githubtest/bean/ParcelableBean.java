package com.zt.wc.githubtest.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王超 on 2017/1/13.
 */

public class ParcelableBean implements Parcelable {

    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel in) {
            return new ParcelableBean(in);
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };
    private String mName;
    private String mClass;
    private boolean mSex;
    private int mAge;

    protected ParcelableBean(Parcel in) {
        mName = in.readString();
        mClass = in.readString();
        mSex = in.readByte() != 0;
        mAge = in.readInt();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public boolean ismSex() {
        return mSex;
    }

    public void setmSex(boolean mSex) {
        this.mSex = mSex;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mClass);
        dest.writeByte((byte) (mSex ? 1 : 0));
        dest.writeInt(mAge);
    }
}
