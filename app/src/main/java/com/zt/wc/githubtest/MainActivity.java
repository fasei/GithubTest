package com.zt.wc.githubtest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.zt.wc.collectcrashinfo.base.ActivityStack;
import com.zt.wc.githubtest.adapter.MainInfoAdapter;
import com.zt.wc.githubtest.base.BaseActivity;
import com.zt.wc.githubtest.base.DividerItemDecoration;
import com.zt.wc.githubtest.bean.MainInfoBean;
import com.zt.wc.githubtest.ui.GreenDaoActivity;
import com.zt.wc.githubtest.ui.IOControlActivity;
import com.zt.wc.githubtest.ui.LogonActivity;
import com.zt.wc.githubtest.ui.LogonUnicomActivity;
import com.zt.wc.githubtest.ui.ReceiveIpActivity;
import com.zt.wc.githubtest.ui.SharePrefernceActivity;
import com.zt.wc.githubtest.ui.SweetDialogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static MainActivity main;
    private static final String TAG = "MainActivity";
    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private List<MainInfoBean> mMainInfoData = null;
    private MainInfoAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityStack.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar(mToolbar,true,"主界面");
        Log.d(TAG, "hello github!!!");

        initView();
        initData();

//        Intent mLogonIntent=new Intent(MainActivity.this, SweetDialogActivity.class);
////        mLogonIntent.setFlags(Intent.EXTRA_DOCK_STATE_HE_DESK&Intent.EXTRA_DOCK_STATE_HE_DESK);
//
//
//        startActivity(mLogonIntent);
//        finish();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    EMailUtils.sendEMail(new MailSenderInfo(),"11111");
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                } catch (GeneralSecurityException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        if(s.equals("")){
//
//        }
    }


    private void initData() {
        mMainInfoData = new ArrayList<>();
//        mMainInfoData.add(new MainInfoBean(GreenDaoActivity.class,"GreenDao的使用","包括数据库的增删改查的使用，基本使用技巧"));

        mAdapter = new MainInfoAdapter(this, mMainInfoData);
        mRecycleView.setAdapter(mAdapter);
    }

    private void mainData() {
        if (mMainInfoData == null) {
            mMainInfoData = new ArrayList<>();
        } else {
            mMainInfoData.clear();
        }
        mMainInfoData.add(new MainInfoBean(GreenDaoActivity.class, "GreenDao的使用", "包括数据库的增删改查的使用，基本使用技巧"));
        mMainInfoData.add(new MainInfoBean(IOControlActivity.class, "模拟IO控制效果", "通过界面按键模拟灯光效果"));
        mMainInfoData.add(new MainInfoBean(SweetDialogActivity.class, "Dialog样式", "个性化的Dialog的延时效果"));
        mMainInfoData.add(new MainInfoBean(LogonActivity.class, "InputEditText的演示", "演示登录界面的效果"));
        mMainInfoData.add(new MainInfoBean(LogonUnicomActivity.class, "联通的授权界面", "演示联通授权界面"));
        mMainInfoData.add(new MainInfoBean(SharePrefernceActivity.class, "SharePrefernce工具类测试", "测试使用功能"));
        mMainInfoData.add(new MainInfoBean(ReceiveIpActivity.class, "接收局域网中的广播", "接收局域网中的广播"));
    }

    private void initView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //改变加载显示的颜色
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK);
        //设置背景颜色
//        mSwipeRefresh.setBackgroundColor(Color.YELLOW);
        //设置初始时的大小
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        //设置监听
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        SystemClock.sleep(1 * 1000);
                        mainData();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mSwipeRefresh.setRefreshing(false);
                            }
                        });
                    }
                }).start();

            }
        });
        //设置向下拉多少出现刷新
        mSwipeRefresh.setDistanceToTriggerSync(2 * 100);
        //设置刷新出现的位置
        mSwipeRefresh.setProgressViewEndTarget(false, 200);

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
