package com.modularization.app.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
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
import com.modularization.common.imageloader.IImageLoader;
import com.modularization.common.imageloader.universal.UniversalManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseAdapter extends BaseAdapter
{
    private static final int CARD_COUNT = 4;
    private static final int CARD_TYPE_ONE = 1;
    private static final int CARD_TYPE_TWO = 2;

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
            switch (type)
            {
                case CARD_TYPE_TWO:
                case CARD_TYPE_ONE:
                {
                    mViewHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.listitem_home_one, parent, false);
                    initConvertView(mViewHolder, convertView);
                    break;
                }
            }
            if (convertView != null)
                convertView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        switch (type)
        {
            case CARD_TYPE_ONE:
            case CARD_TYPE_TWO:
            {
                mViewHolder.mTitleView.setText(body.title);
                mViewHolder.mInfoView.setText(body.info.concat(mContext.getString(R.string.module_app_day_before)));
                mViewHolder.mFooterView.setText(body.text);
                mViewHolder.mPriceView.setText(body.price);
                mViewHolder.mFromView.setText(body.from);
                mViewHolder.mLikeView.setText(mContext.getString(R.string.module_app_likes).concat(body.zan));
                mImageLoader.displayImage(null, mViewHolder.mLogoView, body.logo);
                mImageLoader.displayImage(null, mViewHolder.mProductView, body.url.get(0));
                break;
            }
        }
        return convertView;
    }

    private void initConvertView(ViewHolder holder, View convertView)
    {
        holder.mLogoView = convertView.findViewById(R.id.item_logo_view);
        holder.mTitleView = convertView.findViewById(R.id.item_title_view);
        holder.mInfoView = convertView.findViewById(R.id.item_info_view);
        holder.mFooterView = convertView.findViewById(R.id.item_footer_view);
        holder.mPriceView = convertView.findViewById(R.id.item_price_view);
        holder.mFromView = convertView.findViewById(R.id.item_from_view);
        holder.mLikeView = convertView.findViewById(R.id.item_like_view);
        holder.mProductLayout = convertView.findViewById(R.id.product_photo_layout);
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
