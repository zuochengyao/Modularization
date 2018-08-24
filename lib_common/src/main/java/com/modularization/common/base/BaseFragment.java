package com.modularization.common.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 为所有fragment提供公共的行为与事件
 */
public class BaseFragment extends Fragment
{
    protected BaseActivity mContext;
    protected View mContentView;

    // region Fragment生命周期
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
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

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    // endregion

    /**
     * 获取宿主Activity
     */
    protected BaseActivity getHostActivity()
    {
        return mContext;
    }

    /**
     * equals findViewById
     */
    protected <T extends View> T $(@IdRes int resId)
    {
        if (mContentView == null)
            throw new NullPointerException("The current view is NULL!");
        return mContentView.findViewById(resId);
    }
}
