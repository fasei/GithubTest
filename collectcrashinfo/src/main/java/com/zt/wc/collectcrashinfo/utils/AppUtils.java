package com.zt.wc.collectcrashinfo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by 王超 on 2016/12/15.
 */

public class AppUtils {
    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    public static String getPackageName(Context context){
        return getPackageInfo(context).packageName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
