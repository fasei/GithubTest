package com.zt.wc.githubtest.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lzy.okgo.cache.CacheManager;
import com.zt.wc.githubtest.App;
import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.constant.Constant;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UnicomWebActivity extends AppCompatActivity {
    private static final String TAG = "UnicomWebActivity";
    public static final int Succeed = 1;
    public static final int Fail = 2;
    public static final String TokenUrl = "unicom_token";
    public static final String Action = "web_action";
    public static final String LoadUrl = "load_url";
    public static final String UnicomWeb = "UnicomWeb";
    public static final String WapMall = "WapMall";
    private String mAction = "";

    @Bind(R.id.unicom_web)
    WebView mUnicomWeb;
    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题栏
        setContentView(R.layout.activity_unicom_web);
        ButterKnife.bind(this);
        Intent mIntent = getIntent();
        mAction = mIntent.getStringExtra(Action);
        String urlStr = mIntent.getStringExtra(LoadUrl);

        mProgressbar.setProgress(0);

        mUnicomWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mUnicomWeb.getSettings().setJavaScriptEnabled(true); //支持js
        mUnicomWeb.getSettings().setAppCacheEnabled(true);  //启动缓存
        mUnicomWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存模式,不使用缓存
        mUnicomWeb.getSettings().setLoadWithOverviewMode(true);  //自适应屏幕
        mUnicomWeb.getSettings().setDomStorageEnabled(true);//dom storage(H5缓存机制)
        mUnicomWeb.getSettings().setSavePassword(false);
        mUnicomWeb.getSettings().setSaveFormData(false);
        deleteCache();//清楚浏览器cookie缓存

        Log.d(TAG, "onCreate: url" + urlStr);

        mUnicomWeb.loadUrl(urlStr);
        mUnicomWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(mProgressbar.getVisibility()==View.VISIBLE) {
                    mProgressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mUnicomWeb.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            /**
             * 拦截 url 跳转,在里边添加点击链接跳转或者操作
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            /**
             * 在开始加载网页时会回调
             *
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted: " + url);
                if (UnicomWeb.equals(mAction)) {//登录成功跳转到指定页面，此处需要拦截Url参数
                    if (url != null && url.startsWith(Constant.host)) {
                        Intent mTokenIntent = new Intent(UnicomWebActivity.this, LogonUnicomActivity.class);
                        mTokenIntent.putExtra(TokenUrl, "" + url);
                        setResult(Succeed, mTokenIntent);
                        finish();
                        return;
                    }
                }
                mProgressbar.setVisibility(View.VISIBLE);
            }


            /**
             * 在结束加载网页时会回调
             *
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished: " + url);
                mProgressbar.setVisibility(View.GONE);
            }});

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mUnicomWeb.canGoBack()) {
                mUnicomWeb.goBack();
                return true;
            } else {
                setResult(Fail);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteCache();
    }

    private void deleteCache(){
        CookieSyncManager.createInstance(App.gainContext());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

    }

}
