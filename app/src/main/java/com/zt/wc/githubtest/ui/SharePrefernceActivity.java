package com.zt.wc.githubtest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.utils.SpUtils;

import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SharePrefernceActivity extends AppCompatActivity {

    @Bind(R.id.show_message)
    TextView mShowMessage;
    @Bind(R.id.radio_group)
    RadioGroup mRadioGroup;
    @Bind(R.id.input_key)
    EditText mInputKey;
    @Bind(R.id.input_value)
    EditText mInputValue;
    @Bind(R.id.sp_add)
    Button mSpAdd;
    @Bind(R.id.sp_remove)
    Button mSpRemove;
    @Bind(R.id.sp_update)
    Button mSpUpdate;
    @Bind(R.id.sp_query)
    Button mSpQuery;
    @Bind(R.id.sp_clear)
    Button mSpClear;
    int checkType;
    String key = "";
    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preferrnce);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sp_add, R.id.sp_remove, R.id.sp_update, R.id.sp_query, R.id.sp_clear})
    public void onClick(View view) {
        checkType = mRadioGroup.getCheckedRadioButtonId();
        key = mInputKey.getText().toString();
        value = mInputValue.getText().toString();

        switch (view.getId()) {
            case R.id.sp_add:
                add();
                query();
                break;
            case R.id.sp_remove:
                SpUtils.remove(this, key);
                query();
                break;
            case R.id.sp_update:
                SpUtils.set(this, key, value);
                query();

                break;
            case R.id.sp_query:
                query();
                break;
            case R.id.sp_clear:
                SpUtils.clear(this);
                query();
                break;
        }
    }

    private void add() {
        Object values = null;
        try {
            switch (checkType) {
                case R.id.radio_boolean:
                    values = Boolean.parseBoolean(value);
                    break;
                case R.id.radio_float:
                    values = Float.parseFloat(value);
                    break;
                case R.id.radio_integer:
                    values = Integer.parseInt(value);
                    break;
                case R.id.radio_long:
                    values = Long.parseLong(value);
                    break;
                case R.id.radio_string:
                    values = value;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "类型错误", Toast.LENGTH_SHORT).show();
            return;
        }
        SpUtils.set(this, key, values);

    }

    private void query() {
        StringBuilder builder = new StringBuilder();
        Map<String, ?> map = SpUtils.getAll(this);
        Iterator<? extends Map.Entry<String, ?>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ?> next = iterator.next();
            builder.append("key:" + next.getKey() + ",value:" + next.getValue() + "\r\n");
        }
        mShowMessage.setText(builder.toString());


    }
}
