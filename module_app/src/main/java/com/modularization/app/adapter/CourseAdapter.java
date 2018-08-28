package com.modularization.app.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.modularization.app.R;
import com.modularization.app.model.recommend.RecommendBody;
import com.modularization.app.util.Utils;
import com.modularization.common.imageloader.IImageLoader;
import com.modularization.common.imageloader.universal.UniversalManager;
import com.modularization.common.util.Common;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseAdapter extends BaseAdapter
{
    private static final int CARD_COUNT = 4;
    private static final int CARD_TYPE_ZERO = 0;
    private static final int CARD_TYPE_MULTI_PICTURE = 1;
    private static final int CARD_TYPE_SINGLE_PICTURE = 2;
    private static final int CARD_TYPE_VIEW_PAGER = 3;

    private Context mContext;
    private LayoutInflater mInflater;

    private List<RecommendBody> mData;
    // 异步图片加载
    private IImageLoader mImageLoader;
    private ViewHolder mViewHolder;

    /**
     * 构造方法
     * @param context 上下文
     * @param data 数据
     */
    public CourseAdapter(Context context, List<RecommendBody> data)
    {
        this.mContext = context;
        this.mData = data;
        this.mInflater = LayoutInflater.from(mContext);
        mImageLoader = UniversalManager.getInstance(mContext);
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * @return 列表中item的类型数
     */
    @Override
    public int getViewTypeCount()
    {
        return CARD_COUNT;
    }

    /**
     * 通知Adapter使用哪种类型的item去加载数据
     * @param position position
     * @return 返回每一列数据item的类型
     */
    @Override
    public int getItemViewType(int position)
    {
        RecommendBody body = (RecommendBody) getItem(position);
        return body.type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // 获取数据的type类型
        int type = getItemViewType(position);
        final RecommendBody body = (RecommendBody) getItem(position);
        if (convertView == null)
        {
            mViewHolder = new ViewHolder();
            switch (type)
            {
                case CARD_TYPE_ZERO:
                case CARD_TYPE_VIEW_PAGER:
                {
                    convertView = mInflater.inflate(R.layout.listitem_view_pager, parent, false);
                    break;
                }
                case CARD_TYPE_SINGLE_PICTURE:
                {
                    convertView = mInflater.inflate(R.layout.listitem_single_picture, parent, false);
                    break;
                }
                case CARD_TYPE_MULTI_PICTURE:
                {
                    convertView = mInflater.inflate(R.layout.listitem_multi_picture, parent, false);
                    break;
                }
            }
            if (convertView != null)
            {
                initConvertView(mViewHolder, convertView, type);
                convertView.setTag(mViewHolder);
            }
        }
        else
            mViewHolder = (ViewHolder) convertView.getTag();
        switch (type)
        {
            case CARD_TYPE_ZERO:
                break;
            case CARD_TYPE_MULTI_PICTURE:
            {
                mViewHolder.mTitleView.setText(body.title);
                mViewHolder.mInfoView.setText(body.info.concat(mContext.getString(R.string.module_app_day_before)));
                mViewHolder.mFooterView.setText(body.text);
                mViewHolder.mPriceView.setText(body.price);
                if (!TextUtils.isEmpty(body.from))
                    mViewHolder.mFromView.setText(body.from);
                if (!TextUtils.isEmpty(body.zan))
                    mViewHolder.mLikeView.setText(mContext.getString(R.string.module_app_likes).concat(body.zan));
                mImageLoader.displayImage(body.logo, mViewHolder.mLogoView, null);
                // 删除已有的图片
                mViewHolder.mProductLayout.removeAllViews();
                for (String imageUrl : body.url)
                    mViewHolder.mProductLayout.addView(createImageView(imageUrl));
                break;
            }
            case CARD_TYPE_SINGLE_PICTURE:
            {
                mViewHolder.mTitleView.setText(body.title);
                mViewHolder.mInfoView.setText(body.info.concat(mContext.getString(R.string.module_app_day_before)));
                mViewHolder.mFooterView.setText(body.text);
                mViewHolder.mPriceView.setText(body.price);
                mViewHolder.mFromView.setText(body.from);
                mViewHolder.mLikeView.setText(mContext.getString(R.string.module_app_likes).concat(body.zan));
                mImageLoader.displayImage(body.logo, mViewHolder.mLogoView, null);
                mImageLoader.displayImage(body.url.get(0), mViewHolder.mProductView, null);
                break;
            }
            case CARD_TYPE_VIEW_PAGER:
            {
                List<RecommendBody> list = Utils.handleData(body);
                mViewHolder.mViewPager.setAdapter(new HotSaleAdapter(mContext, list));
                mViewHolder.mViewPager.setCurrentItem(list.size() * 100);
                break;
            }
        }
        return convertView;
    }

    /**
     * 初始化view
     */
    private void initConvertView(ViewHolder holder, View convertView, int cardType)
    {
        switch (cardType)
        {
            case CARD_TYPE_SINGLE_PICTURE:
            case CARD_TYPE_MULTI_PICTURE:
            {
                holder.mLogoView = convertView.findViewById(R.id.item_logo_view);
                holder.mTitleView = convertView.findViewById(R.id.item_title_view);
                holder.mInfoView = convertView.findViewById(R.id.item_info_view);
                holder.mFooterView = convertView.findViewById(R.id.item_footer_view);
                holder.mPriceView = convertView.findViewById(R.id.item_price_view);
                holder.mFromView = convertView.findViewById(R.id.item_from_view);
                holder.mLikeView = convertView.findViewById(R.id.item_like_view);
                holder.mProductView = convertView.findViewById(R.id.item_product_photo_view);
                holder.mProductLayout = convertView.findViewById(R.id.product_photo_layout);
                break;
            }
            case CARD_TYPE_VIEW_PAGER:
            {
                holder.mViewPager = convertView.findViewById(R.id.item_view_pager);
                holder.mViewPager.setPageMargin(Common.dp2px(mContext, 12));
                break;
            }
        }
    }

    /**
     * 创建ImageView，并显示url图片
     */
    private ImageView createImageView(String imageUrl)
    {
        ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Common.dp2px(mContext, 100), LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = Common.dp2px(mContext, 5);
        imageView.setLayoutParams(params);
        mImageLoader.displayImage(imageUrl, imageView, null);
        return imageView;
    }

    /**
     * 缓存已经创建好的item
     */
    private class ViewHolder
    {
        //所有Card共有属性
        private CircleImageView mLogoView;
        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //Video Card特有属性
        private RelativeLayout mVieoContentLayout;
        private ImageView mShareView;
        //Video Card外所有Card具有属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mLikeView;
        //Card One特有属性
        private LinearLayout mProductLayout;
        //Card Two特有属性
        private ImageView mProductView;
        //Card Three特有属性
        private ViewPager mViewPager;
    }
}
