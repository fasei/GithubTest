package com.zt.wc.githubtest.action;

import android.os.SystemClock;
import android.util.Log;

/**
 * IO状态控制总线
 *
 * Created by 王超 on 2016/12/24.
 */

public class WorkState implements Runnable {
    private static final String TAG = "WorkState";
    public static final int Color_R=0;//红色
    public static final int Color_B=1;//蓝色
    public static final int Color_000=2; //黑色
    public static final int Color_reboot = 3; //开始置低IO
    public static final int Color_high = 4;//重启完成开始置高

    private int frameTime = 1000 / 20; //刷新帧数IO

    private boolean isRunning = false;
    private int type;
    private int lastType; //上一次的状态
    private IControlColor mControlColor;

    public WorkState(IControlColor mControlColor) {
        this.mControlColor=mControlColor;
        type = IOControl.Normal;
        lastType=IOControl.Normal;
    }

    @Override
    public void run() {
        setRunning(true);
        while (isRunning()) {
            dispatcher();
        }
    }

    private void dispatcher() {
        switch (type) {
            case IOControl.Normal: {
                normal();
            }
            break;
            case IOControl.NoMac: {
                noMac();
            }
            break;
            case IOControl.Search: {
                search();
            }
            break;
            case IOControl.UserCMD: {
                userCMD();
                if(IOControl.UserCMD==type){
                    Log.d(TAG, "dispatcher: lastType:"+lastType);
                    type=lastType;
                }
            }
            break;
            case IOControl.LoginError: {
                loginError();
            }
            break;
            case IOControl.Reboot: {
                reboot();
            }
            break;
            default: {

            }
        }
    }

    /**
     * 重启IO部分
     */
    private void reboot() {
        int longTime = 200;//ms  0.2S
        int frame = getFrame(longTime);
        mControlColor.controlColor(Color_reboot);
        for (int i = 0; i < frame; i++) {
            sleepInFrame();
        }
        mControlColor.controlColor(Color_high);
    }

    /**
     * 正常的状态
     */
    private void normal() {
        int longTime=2000;//ms
        int frame = getFrame(longTime);
        for(int i=0;i<frame;i++){
            if(type!=IOControl.Normal){
                return;
            }
            if(i<frame/2){
                mControlColor.controlColor(Color_B);
            }else{
                mControlColor.controlColor(Color_000);
            }
            sleepInFrame();
        }
    }
    private int getFrame(int time){
        return time/frameTime;
    }
    private void sleepInFrame(){
        SystemClock.sleep(frameTime);
    }

    /**
     * 未获取MAC的状态
     */
    private void noMac() {
        int longTime=2000;//ms
        int frame = getFrame(longTime);
        for(int i=0;i<frame;i++){
            if(type!=IOControl.NoMac){
                return;
            }
            if(i<frame/2){
                mControlColor.controlColor(Color_R);
            }else{
                mControlColor.controlColor(Color_000);
            }
            sleepInFrame();
        }
    }

    /**
     * 查询设备的状态
     */
    private void search() {
        int longTime=2000;//ms
        int frame = getFrame(longTime);
        for(int i=0;i<frame;i++){
            if(type!=IOControl.Search){
                return;
            }
            if(i<frame/2){
                mControlColor.controlColor(Color_R);
            }else{
                mControlColor.controlColor(Color_B);
            }
            sleepInFrame();
        }
    }

    /**
     * 登录成功的状态
     */
    private void userCMD() {
        int longTime=400;//ms
        int frame = getFrame(longTime);
        for(int i=0;i<frame;i++){
            if(type!=IOControl.UserCMD){
                return;
            }
            if(i<frame/2){
                mControlColor.controlColor(Color_R);
            }else{
                mControlColor.controlColor(Color_000);
            }
            sleepInFrame();
        }
    }

    /**
     * 登录失败的状态
     */
    private void loginError() {
        int longTime=2000;//ms
        int frame = getFrame(longTime);
        for(int i=0;i<frame;i++){
            if(type!=IOControl.LoginError){
                return;
            }
            if(i<frame/2){
                mControlColor.controlColor(Color_R);
            }else{
                mControlColor.controlColor(Color_R);
            }
            sleepInFrame();
        }
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(int frameTime) {
        this.frameTime = 1000/frameTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if(type!=IOControl.UserCMD) {
            this.lastType = type;
        }
            this.type = type;
    }

    public void close() {
        setRunning(false);
    }
}
