package com.zt.wc.collectcrashinfo;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.zt.wc.collectcrashinfo.base.ActivityStack;
import com.zt.wc.collectcrashinfo.base.DefaultCrashHandler;
import com.zt.wc.collectcrashinfo.bean.ErrorMessage;
import com.zt.wc.collectcrashinfo.bean.MailSenderInfo;
import com.zt.wc.collectcrashinfo.bean.PhoneInfo;
import com.zt.wc.collectcrashinfo.type.CollectType;
import com.zt.wc.collectcrashinfo.utils.EMailUtils;
import com.zt.wc.collectcrashinfo.utils.ExecptionUtils;
import com.zt.wc.collectcrashinfo.utils.LocalFileUtils;
import com.zt.wc.collectcrashinfo.utils.PhoneUtils;
import com.zt.wc.collectcrashinfo.utils.ToastUtils;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** 自定义异常消息的处理机制
 * Created by 王超 on 2016/12/14.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static CrashHandler mCrashHandler = new CrashHandler();//单例
    private Context mContext;//上下文
    private Set<CollectType> mCollectType = new HashSet<>();//处理崩溃消息的方式
    private String mFileName = "logInfo/default.log"; //默认log位置
    private CustomCrashListerner mCustomCrashListerner;//自定义处理过程
    private MailSenderInfo mMailSenderInfo = new MailSenderInfo();//默认邮箱发送设置
    private String defaultToastMsg = "很抱歉,程序出现异常,即将退出.";//默认的提示消息

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
        mCollectType.add(CollectType.LocalLog);
        return mCrashHandler;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (mCustomCrashListerner == null) {
            mCustomCrashListerner = new DefaultCrashHandler();
        }
        boolean handed = mCustomCrashListerner.handlerCustomCrash(e);
        if (!handed) {
            toastMsg(defaultToastMsg);
            final String mErorMsg = toErrorStr(e);
            Log.d(TAG, "uncaughtException: " + mErorMsg);
            Iterator<CollectType> collectTypeIterator = mCollectType.iterator();
            while (collectTypeIterator.hasNext()) {
                CollectType oneCollectType = collectTypeIterator.next();
                switch (oneCollectType) {
                    case LocalLog: {
                        try {
                            LocalFileUtils.judgeToLocalFile(mFileName, mErorMsg);
                            Log.d(TAG, "Local Write.");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                }
                    break;
                    case Http: {
                    }
                    break;
                    case Email: {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    EMailUtils.sendEMail(mMailSenderInfo, mErorMsg);
                                    Log.d(TAG, "send E-Mail.");
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    break;
                    case Https: {

                    }
                    break;
                    default: {

                    }
            }
            }

            SystemClock.sleep(3000);
            ActivityStack.getInstance().finishAllActivity();  //关闭全部Activity
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
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

    /**
     * 设置异常的处理方式
     *
     * @param mCollectType
     * @return
     */
    public CrashHandler setmCollectType(CollectType... mCollectType) {
        for (CollectType type : mCollectType) {
            this.mCollectType.add(type);
        }
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
     * 设置最大缓存值（单位：）
     * @param size
     * @return
     */
    public CrashHandler setMaxSize(int size){
        if(size>0){
            LocalFileUtils.maxSize=size;
        }
        return getInstance();
    }

    /**
     * 设置自定义处理异常的过程
     *
     * @param mCustomCrashListerner
     * @return
     */
    public CrashHandler setCustomCrashListerner(CustomCrashListerner mCustomCrashListerner) {
        this.mCustomCrashListerner = mCustomCrashListerner;
        return getInstance();
    }

    /**
     * 设置默认的提示消息
     *
     * @param toastMsg
     * @return
     */
    public CrashHandler setDefaultToastMsg(String toastMsg) {
        this.defaultToastMsg = toastMsg;
        return getInstance();
    }

    public CrashHandler setmMailSenderInfo(MailSenderInfo mailSenderInfo) {
        this.mMailSenderInfo = mailSenderInfo;
        return getInstance();
    }


    /**
     * 崩溃异常监听器
     */
    public interface CustomCrashListerner {
        /**
         * 是否处理
         *
         * @param e 异常
         * @return True：用户处理完成,此事件已经被消费；False：默认流程
         */
        public boolean handlerCustomCrash(Throwable e);
    }
}
