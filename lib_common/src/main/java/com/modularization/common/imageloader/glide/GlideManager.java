package com.modularization.common.imageloader.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.modularization.common.R;
import com.modularization.common.imageloader.IImageLoader;

public class GlideManager implements IImageLoader
{
    private Context mContext;
    private RequestOptions options;
    private static volatile GlideManager mInstance;

    private GlideManager(Context applicationContext)
    {
        this.mContext = applicationContext;
    }

    public static GlideManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (GlideManager.class)
            {
                if (mInstance == null)
                    mInstance = new GlideManager(context.getApplicationContext());
            }
        }
        return mInstance;
    }

    @Override
    public void displayImage(String uri, ImageView view, Object options)
    {
        Glide.with(mContext).load(uri).apply((RequestOptions) options).thumbnail(0.5f).into(view);
    }

    @Override
    public Object getDefaultOptions()
    {
        return new RequestOptions()
                .placeholder(R.drawable.image_loading) // 占位符：当请求正在执行时被展示
                .error(R.drawable.image_failed) // 错误符：在请求永久性失败时展示
                .fallback(R.drawable.image_failed) // 后备回调符：在请求的url/model为 null 时展示
                .override(Target.SIZE_ORIGINAL) // 缩略图size
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.NORMAL);
    }

    @Override
    public void clearCache()
    {
        Glide.get(mContext).clearDiskCache();
        Glide.get(mContext).clearMemory();
    }
}
