package com.kims.jiekan.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.kims.jiekan.MainAdapter;
import com.kims.jiekan.R;
import com.kims.jiekan.databinding.MainActivityBinding;
import com.kims.jiekan.ui.base.AppBaseActivity;
import com.kims.jiekan.ui.fragment.GankIOFragment;
import com.kims.jiekan.ui.fragment.MeiZhiFragment;
import com.kims.jiekan.utils.AppUtil;

import java.util.ArrayList;


/**
 * Created by zhangjian on 2017/5/6.
 */

public class MainActivity extends AppBaseActivity {
    private MainActivityBinding mMainBinding;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        initFragment();
        mMainBinding.actMainViewPager.setOffscreenPageLimit(4);
        mMainBinding.actMainViewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), AppUtil.tabLayoutName(), mFragments));
        mMainBinding.actMainTabLayout.getTabLayout().setTabMode(TabLayout.MODE_FIXED);
        mMainBinding.actMainTabLayout.setTitle(getString(R.string.app_name))
                .setBackEnable(false)
                .setImageArray(AppUtil.imageArray())
                .setupWithViewPager(mMainBinding.actMainViewPager);
    }


    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(MeiZhiFragment.getDefault());
        mFragments.add(GankIOFragment.getDefault(getString(R.string.gank_android)));
        mFragments.add(GankIOFragment.getDefault(getString(R.string.gank_ios)));
        mFragments.add(GankIOFragment.getDefault(getString(R.string.gank_js)));
        mFragments.add(GankIOFragment.getDefault(getString(R.string.gank_expand)));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(1).setVisible(false);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
