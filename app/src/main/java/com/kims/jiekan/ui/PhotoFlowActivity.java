package com.kims.jiekan.ui;

import android.Manifest;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kims.jiekan.R;
import com.kims.jiekan.databinding.PhotoFlowActivityBinding;
import com.kims.jiekan.ui.base.AppBaseActivity;
import com.kims.jiekan.utils.ImagerLoader;
import com.kims.jiekan.utils.ImmersiveUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import ooo.oxo.library.widget.PullBackLayout;
import rx.functions.Action1;

/**
 * Created by zhangjian on 2017/5/24.
 */

public class PhotoFlowActivity extends AppBaseActivity implements PullBackLayout.Callback {
    private Activity mActivity = PhotoFlowActivity.this;
    private PhotoFlowActivityBinding binding;
    private ColorDrawable background;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fade();
        binding = DataBindingUtil.setContentView(this, R.layout.photo_flow_activity);
        binding.actFlowPullBack.setCallback(this);
        final String url = getIntent().getStringExtra("imgUrl");
        Glide.with(this)
                .load(url)
                .fitCenter()
                .into(binding.actFlowImg);
        background = new ColorDrawable(Color.BLACK);

        binding.getRoot().setBackground(background);


        binding.actFlowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.actFlowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions.getInstance(mActivity)
                        .request(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        ).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            ImagerLoader.getDefault().init(PhotoFlowActivity.this).savePicture(url);
                        } else {
                            //不同意，给提示
                            Toast.makeText(mActivity, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }


    @Override
    public void onPullStart() {
        ImmersiveUtil.exit(this);
        binding.actFlowRl.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onPull(float progress) {

        progress = Math.min(1f, progress * 3f);
        background.setAlpha((int) (0xff * (1f - progress)));
    }


    @Override
    public void onPullCancel() {
        binding.actFlowRl.setVisibility(View.VISIBLE);
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
