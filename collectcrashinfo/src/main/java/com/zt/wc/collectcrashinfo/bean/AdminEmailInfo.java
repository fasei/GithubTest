package com.zt.wc.collectcrashinfo.bean;

/**
 * Created by 王超 on 2016/12/15.
 */

public class AdminEmailInfo {
    private String id;
    private String password;
    public static void main(String arg[]){
        int a=(byte)0x1;
        int b=(byte)0x10;
        int c=(byte)0x100;

        int d=a&c;
        System.out.println("a:"+a+",b:"+b+",c:"+c+",d:"+d);
        if((d|a)==c){
            System.out.println("aaaa");
        }else{
            System.out.println("bbb");
        }



    }
}
