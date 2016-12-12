package com.zt.wc.githubtest.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 王超 on 2016/12/12.
 */
@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private int age;
    private String mark;


    @Generated(hash = 1828883881)
    public User(Long id, String name, int age, String mark) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mark = mark;
    }

    @Generated(hash = 586692638)
    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", mark='" + mark + '\'' +
                '}'+"\n";
    }
}
