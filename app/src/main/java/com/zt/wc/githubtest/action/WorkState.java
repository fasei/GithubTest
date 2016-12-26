package com.zt.wc.githubtest.action;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by 王超 on 2016/12/24.
 */

public class WorkState implements Runnable {
    private static final String TAG = "WorkState";
    public static final int Color_R=0;
    public static final int Color_B=1;
    public static final int Color_000=2; //黑色

    private  int frameTime=1000/20; //刷新帧数

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
            default: {

            }
        }
    }

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

//        for(int i=0;i<frame;i++){
//            if(type!=IOControl.NoMac){
//                return;
//            }
//            if(i<frame/2){
//                mControlColor.controlColor(Color_B);
//            }else{
//                mControlColor.controlColor(Color_000);
//            }
//            sleepInFrame();
//        }

    }

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
            Log.d(TAG, "setType: lastType:"+lastType+",type:"+type);
        }
            this.type = type;
        Log.d(TAG, "setType: "+type);
    }

    public void close() {
        setRunning(false);
    }
}
