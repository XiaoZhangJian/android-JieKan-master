package com.kims.apescircle.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.bumptech.glide.Glide;
import com.kims.apescircle.R;
import com.kims.apescircle.databinding.PhotoFlowActivityBinding;
import com.kims.apescircle.ui.base.AppBaseActivity;
import com.kims.apescircle.utils.ImmersiveUtil;
import com.kims.apescircle.utils.InOutAnimationUtils;

import ooo.oxo.library.widget.PullBackLayout;

/**
 * Created by zhangjian on 2017/5/24.
 */

public class PhotoFlowActivity extends AppBaseActivity implements PullBackLayout.Callback {

    private PhotoFlowActivityBinding binding;
    private ColorDrawable background;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fade();
        binding = DataBindingUtil.setContentView(this, R.layout.photo_flow_activity);
        binding.actFlowPullBack.setCallback(this);
        String url = getIntent().getStringExtra("imgUrl");
        Glide.with(this)
                .load(url)
                .fitCenter()
                .into(binding.actFlowImg);
        background = new ColorDrawable(Color.BLACK);
        binding.getRoot().setBackground(background);


    }


    @Override
    public void onPullStart() {
        ImmersiveUtil.exit(this);
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        background.setAlpha((int) (0xff * (1f - progress)));
    }


    @Override
    public void onPullCancel() {
        ImmersiveUtil.exit(this);
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();

    }

    public ScaleAnimation move() {

        final ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//设置动画持续时间
        //设置动画结束后效果保留
        animation.setFillAfter(false);
        //控制动画先慢后快
        animation.setInterpolator(new AccelerateInterpolator());

        return animation;
    }


}
