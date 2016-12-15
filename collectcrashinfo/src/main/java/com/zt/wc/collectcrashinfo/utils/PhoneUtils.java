package com.zt.wc.collectcrashinfo.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

import com.zt.wc.collectcrashinfo.bean.PhoneInfo;

import java.io.File;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by 王超 on 2016/12/15.
 */

public class PhoneUtils {
    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    /**
     * 获取SDCARD总的存储空间(是否可用)
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 判断SDCard是否可以使用
     *
     * @param size
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isSDCardUsable(long size) {
        return size <= getAvailableInternalMemorySize();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isExternalMemoryUsable(long size) {
        return size <= getTotalExternalMemorySize();
    }

    /**
     * 获取手机的型号和品牌
     * @param context
     * @return
     */
    public static PhoneInfo getPhoneInfo(Context context){
        TelephonyManager mTm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb= android.os.Build.BRAND;//手机品牌
        return new PhoneInfo(mtype,mtyb);
    }


}
