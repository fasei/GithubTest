package com.zt.wc.collectcrashinfo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**网络处理（查询网络状态，是否可以使用）
 *
 * Created by 王超 on 2016/12/16.
 */

public class NetUtils {
    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetOk(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
