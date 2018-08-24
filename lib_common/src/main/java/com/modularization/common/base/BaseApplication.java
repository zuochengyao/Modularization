package com.modularization.common.base;

import android.app.Application;

/**
 * @author 左程耀
 *
 * app程序的入口
 */
public class BaseApplication extends Application
{
    private static BaseApplication mApplication;

    public static BaseApplication getInstance()
    {
        return mApplication;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApplication = this;
        initApp();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
    }

    private void initApp()
    {
    }

}
