package com.zt.wc.githubtest.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.zt.wc.githubtest.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 各种dialog样式的使用和学习
 */
public class SweetDialogActivity extends AppCompatActivity {

    @Bind(R.id.all)
    Button mAll;
    @Bind(R.id.only_text)
    Button mOnlyText;
    @Bind(R.id.only_content_text)
    Button mOnlyContentText;
    @Bind(R.id.only_error)
    Button mOnlyError;
    @Bind(R.id.only_warm)
    Button mOnlyWarm;
    @Bind(R.id.only_success)
    Button mOnlySuccess;
    @Bind(R.id.my_self)
    Button mMySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweet_dialog);
        ButterKnife.bind(this);

//        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("正在加载...");
//        pDialog.setCancelable(true);
//        pDialog.setCanceledOnTouchOutside(false);
//        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Toast.makeText(SweetDialogActivity.this, "fewf", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        pDialog.show();

    }

    @OnClick({R.id.all, R.id.only_text, R.id.my_self,R.id.only_content_text, R.id.only_error, R.id.only_warm, R.id.only_success})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all: {
                SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("正在加载...");
                pDialog.setCancelable(false);
//                pDialog.setCancelable(true);
//                pDialog.setCancelText("取消");
                pDialog.setCanceledOnTouchOutside(true);
                pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(SweetDialogActivity.this, "取消了", Toast.LENGTH_SHORT).show();

                    }
                });
                pDialog.show();
            }
            break;
            case R.id.only_text:
                new SweetAlertDialog(this)
                        .setTitleText("Here's a message!")
                        .show();
                break;
            case R.id.only_content_text:
                new SweetAlertDialog(this)
                        .setTitleText("Here's a message!")
                        .setContentText("It's pretty, isn't it?")
                        .show();
                break;
            case R.id.only_error:
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show();
                break;
            case R.id.only_warm:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .show();
                break;
            case R.id.only_success:
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("")
                        .setContentText("")
                        .show();
                break;
            case R.id.my_self:
            {
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Sweet!")
                        .setContentText("Here's a custom image.")
                        .setCustomImage(R.drawable.ic_launcher)
                        .show();
            }
                break;
        }
    }
}
