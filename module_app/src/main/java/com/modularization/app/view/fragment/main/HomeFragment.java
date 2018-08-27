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

import com.modularization.app.R;
import com.modularization.app.controller.HttpController;
import com.modularization.app.model.recommend.Recommend;
import com.modularization.app.model.search.Search;
import com.modularization.common.base.BaseFragment;
import com.modularization.common.okhttp.exception.OkHttpException;
import com.modularization.common.okhttp.listener.OkHttpListener;
import com.modularization.common.util.Log;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements OkHttpListener
{
    private ListView mDataList;
    private ImageView mMenu;
    private ImageView mScanQRCode;
    private TextView mSearchPanel;
    private ImageView mLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        doInitView();
        return mContentView;
    }

    private void doInitView()
    {
        mDataList = $(R.id.list_data);
        mMenu = $(R.id.image_menu);
        mScanQRCode = $(R.id.image_scan_qrcode);
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
                Recommend recommend = (Recommend) response;
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
}
