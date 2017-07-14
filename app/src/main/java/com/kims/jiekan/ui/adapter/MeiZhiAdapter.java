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
import com.kims.jiekan.databinding.ItemMeizhiBinding;
import com.kims.jiekan.model.GankIO;
import com.kims.jiekan.ui.PhotoFlowActivity;

import java.util.List;

/**
 * Created by zhangjian on 2017/5/6.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter<MeiZhiAdapter.MZViewHolder> {

    private List<GankIO.ResultsBean> mData;
    private Context mContext;

    public MeiZhiAdapter(Context context, List<GankIO.ResultsBean> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public MZViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMeizhiBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_meizhi_fragment,
                parent, false);
        MZViewHolder bindHolder = new MZViewHolder(itemBinding.getRoot());
        bindHolder.setItemBinding(itemBinding);
        return bindHolder;
    }

    @Override
    public void onBindViewHolder(final MZViewHolder holder, int position) {
        final GankIO.ResultsBean resultsBean = mData.get(position);
        Glide.with(mContext)
                .load(resultsBean.getUrl())
                .fitCenter()
                .thumbnail(0.1f)
                .priority(Priority.NORMAL)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mItemBinding.itemImgMeizhi);
        holder.mItemBinding.executePendingBindings();

        holder.mItemBinding.itemImgMeizhi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoFlowActivity.class);
                intent.putExtra("imgUrl", resultsBean.getUrl());
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, holder.mItemBinding.itemImgMeizhi, String.valueOf(R.string.name));
                mContext.startActivity(intent, transitionActivityOptions.toBundle());


            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MZViewHolder extends RecyclerView.ViewHolder {

        private ItemMeizhiBinding mItemBinding;


        public MZViewHolder(View itemView) {
            super(itemView);
        }


        public ItemMeizhiBinding getItemBinding() {
            return mItemBinding;
        }

        public void setItemBinding(ItemMeizhiBinding itemBinding) {
            mItemBinding = itemBinding;
        }
    }
}
