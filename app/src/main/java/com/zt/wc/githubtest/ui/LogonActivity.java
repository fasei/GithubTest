package com.zt.wc.githubtest.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.zt.wc.githubtest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogonActivity extends AppCompatActivity {

    @Bind(R.id.logon_name_in)
    EditText mLogonNameIn;
    @Bind(R.id.logon_name)
    TextInputLayout mLogonName;
    @Bind(R.id.logon_password)
    EditText mLogonPassword;
    @Bind(R.id.logon_password_in)
    TextInputLayout mLogonPasswordIn;
    @Bind(R.id.logon)
    Button mLogon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        ButterKnife.bind(this);

    }

    /**
     * 隐藏键盘
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
