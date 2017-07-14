package com.kims.jiekan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kims.jiekan.R;
import com.kims.jiekan.databinding.FragmentBinding;
import com.kims.jiekan.model.GankIO;
import com.kims.jiekan.ui.adapter.GankIOAdapter;
import com.kims.jiekan.utils.AppUtil;

import java.util.List;


/**
 * Created by zhangjian on 2017/4/25.
 */

public class GankIOFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentBinding mAndroidBinding;
    private List<GankIO.ResultsBean> mGankio;
    private GankIOAdapter mAdapter;
    private String type = "未知";
    private int mPage = 1;
    private boolean mIsFirstTimeTouchBottom = true;


    public static GankIOFragment getDefault(String string) {
        GankIOFragment android = new GankIOFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", string);
        android.setArguments(bundle);
        return android;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type = getArguments().getString("type");
        mAndroidBinding = DataBindingUtil.inflate(inflater, R.layout.gank_fragment, container, false);
        initData();
        supperRecycler(new LinearLayoutManager(getContext()));
        supperSwipeRefresh(this);


        return mAndroidBinding.getRoot();
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GankIO.GankIO(type, 20, 1, new GankIO.IGankResponse<List<GankIO.ResultsBean>>() {
                    @Override
                    public void onData(List<GankIO.ResultsBean> data) {
                        mGankio = data;
                        mAdapter = new GankIOAdapter(mAndroidBinding.getRoot().getContext(), mGankio);
                        mAndroidBinding.fragRecycler.setAdapter(mAdapter);
                        mAndroidBinding.fragSwipeRefresh.setRefreshing(false);
                    }

                });
            }
        }, 1500);
    }

    public void supperRecycler(RecyclerView.LayoutManager layoutManager) {
        mAndroidBinding.fragRecycler.setHasFixedSize(true);
        mAndroidBinding.fragRecycler.setLayoutManager(layoutManager);
        mAndroidBinding.fragRecycler.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemAnimator animator = mAndroidBinding.fragRecycler.getItemAnimator();
        animator.setChangeDuration(0);
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        mAndroidBinding.fragRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (AppUtil.isSlideToBottom(recyclerView) && mIsFirstTimeTouchBottom) {
                    mAndroidBinding.fragSwipeRefresh.setRefreshing(true);
                    mPage += 1;
                    mIsFirstTimeTouchBottom = false;
                    loadData();

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    public void supperSwipeRefresh(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mAndroidBinding.fragSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mAndroidBinding.fragSwipeRefresh.setOnRefreshListener(onRefreshListener);
        mAndroidBinding.fragSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GankIO.GankIO(type, mGankio.size(), 1, new GankIO.IGankResponse<List<GankIO.ResultsBean>>() {
                    @Override
                    public void onData(List<GankIO.ResultsBean> data) {
                        mGankio.clear();
                        for (GankIO.ResultsBean meiZhi : data) {
                            mGankio.add(meiZhi);
                        }
                        mAdapter.notifyDataSetChanged();
                        mAndroidBinding.fragSwipeRefresh.setRefreshing(false);
                    }

                });

            }
        }, 1500);
    }


    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GankIO.GankIO(type, 10, mPage, new GankIO.IGankResponse<List<GankIO.ResultsBean>>() {
                    @Override
                    public void onData(List<GankIO.ResultsBean> data) {
                        for (GankIO.ResultsBean meiZhi : data) {
                            mGankio.add(meiZhi);
                        }
                        mAdapter.notifyDataSetChanged();
                        mAndroidBinding.fragSwipeRefresh.setRefreshing(false);
                        mIsFirstTimeTouchBottom = true;
                    }

                });
            }
        }, 1500);


    }
}
