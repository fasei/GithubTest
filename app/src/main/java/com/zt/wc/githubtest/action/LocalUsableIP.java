package com.zt.wc.githubtest.action;

import android.text.TextUtils;
import android.util.Log;

import com.zt.wc.collectcrashinfo.utils.TimeUtils;
import com.zt.wc.githubtest.base.BaseLoopTimeTask;
import com.zt.wc.githubtest.bean.IPInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 王超 on 2017/1/9.
 */

public class LocalUsableIP extends BaseLoopTimeTask{
    private static final String TAG = "LocalUsableIP";
private Map<String,IPInfo> mIPInfoMap=new ConcurrentHashMap<>();
    @Override
    protected void onPrepare() {
        super.onPrepare();
        setmBaseTime(500);
    }

    @Override
    protected void onLoopTimeTask() {
        Iterator<Map.Entry<String, IPInfo>> iterator = mIPInfoMap.entrySet().iterator();
        long nowTime=TimeUtils.getNowTime();
        while(iterator.hasNext()){
            Map.Entry<String, IPInfo> next = iterator.next();
            if(nowTime-next.getValue().getTime()>=1000*5){
                Log.d(TAG, "onLoopTimeTask: remove"+next.getValue().toString());
                iterator.remove();
            }
        }
    }

    @Override
    protected void onFinish() {
        mIPInfoMap.clear();
    }

    public void add(String read){
        if(TextUtils.isEmpty(read)){
            return;
        }
        String spliteStr[]=read.split("-");
        if(spliteStr==null||spliteStr.length<3){
            return;
        }
        if(!spliteStr[0].equals("ZTZH")){
            return;
        }
        IPInfo mIPInfo=new IPInfo(spliteStr[2],spliteStr[1], TimeUtils.getNowTime());
        mIPInfoMap.put(mIPInfo.getId(),mIPInfo);
    }

    public Map<String,IPInfo> getAll(){
        Map<String,IPInfo> tempMap=new HashMap<>(mIPInfoMap);
        Log.d(TAG, "getAll: "+tempMap.size());
        return tempMap;
    }
}
