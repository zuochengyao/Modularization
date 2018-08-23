package com.modularization.common.imageloader.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.modularization.common.R;

@GlideModule
public class GlideApplication extends AppGlideModule
{
    private static final int CACHE_MEMORY_SIZE = 50 * 1024 * 1024; // 最大缓存图片数
    private static final int CACHE_DISK_SIZE = 50 * 1024 * 1024; // 最大缓存图片数

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder)
    {
        builder.setMemoryCache(new LruResourceCache(CACHE_MEMORY_SIZE));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, CACHE_DISK_SIZE));
        builder.setDefaultRequestOptions(new RequestOptions()
             .placeholder(R.drawable.image_loading) // 占位符：当请求正在执行时被展示
             .error(R.drawable.image_failed) // 错误符：在请求永久性失败时展示
             .fallback(R.drawable.image_failed) // 后备回调符：在请求的url/model为 null 时展示
             .override(Target.SIZE_ORIGINAL) // 缩略图size
             .skipMemoryCache(false)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .priority(Priority.NORMAL)
             .format(DecodeFormat.PREFER_RGB_565));
    }
}
