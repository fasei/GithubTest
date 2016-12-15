package com.zt.wc.githubtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zt.wc.collectcrashinfo.base.ActivityStack;
import com.zt.wc.collectcrashinfo.utils.LocalFileUtils;
import com.zt.wc.githubtest.base.BaseActivity;
import com.zt.wc.githubtest.ui.LogonUnicomActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends BaseActivity {
    public static MainActivity main;
    private static final String TAG = "MainActivity";
        String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityStack.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "hello github!!!");

        Intent mLogonIntent=new Intent(MainActivity.this, LogonUnicomActivity.class);
        mLogonIntent.setFlags(Intent.EXTRA_DOCK_STATE_HE_DESK&Intent.EXTRA_DOCK_STATE_HE_DESK);
//        startActivity(mLogonIntent);
//        finish();
        if(s.equals("")){

        }
    }

    @Override
    protected boolean translucentStatusBar() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().removeActivity(this);

    }
}
