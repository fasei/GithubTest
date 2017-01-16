package com.zt.wc.githubtest.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wc.clientaidl.IMyAidlInterface;
import com.zt.wc.githubtest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AIDLActivity extends AppCompatActivity {
    private static final String Action="com.wc.clientaidl.MyAidlService";

    @Bind(R.id.show_result)
    TextView mShowResult;

    private ServiceConnection mServiceConn;
    private IMyAidlInterface mAIDL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {
        mServiceConn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mAIDL=IMyAidlInterface.Stub.asInterface(service);
                try {
                    int result=mAIDL.getAdd(1,2);
                    mShowResult.setText("1+2="+result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mAIDL=null;
            }
        };

        Intent mIntent=new Intent();
        mIntent.setAction(Action);
        //在5.0及以上版本必须要加上这个
        mIntent.setPackage("com.wc.clientaidl");
        bindService(mIntent,mServiceConn, Context.BIND_AUTO_CREATE);
    }
    private void initView() {
    }

}
