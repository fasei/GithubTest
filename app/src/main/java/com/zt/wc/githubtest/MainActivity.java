package com.zt.wc.githubtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zt.wc.githubtest.ui.LogonActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "hello github!!!");
        Intent mLogonIntent=new Intent(MainActivity.this, LogonActivity.class);
        startActivity(mLogonIntent);
    }
}
