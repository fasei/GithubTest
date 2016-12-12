package com.zt.wc.githubtest.dao;

import com.zt.wc.githubtest.App;
import com.zt.wc.githubtest.greendao.gen.DaoMaster;
import com.zt.wc.githubtest.greendao.gen.DaoSession;

/**GreenDao的管理类，相关的内容
 * 一个DaoMaster就代表着一个数据库的连接；
 * DaoSession可以让我们使用一些Entity的基本操作和获取Dao操作类，
 * DaoSession可以创建多个，
 * 每一个都是属于同一个数据库连接的。
 * Created by 王超 on 2016/12/12.
 */

public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    public GreenDaoManager() {
         DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.gainContext(), "node_db", null);
        mDaoMaster=new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession=mDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance(){
        if(mInstance==null){
            mInstance=new GreenDaoManager();
        }
        return  mInstance;
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }
    public DaoSession getmNewDaoSession() {
        mDaoSession=mDaoMaster.newSession();
        return mDaoSession;
    }



}
