package com.modularization.imageloader.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideManager
{
    public void test(Context context, String url, int resId, int errorId, ImageView view)
    {
        Glide.with(context).load(url).into(view);
    }
}
