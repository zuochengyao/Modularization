package com.modularization.imageloader;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public interface IImageLoader
{
    void displayImage(String uri, ImageView view, DisplayImageOptions options, ImageLoadingListener listener);
}
