package com.modularization.common.okhttp.listener;

/**
 * @author 左程耀
 *
 * 自定义事件监听
 */
public interface OkHttpListener
{
    /**
     * 请求成功响应回调事件
     * @param response 响应对象
     */
    void onSuccess(Object response);

    /**
     * 请求失败响应回调事件
     * @param reason 失败原因
     */
    void onFailure(Object reason);
}
