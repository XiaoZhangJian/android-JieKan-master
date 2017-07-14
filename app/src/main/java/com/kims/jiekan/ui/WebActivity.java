package com.kims.jiekan.ui;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.kims.jiekan.R;
import com.kims.jiekan.databinding.WebActivityBinding;
import com.kims.jiekan.utils.ShareUtil;
import com.kims.jiekan.ui.base.AppBaseActivity;

/**
 * Created by zhangjian on 2017/5/7.
 */

public class WebActivity extends AppBaseActivity {

    private WebActivityBinding mWebBinding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final String url = getIntent().getStringExtra("URL");
        final String title = getIntent().getStringExtra("TITLE");
        mWebBinding = DataBindingUtil.setContentView(this, R.layout.web_activity);
        setSupportActionBar(mWebBinding.actWebToolbar.toolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebBinding.actWebToolbar.toolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.this.onBackPressed();
            }
        });

        mWebBinding.actWebToolbar.toolbarViewTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView = new TextView(WebActivity.this);
                textView.setTextAppearance(WebActivity.this, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                }, 100);
                return textView;
            }
        });
        mWebBinding.actWebToolbar.toolbarViewTitle.setInAnimation(this, android.R.anim.fade_in);
        mWebBinding.actWebToolbar.toolbarViewTitle.setOutAnimation(this, android.R.anim.fade_out);
        mWebBinding.actWebToolbar.toolbarViewTitle.setText(title);


        //启用支持javascript
        WebSettings settings = mWebBinding.actWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebBinding.actWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }


        });

        mWebBinding.actWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mWebBinding.actWebProgressbar.setProgress(newProgress);
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    mWebBinding.actWebProgressbar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mWebBinding.actWebProgressbar.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mWebBinding.actWebToolbar.toolbarViewTitle.setText(title);
            }
        });
        mWebBinding.actWebView.loadUrl(url);


        mWebBinding.actWebToolbar.toolbarView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_share:
                        ShareUtil.getInstance().shareText(WebActivity.this, title + "\n" + url);
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }


    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebBinding.actWebView.canGoBack()) {
                mWebBinding.actWebView.goBack();//返回上一页面
                return true;
            } else {
                WebActivity.this.onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
