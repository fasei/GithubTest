package com.zt.wc.githubtest.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zt.wc.githubtest.R;

/**
 * Created by 王超 on 2017/1/6.
 */

public class MainInfoViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitle;
    public Button mGoGoGo;

    public MainInfoViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.maininfo_item_title);
        mGoGoGo = (Button) itemView.findViewById(R.id.gogogo);
    }
}
