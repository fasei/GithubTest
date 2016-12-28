package com.zt.wc.collectcrashinfo.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 本地缓存机制：
 * 1、判断SDCard是否可以使用，可以使用直接缓存；
 * 2、判断内部存储卡是否足够，可以直接缓存；
 * 3、直接打印log,否则不处理.
 * Created by 王超 on 2016/12/14.
 */
public class LocalFileUtils {
    private static final String TAG = "LocalFileUtils";
    public static int maxSize=50;
    /**
     * 文件的编码格式
     */
    private static String charSet = "UTF-8";

    public static int writeToLocalFile(String path, String msg) throws IOException {
        int result = 0;
        Log.d(TAG, "writeToLocalFile: path:" + path);
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.getParentFile().mkdirs();
        } else {
            int size = getFileSize(file1);
            Log.d(TAG, "writeToLocalFile: " + size);
            if (size >= 1024 * 1024 * 50) { //>=50M的文件自动清空
                file1.delete();
                writeToLocalFile(path, msg);//回调自己
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file1, true);
            fos.write(msg.getBytes(charSet));
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            result=-1;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 说明： 如果使用公共存储（SD卡），在6.0版本需要权限；
     * 本部分使用的是扩展存储（SD卡），不需要申请权限，但是会随着App的删除而删除.
     * 如果扩展存储空间不足，自动在内置存储空间存储.
     * @param mContext
     * @param path 具体的相对目录路径
     * @param msg 存储内容
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void judgeToLocalFile(Context mContext,String path, String msg) throws IOException {
        long length = msg.getBytes().length;
        if (PhoneUtils.isSDCardUsable(length)) {
//            writeToLocalFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + path, msg);
            writeToLocalFile(mContext.getExternalCacheDir().getAbsolutePath() + File.separator + path, msg);
        } else if (PhoneUtils.isExternalMemoryUsable(length)) {//暂时不可使用
//          writeToLocalFile(Environment.getDataDirectory().getAbsolutePath() + File.separator + path, msg);
            writeToLocalFile(mContext.getFilesDir().getAbsolutePath() + File.separator + path, msg);//data/user/0/com.zt.wc.githubtest/files/log.log
        } else {
            Log.d(TAG, "writeToLocalFile: " + msg);
        }
    }

    /**
     * 获取文件的大小(B)
     *
     * @param file
     * @return (Byte)
     * @throws IOException
     */
    public static int getFileSize(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        int available = fis.available();
        fis.close();
        return available;
    }


}
