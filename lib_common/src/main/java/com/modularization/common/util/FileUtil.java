package com.modularization.common.util;

import android.content.Context;

import java.io.InputStream;

public class FileUtil
{
    public static byte[] readFile(Context context, int rawId)
    {
        byte[] data = null;
        try
        {
            InputStream in = context.getResources().openRawResource(rawId);
            data = new byte[in.available()];
            in.read(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }
}
