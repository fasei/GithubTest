package com.zt.wc.collectcrashinfo.type;

/**
 * 崩溃消息的收集方式
 * 1、文件方式保存在本地（测试使用）
 * 2、邮件方式，提交到QQ邮箱
 * 3、Http方式提交到服务器，服务器收集各类崩溃消息
 * 4、同上（提高了安全性）
 * Created by 王超 on 2016/12/14.
 */
public enum CollectType {
    LocalLog,Email,Http,Https
}
