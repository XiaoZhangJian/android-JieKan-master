package com.kims.jiekan.ui;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.kims.jiekan.BuildConfig;
import com.kims.jiekan.R;
import com.kims.jiekan.databinding.AboutActivityBinding;
import com.kims.jiekan.ui.base.AppBaseActivity;

/**
 * Created by zhangjian on 2017/5/7.
 */

public class AboutActivity extends AppBaseActivity {

    private AboutActivityBinding mAboutBinding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 允许使用transitions
        explode();

        mAboutBinding = DataBindingUtil.setContentView(this, R.layout.about_activity);
        setUpVersionName();
        mAboutBinding.aboutAppbarCollapsingToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mAboutBinding.aboutToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAboutBinding.aboutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.onBackPressed();
            }
        });
    }

    private void setUpVersionName() {
        mAboutBinding.aboutTextVersion.setText("Version " + BuildConfig.VERSION_NAME);
    }
}
