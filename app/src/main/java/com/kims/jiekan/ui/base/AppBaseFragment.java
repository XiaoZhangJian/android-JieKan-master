package com.kims.jiekan.ui.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kims.jiekan.R;
import com.kims.jiekan.databinding.FragmentBinding;

/**
 * Created by zhangjian on 2017/5/26.
 */

public class AppBaseFragment extends Fragment {


    public FragmentBinding mFragmentBind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentBind = DataBindingUtil.inflate(inflater, R.layout.gank_fragment, container, false);

        return mFragmentBind.getRoot();
    }


    public void supperRecycler(RecyclerView.LayoutManager layoutManager) {
        mFragmentBind.fragRecycler.setHasFixedSize(true);
        mFragmentBind.fragRecycler.setLayoutManager(layoutManager);
        mFragmentBind.fragRecycler.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemAnimator animator = mFragmentBind.fragRecycler.getItemAnimator();
        animator.setChangeDuration(0);
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

    }


    public void supperSwipeRefresh(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mFragmentBind.fragSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mFragmentBind.fragSwipeRefresh.setOnRefreshListener(onRefreshListener);
        mFragmentBind.fragSwipeRefresh.setRefreshing(true);
    }
}
