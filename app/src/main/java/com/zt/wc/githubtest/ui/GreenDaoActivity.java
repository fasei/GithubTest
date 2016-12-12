package com.zt.wc.githubtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.dao.GreenDaoManager;
import com.zt.wc.githubtest.dao.User;
import com.zt.wc.githubtest.greendao.gen.UserDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * GreenDao的初次使用，效果很满意，哈哈
 */
public class GreenDaoActivity extends AppCompatActivity {
    private static final String TAG = "GreenDaoActivity";
    @Bind(R.id.greendao_user_name)
    EditText mUserName;
    @Bind(R.id.greendao_user_age)
    EditText mUserAge;
    @Bind(R.id.green_show_info)
    TextView mShowInfo;
    @Bind(R.id.green_insert)
    Button mInsert;
    @Bind(R.id.green_delete)
    Button mDelete;
    @Bind(R.id.green_update)
    Button mUpdate;
    @Bind(R.id.green_select)
    Button mSelect;
    private UserDao mUserDao;  //数据表的持有对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mUserDao = GreenDaoManager.getInstance().getmDaoSession().getUserDao();
    }


    @OnClick({R.id.green_insert, R.id.green_delete, R.id.green_update, R.id.green_select})
    public void onClick(View view) {
        Log.d(TAG, "onClick: "+view.getId());
        switch (view.getId()) {
            case R.id.green_insert: {
                insert();
                select();
            }
            break;
            case R.id.green_delete: {
                delete();
                select();
            }
            break;
            case R.id.green_update:{
                update();
                select();
            }
                break;
            case R.id.green_select: {
                select();
            }
            break;
        }
    }

    /**
     * 查询数据库的内容
     */
    private void select() {
        Log.d(TAG, "onClick: green_select");
        List<User> userList = mUserDao.queryBuilder().where(UserDao.Properties.Id.ge(1)).build().list();
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 0; i < userList.size(); i++) {
            strBuffer.append(userList.get(i).toString());
        }
        mShowInfo.setText(strBuffer.toString().trim());
    }

    /**
     * 更新数据库
     */
    private void update() {
        Log.d(TAG, "onClick: green_update");
        List<User> userList = mUserDao.queryBuilder().where(UserDao.Properties.Age.ge(0)).build().list();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            user.setName(mUserName.getText().toString().trim());
            mUserDao.update(user);
        }
    }

    /**
     * 删除数据库的三条数据
     */
    private void delete() {
        Log.d(TAG, "onClick: green_delete");
        List<User> users = mUserDao.queryBuilder().where(UserDao.Properties.Age.ge(0)).orderDesc(UserDao.Properties.Id).limit(3).build().list();
        for (int i = 0; i < users.size(); i++) {
            mUserDao.delete(users.get(i));
        }
    }

    /**
     * 插入一条新的数据
     */
    private void insert() {
        Log.d(TAG, "onClick: green_insert");
        String name = mUserName.getText().toString().trim();
        String age = mUserAge.getText().toString().trim();
        if (TextUtils.isEmpty(age)) {
            age = "0";
        }
        User user = new User(null, name, Integer.parseInt(age), "mark");
        mUserDao.insert(user);
    }

}
