package com.kims.apescircle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kims.apescircle.R;
import com.kims.apescircle.entity.ApesData;

import java.util.List;

/**
 * item内嵌的RecyclerView 的 Adapter
 * Created by zhangjian on 16/7/24.
 */
public class SonAdapter extends RecyclerView.Adapter<SonAdapter.ViewHolder> {

    private Context mContext;
    private List<ApesData> mDatas;
    private LayoutInflater mLayoutInflater;

    public SonAdapter(Context context, List<ApesData> mDatas){
        mContext = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(mLayoutInflater.inflate(R.layout.layout_recycler_listview_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.img.setImageResource(mDatas.get(position).imgView);
        holder.tv.setText(mDatas.get(position).tvSubtitle);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.recycler_item_list_img);
            tv = (TextView) itemView.findViewById(R.id.recycler_item_list_tv);
        }
    }
}
