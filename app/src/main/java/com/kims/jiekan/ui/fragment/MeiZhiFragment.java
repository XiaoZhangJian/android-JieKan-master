package com.kims.jiekan.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kims.jiekan.R;
import com.kims.jiekan.databinding.FragmentBinding;
import com.kims.jiekan.model.GankIO;
import com.kims.jiekan.ui.adapter.MeiZhiAdapter;
import com.kims.jiekan.utils.AppUtil;

import java.util.List;


public class MeiZhiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentBinding mBinding;
    private List<GankIO.ResultsBean> mMeiZhis;
    private MeiZhiAdapter mAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private int mPage = 1;
    private static final int PRELOAD_SIZE = 6;

    public static MeiZhiFragment getDefault() {
        return new MeiZhiFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.gank_fragment, container, false);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        initData();
        supperRecycler(layoutManager);
        supperSwipeRefresh(this);
        return mBinding.getRoot();
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GankIO.GankIO(getString(R.string.gank_welfare), 20, 1, new GankIO.IGankResponse<List<GankIO.ResultsBean>>() {
                    @Override
                    public void onData(List<GankIO.ResultsBean> data) {
                        mMeiZhis = data;
                        mAdapter = new MeiZhiAdapter(mBinding.getRoot().getContext(), mMeiZhis);
                        mBinding.fragRecycler.setAdapter(mAdapter);
                        mBinding.fragSwipeRefresh.setRefreshing(false);
                    }

                });
            }
        }, 1500);
    }


    public void supperRecycler(RecyclerView.LayoutManager layoutManager) {
        mBinding.fragRecycler.setHasFixedSize(true);
        mBinding.fragRecycler.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator animator = mBinding.fragRecycler.getItemAnimator();
        animator.setChangeDuration(0);
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        mBinding.fragRecycler.setItemAnimator(animator);

        mBinding.fragRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (AppUtil.isSlideToBottom(recyclerView) && mIsFirstTimeTouchBottom) {
                    mBinding.fragSwipeRefresh.setRefreshing(true);
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
        mBinding.fragSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mBinding.fragSwipeRefresh.setOnRefreshListener(onRefreshListener);
        mBinding.fragSwipeRefresh.setRefreshing(true);
    }

    private void initView() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GankIO.GankIO(getString(R.string.gank_welfare), 20, 1, new GankIO.IGankResponse<List<GankIO.ResultsBean>>() {
                    @Override
                    public void onData(List<GankIO.ResultsBean> data) {
                        mMeiZhis = data;
                        mAdapter = new MeiZhiAdapter(mBinding.getRoot().getContext(), mMeiZhis);
                        mBinding.fragRecycler.setAdapter(mAdapter);
                        mBinding.fragSwipeRefresh.setRefreshing(false);
                    }

                });
            }
        }, 1500);

        mBinding.fragRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (AppUtil.isSlideToBottom(recyclerView) && mIsFirstTimeTouchBottom) {
                    mIsFirstTimeTouchBottom = false; // 未刷新到数据不能再次刷新
                    mBinding.fragSwipeRefresh.setRefreshing(true);
                    mPage += 1;

                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GankIO.GankIO(getString(R.string.gank_welfare), mMeiZhis.size(), 1, new GankIO.IGankResponse<List<GankIO.ResultsBean>>() {
                    @Override
                    public void onData(List<GankIO.ResultsBean> data) {
                        mMeiZhis.clear();
                        for (GankIO.ResultsBean meiZhi : data) {
                            mMeiZhis.add(meiZhi);
                        }
                        mAdapter.notifyDataSetChanged();
                        mBinding.fragSwipeRefresh.setRefreshing(false);
                    }

                });

            }
        }, 1000);
    }


    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GankIO.GankIO(getString(R.string.gank_welfare), 15, mPage, new GankIO.IGankResponse<List<GankIO.ResultsBean>>() {
                    @Override
                    public void onData(List<GankIO.ResultsBean> data) {
                        for (GankIO.ResultsBean meiZhi : data) {
                            mMeiZhis.add(meiZhi);
                        }
                        mBinding.fragSwipeRefresh.setRefreshing(false);
                        mIsFirstTimeTouchBottom = true;
                    }

                });
            }
        }, 1000);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
}
