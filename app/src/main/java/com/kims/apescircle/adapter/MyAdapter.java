package com.kims.apescircle.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kims.apescircle.R;
import com.kims.apescircle.entity.ApesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjian on 16/7/23.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private LayoutInflater mInfolater;
    private Context mContext;
    private List<ApesData> mDatas;
    private List<ApesData> dataList;
    private MyTAdapter mMyTAdapter;



    public MyAdapter(Context context, List<ApesData> mDatas){
        this.mContext = context;
        this.mDatas = mDatas;
        mInfolater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(mInfolater.inflate(R.layout.layout_listview_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv.setText(mDatas.get(position).tvTitle);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);


        dataList = new ArrayList<>();
        ApesData apesData = new ApesData();
        for (int i=0; i < 10 ; i++){
            apesData.imgView = R.mipmap.ic_launcher;
            apesData.tvSubtitle = "test";
            dataList.add(apesData);
        }

        mMyTAdapter = new MyTAdapter(mContext,dataList);
        holder.mRecyclerView.setAdapter(mMyTAdapter);
        holder.mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.HORIZONTAL_LIST));

    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        RecyclerView mRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_title_content);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_recycler_list_view);
        }
    }

}


