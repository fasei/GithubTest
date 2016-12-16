package com.zt.wc.collectcrashinfo.base;

import com.zt.wc.collectcrashinfo.CrashHandler;

/**
 * Created by 王超 on 2016/12/16.
 */

public class DefaultCrashHandler implements CrashHandler.CustomCrashListerner {
    @Override
    public boolean handlerCustomCrash(Throwable e) {
        return false;
    }
}
