package com.zt.wc.githubtest.action;

import android.util.Log;

import com.zt.wc.githubtest.base.BaseLoopTask;
import com.zt.wc.githubtest.bean.IPInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;

/**
 * Created by 王超 on 2017/1/9.
 */

public class LocalBroadcastIp extends BaseLoopTask {
    private static final String TAG = "LocalBroadcastIp";
    private static int port = 7788;
    private DatagramSocket mSocket=null;

    private LocalUsableIP mLocalUsableIP=null;

    public LocalBroadcastIp() throws SocketException {
        mLocalUsableIP = new LocalUsableIP();
        mSocket = new DatagramSocket(port);
        new Thread(this).start();
    }

    @Override
    protected void onPrepare() {
        new Thread(mLocalUsableIP).start();
    }

    @Override
    protected void onLoopTask() {
        if (mSocket != null) {
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            try {
                mSocket.receive(dp);
                String text = new String(dp.getData(), 0, dp.getLength());
                Log.d(TAG, "onLoopTask: " + text);
                mLocalUsableIP.add(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Map<String, IPInfo> getAll() {
        return mLocalUsableIP.getAll();
    }

    @Override
    protected void onFinish() {
        if (mSocket != null) {
            mSocket.close();
        }
        if (mLocalUsableIP != null) {
            mLocalUsableIP.close();
        }
    }
}
