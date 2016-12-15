package com.zt.wc.collectcrashinfo;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;

import com.zt.wc.collectcrashinfo.base.ActivityStack;
import com.zt.wc.collectcrashinfo.bean.ErrorMessage;
import com.zt.wc.collectcrashinfo.bean.PhoneInfo;
import com.zt.wc.collectcrashinfo.type.CollectType;
import com.zt.wc.collectcrashinfo.utils.ExecptionUtils;
import com.zt.wc.collectcrashinfo.utils.LocalFileUtils;
import com.zt.wc.collectcrashinfo.utils.PhoneUtils;
import com.zt.wc.collectcrashinfo.utils.ToastUtils;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

/** 自定义异常消息的处理机制
 * Created by 王超 on 2016/12/14.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler = new CrashHandler();
    private Context mContext;
    private CollectType mCollectType = CollectType.LocalLog;
    private String mFileName="logInfo/default.log";

    public static CrashHandler getInstance() {
        return mCrashHandler;
    }

    /**
     * 初始化，必须进行
     * @param context
     * @return
     */
    public CrashHandler init(Context context) {
        this.mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mCrashHandler;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        toastMsg("很抱歉,程序出现异常,即将退出.");
        String mErorMsg=toErrorStr(e);
        switch (mCollectType) {
            case LocalLog: {
                try {
                    LocalFileUtils.judgeToLocalFile(mFileName,mErorMsg);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            break;
            case Http: {
            }
            break;
            case Email: {
            }
            break;
            case Https: {

            }
            break;
            default: {

            }
        }
        SystemClock.sleep(2000);
        ActivityStack.getInstance().finishAllActivity();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 获取需要存储的内容，可以定义
     * @param ex
     * @return
     */
    private String toErrorStr(Throwable ex){
        String exceptionMsg = ExecptionUtils.getExceptionInfo(ex);//报警消息
        PhoneInfo phoneInfo = PhoneUtils.getPhoneInfo(mContext);//手机基本信息
        String msg=new ErrorMessage(phoneInfo,exceptionMsg).toErrirMessage();
        return msg;
    }
    private void toastMsg(final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showText(mContext,msg);
                Looper.loop();
            }
        }).start();
    }


    public CrashHandler setmCollectType(CollectType mCollectType) {
        this.mCollectType = mCollectType;
        return getInstance();
    }

    /**
     * 设置log保存路径 like("log/me.log")
     * @param mFilePath 保存路径，本地自动判断，优先写入SDCard,然后写入内置存储卡
     * @return
     */
    public CrashHandler setLogPath(String mFilePath) {
        this.mFileName = mFilePath;
        return getInstance();
    }

    /**
     * 设置最大缓存值
     * @param size
     * @return
     */
    public CrashHandler setMaxSize(int size){
        if(size>0){
            LocalFileUtils.maxSize=size;
        }
        return getInstance();
    }
}
