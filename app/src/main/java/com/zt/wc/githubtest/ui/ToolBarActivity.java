package com.zt.wc.githubtest.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToolBarActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        ButterKnife.bind(this);
        initToolBar(mToolbar,true,"ToolBar Test!");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
}
