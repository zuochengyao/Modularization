package com.modularization.common.imageloader;

import android.widget.ImageView;

public interface IImageLoader
{
    /**
     * 显示图片
     * @param uri 图片uri
     * @param view 待显示的ImageView
     * @param options options
     */
    void displayImage(String uri, ImageView view, Object options);

    Object getDefaultOptions();

    void clear();
}
