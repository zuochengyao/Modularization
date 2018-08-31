package com.modularization.app.view.fragment.main;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.modularization.app.R;
import com.modularization.app.adapter.CourseAdapter;
import com.modularization.app.controller.HttpController;
import com.modularization.app.model.recommend.Recommend;
import com.modularization.app.model.recommend.RecommendBody;
import com.modularization.app.model.search.Search;
import com.modularization.app.view.widget.ListRecommendHeader;
import com.modularization.common.base.BaseFragment;
import com.modularization.common.okhttp.exception.OkHttpException;
import com.modularization.common.okhttp.listener.OkHttpListener;
import com.modularization.common.util.Log;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements OkHttpListener, View.OnClickListener
{
    private ListView mDataList;
    private CourseAdapter mCourseAdapter;
    private ImageView mMenu;
    private ImageView mScanQRCode;
    private TextView mSearchPanel;
    private ImageView mLoading;
    private ListRecommendHeader recommendHeader;

    private Recommend mRecommend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContentView = inflater.inflate(R.layout.fragment_home, container, false);
        doInitView();
        return mContentView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if (recommendHeader != null)
            recommendHeader.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (recommendHeader != null)
            recommendHeader.onStop();
    }

    private void doInitView()
    {
        mDataList = $(R.id.list_data);
        mMenu = $(R.id.image_menu);
        mScanQRCode = $(R.id.image_scan_qrcode);
        mScanQRCode.setOnClickListener(this);
        mSearchPanel = $(R.id.text_search);
        mLoading = $(R.id.image_loading);
        // 启动loading数据动画
        AnimationDrawable anim = (AnimationDrawable) mLoading.getDrawable();
        anim.start();
        requestData();
    }

    private void requestData()
    {
        HttpController.loadRecommend(this, Recommend.class);
    }

    private void refreshUI(boolean isSuccess)
    {
        mLoading.setVisibility(View.GONE);
        if (isSuccess)
        {
            List<RecommendBody> dataList = mRecommend.data.list;
            if (dataList != null && dataList.size() > 0)
            {
                mDataList.setVisibility(View.VISIBLE);
                recommendHeader = new ListRecommendHeader(mContext, mRecommend.data.head);
                mDataList.addHeaderView(recommendHeader);
                mCourseAdapter = new CourseAdapter(mContext, dataList);
                mDataList.setAdapter(mCourseAdapter);
            }
        }
        else
        {

        }
    }

    @Override
    public void onSuccess(int requestId, Object response)
    {
        Log.i(getHostActivity().TAG, "onSuccess:data = " + response);
        switch (requestId)
        {
            case HttpController.REQUEST_ID_RECOMMEND:
                mRecommend = (Recommend) response;
                break;
            case HttpController.REQUEST_ID_SEARCH:
                Search search = (Search) response;
                break;
        }
        refreshUI(true);

    }

    @Override
    public void onFailure(int requestId, OkHttpException reason)
    {
        int code = reason.getErrorCode();
        String msg = reason.getErrorMsg().toString();
        Log.i(getHostActivity().TAG, "onFailure:" + code + ", " + msg);
        refreshUI(false);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.image_scan_qrcode:
            {
                ARouter.getInstance().build("/qrcode/CaptureActivity").navigation();
                break;
            }
        }
    }
}
