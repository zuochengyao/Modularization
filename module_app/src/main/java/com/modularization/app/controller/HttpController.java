package com.modularization.app.controller;

import com.modularization.common.okhttp.OkHttpManager;
import com.modularization.common.okhttp.listener.OkHttpHandler;
import com.modularization.common.okhttp.listener.OkHttpListener;
import com.modularization.common.okhttp.request.OkHttpRequest;
import com.modularization.common.okhttp.request.OkHttpRequestParam;
import com.modularization.common.okhttp.response.OkHttpJsonCallback;

public class HttpController
{
    public static final int REQUEST_ID_RECOMMEND = 1001;
    public static final int REQUEST_ID_SEARCH = 1002;

    private static final String ROOT_URL = "http://10.155.2.171:8080/module";
    /**
     * 请求本地产品列表
     */
    public static String PRODUCT_LIST = ROOT_URL + "/home/search.action";
    /**
     * 本地产品列表更新时间措请求
     */
    public static String PRODUCT_LATESAT_UPDATE = ROOT_URL + "/fund/upsearch.action";
    /**
     * 登陆接口
     */
    public static String LOGIN = ROOT_URL + "/user/login_phone.action";
    /**
     * 检查更新接口
     */
    public static String CHECK_UPDATE = ROOT_URL + "/config/check_update.action";
    /**
     * 首页产品请求接口
     */
    public static String HOME_RECOMMEND = ROOT_URL + "/home/recommend.action";
    /**
     * 课程详情接口
     */
    public static String COURSE_DETAIL = ROOT_URL + "/product/course_detail.action";

    /**
     * http://news.at.zhihu.com/api/4/news/before/20170225
     */
    private static final String ZHIHU_DAILY_BEFORE_MESSAGE = "http://news.at.zhihu.com/api/4/news/before/";

    /**
     * http://news-at.zhihu.com/api/4/news/9241375
     */
    private static final String ZHIHU_DAILY_BEFORE_MESSAGE_DETAIL = "http://news-at.zhihu.com/api/4/news/9241375";

    public static void loadRecommend(OkHttpListener listener)
    {
        loadRecommend(listener, null);
    }

    public static void loadRecommend(OkHttpListener listener, Class mClass)
    {
        OkHttpManager.getInstance().SyncRequest(OkHttpRequest.createPostRequest(HOME_RECOMMEND, new OkHttpRequestParam()), new OkHttpJsonCallback(new OkHttpHandler(listener, REQUEST_ID_RECOMMEND, mClass)));
    }

    public static void loadSearch(OkHttpListener listener)
    {
        loadSearch(listener, null);
    }

    public static void loadSearch(OkHttpListener listener, Class mClass)
    {
        OkHttpManager.getInstance().SyncRequest(OkHttpRequest.createPostRequest(PRODUCT_LIST, new OkHttpRequestParam()), new OkHttpJsonCallback(new OkHttpHandler(listener, REQUEST_ID_SEARCH, mClass)));
    }
}
