package com.zt.wc.githubtest.action;

import android.os.Handler;
import android.util.Log;

/**
 * Created by 王超 on 2016/12/24.
 */

public class IOControl implements IControlColor {
    private static final String TAG = "IOControl";

    public static final int Normal = 0;//正常状态
    public static final int Search = 1;//查询设备
    public static final int NoMac = 2;//没有获取到MAC
    public static final int LoginError = 3;//登录失败
    public static final int UserCMD = 4;//用户控制
    public static final int Reboot=5;//重启模块

    private static IOControl mIOControl;
    private Handler mHandler;
    private WorkState mWorkState;

    public IOControl() {
        initData();
    }

    public static IOControl getInstance() {
        synchronized (IOControl.class) {
            if (mIOControl == null) {
                mIOControl = new IOControl();
            }
        }
        return mIOControl;
    }

    private void initData() {
        this.mWorkState = new WorkState(this);
        mWorkState.setType(Normal);//默认状态
    }

    /**
     * 开始运行控制效果
     */
    public void onCreate() {
        if (!mWorkState.isRunning()) {
            new Thread(mWorkState).start();
        }
    }


    public IOControl setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
        return this;
    }

    public int getType() {
        return mWorkState.getType();
    }

    public void setType(int type) {
        mWorkState.setType(type);
    }

    @Override
    public void controlColor(int color) {
        Log.d(TAG, "controlColor:color: "+color);
        if (mHandler != null) {
            mHandler.sendEmptyMessage(color);
        }
    }

    /**
     * 结束控制
     */
    public void onDestroy() {
        if (mWorkState != null && mWorkState.isRunning()) {
            mWorkState.close();
        }
    }

}
