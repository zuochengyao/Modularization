package com.modularization.common.util;

import android.content.Context;

import com.modularization.common.BuildConfig;

public class Common
{
    public static boolean isDebug()
    {
        return BuildConfig.DEBUG;
    }

    public static int dp2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    public static int px2dp(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }
}
