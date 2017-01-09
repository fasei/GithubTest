package com.zt.wc.githubtest.bean;

/**
 * Created by 王超 on 2017/1/6.
 */

public class MainInfoBean {
    private Class mClass;
    private String title;
    private String content;

    public MainInfoBean(Class mClass, String title, String content) {
        this.mClass = mClass;
        this.title = title;
        this.content = content;
    }

    public Class getmClass() {
        return mClass;
    }

    public void setmClass(Class mClass) {
        this.mClass = mClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MainInfoBean{" +
                "mClass=" + mClass +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}


