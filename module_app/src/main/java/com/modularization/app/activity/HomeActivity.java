package com.modularization.app.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.modularization.app.R;
import com.modularization.common.base.BaseActivity;

/**
 * 创建首页所有的fragment，以及fragment切换
 */
public class HomeActivity extends BaseActivity
{
    private TextView tvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView()
    {
        tvHome = $(R.id.tv_home);
    }

}
