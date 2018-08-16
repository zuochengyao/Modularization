package com.modularization.common.base;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 为所有Activity提供公共的行为与事件
 */
public class BaseActivity extends AppCompatActivity
{
    public String TAG;

    // region Activity生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
    // endregion

    // region Methods

    /**
     * equals findViewById
     */
    protected <T extends View> T $(@IdRes int resId)
    {
        return findViewById(resId);
    }
    // endregion
}
