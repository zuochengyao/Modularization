package com.modularization.common.okhttp.listener;

/**
 * @author 左程耀
 *
 * 处理json数据转换为实体对象
 */
public class OkHttpHandler
{
    public OkHttpListener mListener;
    // 待转换对象class类型
    public Class<?> mClass;
    public int mRequestId;

    public OkHttpHandler(OkHttpListener listener, int requestId)
    {
        this(listener, requestId, null);
    }

    public OkHttpHandler(OkHttpListener listener, int requestId, Class<?> clazz)
    {
        this.mListener = listener;
        this.mRequestId  = requestId;
        this.mClass = clazz;
    }
}
