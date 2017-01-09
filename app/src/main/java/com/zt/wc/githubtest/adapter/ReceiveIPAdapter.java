package com.zt.wc.githubtest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.zt.wc.githubtest.R;
import com.zt.wc.githubtest.base.BaseRecyclerAdapter;
import com.zt.wc.githubtest.bean.IPInfo;
import com.zt.wc.githubtest.bean.ReceiveIPViewHolder;
import java.util.List;
/**
 * Created by 王超 on 2017/1/9.
 */

public class ReceiveIPAdapter extends BaseRecyclerAdapter<IPInfo,ReceiveIPViewHolder> {

    public ReceiveIPAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public ReceiveIPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.receiveip_item, parent, false);
        ReceiveIPViewHolder mReceiveIPViewHolder=new ReceiveIPViewHolder(view);

        return mReceiveIPViewHolder;
    }

    @Override
    public void onBindViewHolder(ReceiveIPViewHolder holder, int position) {
        IPInfo ipInfo = mDatas.get(position);
        holder.mTitle.setText("ID:"+ipInfo.getId()+",IP:"+ipInfo.getIp());
    }

}
