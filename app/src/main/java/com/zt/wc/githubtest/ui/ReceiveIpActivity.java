package com.zt.wc.githubtest.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.action.LocalBroadcastIp;
import com.zt.wc.githubtest.adapter.ReceiveIPAdapter;
import com.zt.wc.githubtest.base.DividerItemDecoration;
import com.zt.wc.githubtest.bean.IPInfo;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReceiveIpActivity extends AppCompatActivity {
    @Bind(R.id.recycle_view_ip)
    RecyclerView mRecycleViewIp;
    @Bind(R.id.swipe_refresh_ip)
    SwipeRefreshLayout mSwipeRefreshIp;
    private LocalBroadcastIp mLocalBroadcastIp;

    private ReceiveIPAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_ip);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        mRecycleViewIp.setLayoutManager(new LinearLayoutManager(this));
        mRecycleViewIp.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        mSwipeRefreshIp.setColorSchemeColors(Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK);
        //设置向下拉多少出现刷新
        mSwipeRefreshIp.setDistanceToTriggerSync(2 * 100);
        //设置刷新出现的位置
        mSwipeRefreshIp.setProgressViewEndTarget(false, 200);
    }

    private void initData() {
        try {
            mLocalBroadcastIp = new LocalBroadcastIp();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        List<IPInfo> datas = new ArrayList<>(mLocalBroadcastIp.getAll().values());

        mAdapter = new ReceiveIPAdapter(this, datas);
        mRecycleViewIp.setAdapter(mAdapter);
    }

    private void initListener() {
        mSwipeRefreshIp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2 * 1000);
                        ReceiveIpActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<IPInfo> datas = new ArrayList<>(mLocalBroadcastIp.getAll().values());
                                mAdapter.updateItems(datas);
                                mSwipeRefreshIp.setRefreshing(false);
                            }
                        });

                    }
                }).start();

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastIp.close();
    }
}
