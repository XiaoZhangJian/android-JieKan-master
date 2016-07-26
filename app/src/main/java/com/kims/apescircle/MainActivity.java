package com.kims.apescircle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.kims.apescircle.adapter.DividerItemDecoration;
import com.kims.apescircle.adapter.MyAdapter;
import com.kims.apescircle.entity.ApesData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    protected Toolbar mToolbar;
    protected RecyclerView mRecyclerView;
    private List<ApesData> mDatas;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

        mAdapter = new MyAdapter(this,mDatas);
        mRecyclerView.setAdapter(mAdapter);
        // 设置 RecyclerView布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 设置RecyclerView分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

    }

    @Override
    public void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_list_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initData() {
        mDatas = new ArrayList<>();
        ApesData apesData = new ApesData();
        for (int i = 0; i < 10 ; i ++){
            apesData.tvTitle = "IOS";
            mDatas.add(apesData);
        }

//        mDatas = new ArrayList<>();
//        ApesData data = new ApesData();
//        for (int i = 0 ; i <= 10; i++){
//            data.tvTitle = "kims"+i;
//            mDatas.add(data);
//        }

    }
}
