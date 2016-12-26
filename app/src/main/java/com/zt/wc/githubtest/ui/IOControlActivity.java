package com.zt.wc.githubtest.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.action.IOControl;
import com.zt.wc.githubtest.action.WorkState;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 控制IO状态，达到控制灯光效果
 */
public class IOControlActivity extends AppCompatActivity {
    @Bind(R.id.show_io_state)
    TextView mShowIoState;
    @Bind(R.id.normal_state)
    Button mNormalState;
    @Bind(R.id.select_state)
    Button mSelectState;
    @Bind(R.id.no_mac_state)
    Button mNoMacState;
    @Bind(R.id.no_connect_state)
    Button mNoConnectState;
    @Bind(R.id.usercmd_state)
    Button mUsercmdState;

    private String controlName[]={"正常状态","搜索状态","未搜索到MAC","登录失败/联网失败","用户控制"};

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
        mShowIoState.setText("灯光展示台,当前操作："+controlName[IOControl.getInstance().getType()]);
            switch (msg.what) {
                case WorkState.Color_000:
                    mShowIoState.setBackgroundColor(Color.BLACK);
                    break;
                case WorkState.Color_R:
                    mShowIoState.setBackgroundColor(Color.RED);
                    break;
                case WorkState.Color_B:
                    mShowIoState.setBackgroundColor(Color.BLUE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iocontrol);
        ButterKnife.bind(this);

        IOControl.getInstance().setmHandler(mHandler).onCreate();
    }

    @OnClick({R.id.normal_state, R.id.select_state, R.id.no_mac_state, R.id.no_connect_state,R.id.usercmd_state})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.normal_state:
                IOControl.getInstance().setType(IOControl.Normal);
                break;
            case R.id.select_state:
                IOControl.getInstance().setType(IOControl.Search);
                break;
            case R.id.no_mac_state:
                IOControl.getInstance().setType(IOControl.NoMac);
                break;
            case R.id.no_connect_state:
                IOControl.getInstance().setType(IOControl.LoginError);
                break;
            case R.id.usercmd_state:
                IOControl.getInstance().setType(IOControl.UserCMD);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IOControl.getInstance().onDestroy();
    }

}
