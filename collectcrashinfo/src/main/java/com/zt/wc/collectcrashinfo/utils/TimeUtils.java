package com.zt.wc.collectcrashinfo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间管理类
 *
 * Created by 王超 on 2016/12/14.
 */

public class TimeUtils {
    /**
     *获取当前时间
     * @return
     */
    public static String getTime() {
        return getTime("yyyy-MM-dd HH:mm:ss");
    }


    public static String getTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);//
        return df.format(new Date());
    }

    public static String getFileName() {
        return getTime("yyyy_MM_dd_HH_mm_ss");
    }

    public static String dateToStr() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = format.format(date);
        return timeStr;
    }

    public static Date strToDate(String timeStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = format.parse(timeStr);
        return date;
    }
    public static long getNowTime(){
        return Calendar.getInstance().getTimeInMillis();
    }
}
