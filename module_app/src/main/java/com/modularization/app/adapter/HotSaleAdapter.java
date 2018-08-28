package com.modularization.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.modularization.app.R;
import com.modularization.app.model.recommend.RecommendBody;
import com.modularization.common.imageloader.IImageLoader;
import com.modularization.common.imageloader.universal.UniversalManager;

import java.util.List;

public class HotSaleAdapter extends PagerAdapter
{
    private Context mContext;
    private List<RecommendBody> mData;
    private LayoutInflater mInflate;
    private IImageLoader mImageLoader;

    public HotSaleAdapter(Context context, List<RecommendBody> list)
    {
        mContext = context;
        mData = list;
        mInflate = LayoutInflater.from(mContext);
        mImageLoader = UniversalManager.getInstance(mContext);
    }

    @Override
    public int getCount()
    {
        // 无限循环效果
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        // position % mData.size()：数据重复出现
        final RecommendBody value = mData.get(position % mData.size());
        View rootView = mInflate.inflate(R.layout.listitem_hot_sale, null);
        TextView titleView = rootView.findViewById(R.id.title_view);
        TextView infoView = rootView.findViewById(R.id.info_view);
        TextView gonggaoView = rootView.findViewById(R.id.gonggao_view);
        TextView saleView = rootView.findViewById(R.id.sale_num_view);
        ImageView[] imageViews = new ImageView[3];
        imageViews[0] = rootView.findViewById(R.id.image_one);
        imageViews[1] = rootView.findViewById(R.id.image_two);
        imageViews[2] = rootView.findViewById(R.id.image_three);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, CourseDetailActivity.class);
//                intent.putExtra(CourseDetailActivity.COURSE_ID, value.adid);
//                mContext.startActivity(intent);
            }
        });

        titleView.setText(value.title);
        infoView.setText(value.price);
        gonggaoView.setText(value.info);
        saleView.setText(value.text);
        for (int i = 0; i < imageViews.length; i++) {
            mImageLoader.displayImage(value.url.get(i), imageViews[i]);
        }
        container.addView(rootView, 0);
        return rootView;
    }
}
