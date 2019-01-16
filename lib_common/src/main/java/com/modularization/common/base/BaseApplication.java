package com.modularization.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.modularization.common.util.Common;
import com.modularization.common.util.Log;

/**
 * @author 左程耀
 * app程序的入口
 */
public class BaseApplication extends Application
{
    private static final String TAG = "MODULARIZATION";
    private static final String APP_SIGN = "";
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
        if (!isOwnApp())
        {
            Log.e(TAG, "IS NOT OWN APP!!!");
            // Process.killProcess(Process.myPid());
        }
        if (Common.isDebug())
        {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(mApplication);
    }

    public boolean isOwnApp()
    {
        String sign = Common.getSignature(this);
        return APP_SIGN.equals(sign);
    }
}
