package com.zt.wc.githubtest.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zt.wc.githubtest.R;

/**
 * Created by 王超 on 2017/1/9.
 */

public class ReceiveIPViewHolder extends RecyclerView.ViewHolder
{
 public   TextView mTitle;
    public ReceiveIPViewHolder(View itemView) {
        super(itemView);
        mTitle= (TextView) itemView.findViewById(R.id.textView);
    }
}
