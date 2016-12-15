package com.zt.wc.githubtest.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.bean.CheckTokenResult;
import com.zt.wc.githubtest.bean.LogonResult;
import com.zt.wc.githubtest.bean.LogonUserInfo;
import com.zt.wc.githubtest.bean.WebBindInfo;
import com.zt.wc.githubtest.bean.WebUCenterInfo;
import com.zt.wc.githubtest.constant.Constant;
import com.zt.wc.githubtest.data.UserConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LogonUnicomActivity extends AppCompatActivity {
    private static final String TAG = "LogonUnicomActivity";
    private static final int GETTOKENREQUEST = 22;  //获取Unicom授权Token
    @Bind(R.id.logon_token)
    TextView mLogonToken;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.author)
    Button mAuthor;
    private boolean hasBenAothor = false;
    private LogonResult mLogonResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题栏
        setContentView(R.layout.activity_logon_unicom);
        ButterKnife.bind(this);

//        mToolbar.setTitle("联通授权");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        if (hasBenAothor) {
            mAuthor.setVisibility(View.VISIBLE);
        }
        UserConfig uc=new UserConfig(this);
        if(uc.isUseable()){  //可以登录
            Log.d(TAG, "onCreate: checkToken");
            checkToken(uc.getOpenId(),uc.getToken());
        }else{
            Log.d(TAG, "onCreate: goIntoUnicomWeb"+uc.getOpenId()+","+uc.getToken());
            goIntoUnicomWeb();
        }
    }

    /**
     * 跳转到联通的授权界面
     */
    public void goIntoUnicomWeb() {
        String loadUrl = Constant.baseUrl + Constant.register + "?response_type=code&appid=" + Constant.apiID + "&redirect_uri=" + Constant.host + "&scope=SCOPE&state=STATE&display=mobile";
        Log.d(TAG, "goIntoUnicomWeb: "+loadUrl);
        Intent mGetTokenIntent = new Intent(LogonUnicomActivity.this, UnicomWebActivity.class);
        mGetTokenIntent.putExtra(UnicomWebActivity.Action, UnicomWebActivity.UnicomWeb);
        mGetTokenIntent.putExtra(UnicomWebActivity.LoadUrl, loadUrl);
        startActivityForResult(mGetTokenIntent, GETTOKENREQUEST);
    }

    /**
     * 进入手机商城
     * @param loadUrl
     */
    public void goIntoWapMall(String loadUrl) {
        Log.d(TAG, "goIntoWapMall: "+loadUrl);
        Intent mGetTokenIntent = new Intent(LogonUnicomActivity.this, UnicomWebActivity.class);
        mGetTokenIntent.putExtra(UnicomWebActivity.Action, UnicomWebActivity.WapMall);
        mGetTokenIntent.putExtra(UnicomWebActivity.LoadUrl, loadUrl);
        startActivityForResult(mGetTokenIntent, GETTOKENREQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GETTOKENREQUEST) {
            if (resultCode == UnicomWebActivity.Succeed) {
                String url = data.getStringExtra(UnicomWebActivity.TokenUrl);
                Log.d(TAG, "onActivityResult: url:" + url);
                String result = url.substring(url.indexOf("?") + 1);
                if (result.contains("code")) {
                    String code = result.substring(result.indexOf("=") + 1, result.indexOf("&"));
                    if (!TextUtils.isEmpty(code)) {
                        Log.d(TAG, "onActivityResult: code:" + code);
                        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                        OkGo.get(Constant.baseUrl + Constant.getToken).tag(this)
                                .params("grant_type", "authorization_code")
                                .params("code", code)
                                .params("appid", Constant.apiID)
                                .params("appsecret", Constant.apiKey)
                                .params("redirect_uri", Constant.host)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        Toast.makeText(LogonUnicomActivity.this, "获取Token失败", Toast.LENGTH_SHORT).show();
                                        super.onError(call, response, e);
                                    }

                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Log.d(TAG, "onSuccess: " + s);
                                        mLogonToken.setText("获取消息" + s);
                                        mLogonResult = JSON.parseObject(s, LogonResult.class);
                                        if (mLogonResult != null && mLogonResult.getEcode() == 0) {//成功
                                            Toast.makeText(LogonUnicomActivity.this, "获取Token成功", Toast.LENGTH_SHORT).show();

                                            getUserNameAndPassword(mLogonResult);//获取用户的用户名和密码
                                        } else {
                                            Toast.makeText(LogonUnicomActivity.this, "获取Token失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(this, "用户禁止授权", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 从web服务器获取用户的用户名和密码
     *
     * @param logonResult
     */
    public void getUserNameAndPassword(LogonResult logonResult) {
        OkGo.get(Constant.web_baseUrl + "?openid=" + logonResult.getOpenid() + "&token=" + logonResult.getAccess_token()).tag(this)
                .headers("Content-Type", "application/json")
                .execute(new StringCallback() {

                    /**
                     * 如果接收结果为null，自动转化为""
                     * @param response 返回消息体
                     * @return 去读返回结果的body字符串
                     * @throws Exception  转化异常
                     */
                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        String readMessage = super.convertSuccess(response);
                        Log.d(TAG, "convertSuccess: readMessage:" + readMessage);
                        if (!TextUtils.isEmpty(readMessage)) {
                            if (readMessage.toLowerCase().equals("null")) {
                                readMessage = "";
                            }
                        }
                        return readMessage;
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Log.d(TAG, "onError: ");
                        super.onError(call, response, e);
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "onSuccess: s:" + s + "-----len:" + s.length());
                        if (!TextUtils.isEmpty(s)) {
                            if (s.contains("INVALID OPENID")) {//OpenID与Token无法匹配
                                Log.d(TAG, "registerSmateruser(): ");
                                registerSmateruser();
                            } else if (s.contains("OPENID AND TOKEN NOT MATCH")) {//OpenID不存在
                                try {
                                    Log.d(TAG, "updateToken(): ");
                                    updateToken();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.d(TAG, "logonUserInfo(): ");
                                LogonUserInfo logonUserInfo = JSON.parseObject(s, LogonUserInfo.class);
                                if (logonUserInfo != null) {  //此处处理登录的过程
                                    Log.d(TAG, "onSuccess: " + logonUserInfo.toString());
                                    mLogonToken.setText(logonUserInfo.toString());

                                    //更新缓存数据
                                    UserConfig uc = new UserConfig(LogonUnicomActivity.this);
                                    uc.setToken(mLogonResult.getAccess_token());
                                    uc.setOpenID(mLogonResult.getOpenid());
                                    uc.setUseable(true);

                                    Log.d(TAG, "onSuccess: mLogonResult"+mLogonResult.toString());

                                    Toast.makeText(LogonUnicomActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
//
                                    Intent mIntent=new Intent(LogonUnicomActivity.this,GreenDaoActivity.class);
                                    Bundle mBundle=new Bundle();
                                    mBundle.putString("id","123");
                                    mIntent.putExtras(mBundle);
                                    LogonUnicomActivity.this.startActivity(mIntent);
                                    LogonUnicomActivity.this.finish();

//                                    OkGo.get(Constant.wap_logout).execute(new StringCallback() {
//                                        @Override
//                                        public void onError(Call call, Response response, Exception e) {
//                                            super.onError(call, response, e);
//                                        }
//
//                                        @Override
//                                        public void onSuccess(String s, Call call, Response response) {
//                                            String loadUrl = "http://o2o.bjztzh.com/wap/index.php?token=" + mLogonResult.getAccess_token() + "&uid=" + mLogonResult.getOpenid();
//                                            Log.d(TAG, "onSuccess: loadUrl:"+loadUrl);
//
//                                            goIntoWapMall(loadUrl);
//                                        }
//                                    });

                                } else {
                                    Log.d(TAG, "onSuccess: logonResult :      null...");
                                }
                            }
                        } else {
                            Log.d(TAG, "onSuccess: s is null");
                            //开始走注册的流程
                            registerSmateruser();

                        }

                    }
                });

    }

    public void registerSmateruser() {
//        String mSmaterUserID = "18513410245";  //有效用户
//        String mSmaterUserID = "0011006213B";
        String mSmaterUserID = "13831217198";
//        String mSmaterUserPassword = "123456789";
        registerWebBind(mSmaterUserID);
    }

    /**
     * 提交Smater用户和联通OpenID的绑定
     *
     * @param smaterUserID
     */
    public void registerWebBind(String smaterUserID) {
        if (mLogonResult == null) {
            return;
        }
        WebBindInfo mWebBindInfo = new WebBindInfo(mLogonResult.getOpenid(), smaterUserID, mLogonResult.getAccess_token());
        String msg = new Gson().toJson(mWebBindInfo);
//        String msg=JSON.toJSONString(mWebBindInfo);  //转String首字母会小写,不区分大小写

        Log.d(TAG, "registerWebBind: param:" + msg);
        OkGo.post(Constant.web_baseUrl).tag(this)
                .headers("Content-Type", "application/json")
                .upJson(msg)
                .execute(new StringCallback() {
                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        String msg = super.convertSuccess(response);
                        Log.d(TAG, "convertSuccess: " + msg);
                        return msg;
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "registerWebBind: " + s);
                        if (!TextUtils.isEmpty(s)) {
                            if ("true".equals(s.toLowerCase())) {//绑定成功
                                registerWapMall();  //web绑定成功，开始注册商城用户
                            } else if ("false".equals(s.toLowerCase())) { //绑定失败

                            } else { //错误

                            }
                        } else {  //失败

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void updateToken() throws JSONException {
        if (mLogonResult == null) {
            return;
        }
        JSONObject msgObj = new JSONObject();
        msgObj.put("OldToken", "666666");
        msgObj.put("NewToken", mLogonResult.getAccess_token());

        Log.d(TAG, "updateToken: ");
        OkGo.put(Constant.web_baseUrl + "?openid=" + mLogonResult.getOpenid()).tag(this)
                .upJson(msgObj.toString()).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d(TAG, "updateToken: " + s);
                if (!TextUtils.isEmpty(s)) {
                    if ("true".equals(s.toLowerCase())) {//绑定成功
                        getUserNameAndPassword(mLogonResult);
//                        registerWapMall();//测试
                    } else if ("false".equals(s.toLowerCase())) { //绑定失败

                    } else { //错误

                    }
                } else {  //失败

                }
            }
        });


    }

    public void removeWebBind() {
        if (mLogonResult == null) {
            return;
        }
        String msg = "{\"Token\":\"+mLogonResult.getAccess_token+\"}";

        Log.d(TAG, "registerWebBind: param:" + msg);
        OkGo.delete(Constant.web_baseUrl + "?openid=" + mLogonResult.getOpenid()).tag(this)
                .upJson(msg)
                .headers("Content-Type", "application/json")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "removeWebBind: " + s);
                        if (!TextUtils.isEmpty(s)) {
                            if ("true".equals(s.toLowerCase())) {//绑定成功

                            } else if ("false".equals(s.toLowerCase())) { //绑定失败

                            } else { //错误

                            }
                        } else {  //失败

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }


    public void registerWapMall() {
        if (mLogonResult == null) {
            return;
        }
        WebUCenterInfo mWebUCenterInfo = new WebUCenterInfo(mLogonResult.getOpenid(), mLogonResult.getAccess_token(), "6666" + "@bjztzh.com");
        String msg = new Gson().toJson(mWebUCenterInfo);
        OkGo.post(Constant.web_UCenterUrl).tag(this)
                .headers("Content-Type", "application/json")
                .upJson(msg)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        removeWebBind();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "registerWapMall: " + s);
                        if (!TextUtils.isEmpty(s)) {
                            if ("true".equals(s.toLowerCase())) {//绑定成功
                                getUserNameAndPassword(mLogonResult);  //注册工作全部完成，自动转入获取用户名
                            } else if ("false".equals(s.toLowerCase())) { //绑定失败
//                        removeWebBind();
                            } else { //错误
//                        removeWebBind();
                            }
                        } else {
//                    removeWebBind();
                        }
                    }
                });
    }

    public void loginUCenter(String name, String password) throws JSONException {
        JSONObject msgObj = new JSONObject();
        msgObj.put("UserName", name);
        msgObj.put("Password", password);

        OkGo.post(Constant.web_UCenterUrl + "?username=" + name).tag(this)
                .headers("Content-Type", "application/json")
                .upJson(msgObj.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "loginUCenter: " + s);
                        if (!TextUtils.isEmpty(s)) {
                            if ("true".equals(s.toLowerCase())) {//成功

                            } else if ("false".equals(s.toLowerCase())) { //失败 ?难道是未注册？

                            } else { //错误

                            }
                        } else {

                        }
                    }
                });
    }

    @OnClick({R.id.author, R.id.author_unicon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.author:
            case R.id.author_unicon:
                goIntoUnicomWeb();
                break;
        }
    }

    private void checkToken(final String openID, final String token){
    OkGo.get(Constant.baseUrl+Constant.checkToken+"?access_token="+token+"&openid="+openID)
            .execute(new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            Log.d(TAG, "onSuccess: "+s);
            if(!TextUtils.isEmpty(s)){
                CheckTokenResult checkTokenResult = JSON.parseObject(s, CheckTokenResult.class);
                if(checkTokenResult!=null&&checkTokenResult.getEcode()==0){  //token可以使用
                    mLogonResult=new LogonResult();
                    mLogonResult.setAccess_token(token);
                    mLogonResult.setOpenid(openID);
                    getUserNameAndPassword(mLogonResult);
                    Toast.makeText(LogonUnicomActivity.this, "自动登录", Toast.LENGTH_SHORT).show();
                }else{
                    goIntoUnicomWeb();
                }
            }

        }
    });

    }

    private ProgressDialog dialog;

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
