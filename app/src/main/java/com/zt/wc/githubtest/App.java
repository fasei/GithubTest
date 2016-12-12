package com.zt.wc.githubtest;

import android.app.Application;
import android.content.Context;

import com.zt.wc.githubtest.dao.GreenDaoManager;

/**
 * Created by 王超 on 2016/12/12.
 */

public class App extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        GreenDaoManager.getInstance();  //首先初始化数据库内容
    }

    /**
     * 全局上下文关系
     * @return
     */
    public static Context gainContext(){
        return mContext;
    }

}
