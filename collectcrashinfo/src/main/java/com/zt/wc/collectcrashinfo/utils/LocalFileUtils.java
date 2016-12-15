package com.zt.wc.collectcrashinfo.utils;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void judgeToLocalFile(String path, String msg) throws IOException {
        long length = msg.getBytes().length;
        if (PhoneUtils.isSDCardUsable(length)) {
            writeToLocalFile(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + path, msg);
        } else if (PhoneUtils.isExternalMemoryUsable(length)) {
            writeToLocalFile(Environment.getDataDirectory().getAbsolutePath() + File.separator + path, msg);
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
