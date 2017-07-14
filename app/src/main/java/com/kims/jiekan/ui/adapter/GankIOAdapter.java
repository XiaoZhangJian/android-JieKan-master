package com.kims.jiekan.ui.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kims.jiekan.R;
import com.kims.jiekan.databinding.ItemBinding;
import com.kims.jiekan.model.GankIO;
import com.kims.jiekan.utils.AppUtil;
import com.kims.jiekan.ui.WebActivity;

import java.util.List;

/**
 * Created by zhangjian on 2017/5/6.
 */

public class GankIOAdapter extends RecyclerView.Adapter<GankIOAdapter.MZViewHolder> {

    private List<GankIO.ResultsBean> mData;
    private Context mContext;

    public GankIOAdapter(Context context, List<GankIO.ResultsBean> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public MZViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBinding
                itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_gank_io_fragment,
                parent, false);

        MZViewHolder bindHolder = new MZViewHolder(itemBinding.getRoot());
        bindHolder.setItemBinding(itemBinding);
        return bindHolder;
    }

    @Override
    public void onBindViewHolder(MZViewHolder holder, int position) {

        final GankIO.ResultsBean resultsBean = mData.get(position);
        resultsBean.setName("DaoLao未留名");
        if (null != resultsBean.getImages()) {
            for (String string : resultsBean.getImages()) {
                holder.mItemBinding.itemImg.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(string)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 缓存策略
                        .priority(Priority.NORMAL) // 下载优先级
                        .into(holder.mItemBinding.itemImg);
            }
        } else {
            holder.mItemBinding.itemImg.setVisibility(View.GONE);
        }
        String date_month_year = resultsBean.getPublishedAt().substring(0, resultsBean.getPublishedAt().indexOf("T"));
        String Minutes = resultsBean.getPublishedAt().substring(resultsBean.getPublishedAt().indexOf("T") + 1, resultsBean.getPublishedAt().length() - 1);


        holder.mItemBinding.itemText.setText(" · " + AppUtil.friendlyTimeFormat(date_month_year + " " + Minutes));

        holder.mItemBinding.setGank(resultsBean);
        holder.mItemBinding.executePendingBindings();
        holder.mItemBinding.itemCardViewLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("URL", resultsBean.getUrl());
                intent.putExtra("TITLE", resultsBean.getDesc());

                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MZViewHolder extends RecyclerView.ViewHolder {

        private ItemBinding mItemBinding;


        public MZViewHolder(View itemView) {
            super(itemView);
        }


        public ItemBinding getItemBinding() {
            return mItemBinding;
        }

        public void setItemBinding(ItemBinding itemBinding) {
            mItemBinding = itemBinding;
        }
    }
}
