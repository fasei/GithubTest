package com.zt.wc.githubtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.base.BaseRecyclerAdapter;
import com.zt.wc.githubtest.bean.MainInfoViewHolder;
import com.zt.wc.githubtest.bean.MainInfoBean;

import java.util.List;

/**
 * Created by 王超 on 2017/1/6.
 */

public class MainInfoAdapter extends BaseRecyclerAdapter<MainInfoBean,MainInfoViewHolder> {
    public MainInfoAdapter(Context context) {
        super(context);
    }

    public MainInfoAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public MainInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.maininfo_item, parent, false);
        MainInfoViewHolder mMainInfoViewHolder=new MainInfoViewHolder(view);
        return mMainInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(MainInfoViewHolder holder, final int position) {
        holder.mTitle.setText(mDatas.get(position).getTitle());
        holder.mGoGoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,mDatas.get(position).getmClass()));
            }
        });

    }


}
