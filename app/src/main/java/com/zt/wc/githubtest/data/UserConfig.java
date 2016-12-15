package com.zt.wc.githubtest.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户信息的配置文件
 * Created by 王超 on 2016/12/8.
 */

public class UserConfig {
    SharedPreferences userconfig;

    private Context context;

    public UserConfig(Context context) {
        userconfig = context.getSharedPreferences("userconfig", Context.MODE_PRIVATE);
    }

    public String getOpenId() {
        return getStr("id");
    }

    public void setOpenID(String id) {
        setStr("id", id);
    }

    public String getToken() {
        return getStr("token");
    }

    public void setToken(String token) {
        setStr("token", token);
    }

    private String getStr(String key) {
        return userconfig.getString(key, "");
    }

    private void setStr(String key, String value) {
        userconfig.edit().putString(key, value).commit();
    }

    private boolean getBoolean(String key) {
        return userconfig.getBoolean(key, false);
    }

    private void setBoolean(String key, boolean value) {
        userconfig.edit().putBoolean(key, value).commit();
    }

    public boolean isUseable() {
        return getBoolean("useable");
    }

    public void setUseable(boolean isUseable) {
        setBoolean("useable",isUseable);
    }

}
