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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.modularization.app.R;
import com.modularization.app.controller.HttpController;
import com.modularization.app.model.recommend.Recommend;
import com.modularization.common.base.BaseFragment;
import com.modularization.common.okhttp.exception.OkHttpException;
import com.modularization.common.okhttp.listener.OkHttpHandler;
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

    private OkHttpHandler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        doInitView();
        return mContentView;
    }

    @Override
    public void onSuccess(Object response)
    {
        Gson gson = new GsonBuilder().create();
        Recommend data = gson.fromJson(response.toString(), Recommend.class);
        Log.i(getHostActivity().TAG, "onSuccess:data = " + (data.data.list == null));
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Object reason)
    {
        int code = ((OkHttpException) reason).getErrorCode();
        String msg = ((OkHttpException) reason).getErrorMsg().toString();
        Log.i(getHostActivity().TAG, "onFailure:" + code + ", " + msg);
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
        mHandler = new OkHttpHandler(this);
        requestData();
    }

    private void requestData()
    {
        HttpController.loadRecommend(mHandler);
    }

}
