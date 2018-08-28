package com.modularization.app.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.modularization.app.R;
import com.modularization.app.model.recommend.RecommendHead;
import com.modularization.common.imageloader.IImageLoader;
import com.modularization.common.imageloader.glide.GlideManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

public class ListRecommendHeader extends RelativeLayout
{
    private Context mContext;
    private RecommendHead mHead;
    private IImageLoader mImageLoader;
    private LayoutInflater mInflater;

    private RelativeLayout mRootView;
    private Banner mBanner;

    public ListRecommendHeader(Context context, RecommendHead head)
    {
        this(context, null, head);
    }

    public ListRecommendHeader(Context context, AttributeSet attrs, RecommendHead head)
    {
        super(context, attrs);
        this.mContext = context;
        this.mHead = head;
        this.mImageLoader = GlideManager.getInstance(mContext);
        this.mInflater = LayoutInflater.from(mContext);
        doInitView();
    }

    private void doInitView()
    {
        mRootView = (RelativeLayout) mInflater.inflate(R.layout.listview_header_recommend, this);
        mBanner = mRootView.findViewById(R.id.banner);
        mBanner.setImageLoader(new ImageLoader()
        {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView)
            {
                mImageLoader.displayImage(path.toString(), imageView);
            }
        }).setImages(mHead.ads).isAutoPlay(true).setDelayTime(3000).setBannerAnimation(Transformer.Default).setBannerStyle(BannerConfig.CIRCLE_INDICATOR).start();
    }

    public void onStart()
    {
        if (mBanner != null)
            mBanner.startAutoPlay();
    }

    public void onStop()
    {
        if (mBanner != null)
            mBanner.stopAutoPlay();
    }

}
