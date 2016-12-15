package com.zt.wc.collectcrashinfo.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常消息管理器（将异常转化为对应的字符串显示）
 * <p>
 * Created by 王超 on 2016/12/14.
 */
public class ExecptionUtils {
    /**
     * 获取异常消息的内容
     *
     * @param ex 具体的异常内容
     * @return 转化的字符串内容
     */
    public static String getExceptionInfo(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sw.toString() + "\n");
        return stringBuffer.toString();
    }

}
