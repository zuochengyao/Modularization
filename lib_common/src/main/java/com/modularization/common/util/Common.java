package com.modularization.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.modularization.common.BuildConfig;

public class Common
{
    public static boolean isDebug()
    {
        return BuildConfig.DEBUG;
    }

    public static String getSignature(Context context)
    {
        String sign = null;
        try
        {
            // 通过包管理器获得指定包名包含签名的包信息
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            // 通过返回的包信息获得签名数组
            Signature[] signatures = packageInfo.signatures;
            StringBuilder builder = new StringBuilder();
            for (Signature signature : signatures)
            {
                builder.append(signature.toCharsString());
            }
            sign = builder.toString();
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return sign;
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
