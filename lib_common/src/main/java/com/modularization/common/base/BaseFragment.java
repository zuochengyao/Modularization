package com.modularization.common.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 为所有fragment提供公共的行为与事件
 */
public class BaseFragment extends Fragment
{
    protected BaseActivity mContext;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.mContext = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    /**
     * 获取宿主Activity
     */
    protected BaseActivity getHostActivity()
    {
        return mContext;
    }
}
