package com.modularization.common.util;

public class Log
{
    public static void i(String tag, String msg)
    {
        android.util.Log.i(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        android.util.Log.e(tag, msg);
    }
}
