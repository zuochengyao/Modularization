package com.modularization.common.imageloader.universal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.modularization.common.R;
import com.modularization.common.imageloader.IImageLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class UniversalManager implements IImageLoader
{
    private static final int THREAD_POOL_COUNT = 4; // UniversalImageLoader最大线程数
    private static final int THREAD_PRIORITY = 2; // 图片加载优先级
    private static final int CACHE_MEMORY_SIZE = 50 * 1024 * 1024; // 最大缓存图片数
    private static final int CACHE_DISK_SIZE = 50 * 1024 * 1024; // 最大缓存图片数
    private static final int TIME_OUT_CONNECTION = 5 * 1000; // 连接超时时间
    private static final int TIME_OUT_READ = 30 * 1000; // 读取超时时间

    private static ImageLoader mImageLoader;
    private Context mContext;

    //@SuppressLint("StaticFieldLeak")
    private static volatile UniversalManager mInstance;

    private UniversalManager(Context applicationContext)
    {
        this.mContext = applicationContext;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(mContext)
                .threadPoolSize(THREAD_POOL_COUNT) // 最大线程数
                .threadPriority(Thread.NORM_PRIORITY - THREAD_PRIORITY)
                .denyCacheImageMultipleSizesInMemory() // 防止缓存多尺寸的图片到内存中
                .memoryCache(new LruMemoryCache(CACHE_MEMORY_SIZE)) // 使用LRU内存缓存
                .diskCacheSize(CACHE_DISK_SIZE) // 硬盘缓存大小
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 使用MD5命名文件
                .defaultDisplayImageOptions((DisplayImageOptions) getDefaultOptions()) // 默认图片加载options
                .imageDownloader(new BaseImageDownloader(mContext, TIME_OUT_CONNECTION, TIME_OUT_READ)) // 图片下载器
                .writeDebugLogs() // debug模式下输出日志
                .build();

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(configuration);
    }

    public static UniversalManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (UniversalManager.class)
            {
                if (mInstance == null)
                    mInstance = new UniversalManager(context.getApplicationContext());
            }
        }
        return mInstance;
    }

    @Override
    public Object getDefaultOptions()
    {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.image_loading) // 图片地址为空时显示
                .showImageOnFail(R.drawable.image_failed) // 图片加载失败时显示
                .cacheInMemory(true) // 设置可以缓存在内存中
                .cacheOnDisk(true) // 设置可以缓存在硬盘中
                .bitmapConfig(Bitmap.Config.RGB_565) // 解码类型
                .decodingOptions(new BitmapFactory.Options()) // 图片解码配置
                .build();
    }

    @Override
    public void clearCache()
    {
        mImageLoader.clearDiskCache();
        mImageLoader.clearMemoryCache();
    }

    @Override
    public void displayImage(String uri, ImageView view)
    {
        displayImage(uri, view, getDefaultOptions());
    }

    @Override
    public void displayImage(String uri, ImageView view, Object options)
    {
        if (mImageLoader != null)
            mImageLoader.displayImage(uri, view, (DisplayImageOptions) options, new SimpleImageLoadingListener());
    }

}
