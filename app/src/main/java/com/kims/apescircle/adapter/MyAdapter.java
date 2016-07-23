package com.kims.apescircle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kims.apescircle.R;

import java.util.List;

/**
 * Created by zhangjian on 16/7/23.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private LayoutInflater mInfolater;
    private Context mContext;
    private List<String> mDatas;

    public MyAdapter(Context context, List<String> mDatas){
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

        holder.tv.setText(mDatas.get(position));
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_title_content);
        }
    }

}


