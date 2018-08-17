package com.modularization.common.okhttp.request;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 左程耀
 *
 * 封装所有请求的参数到Map中
 */
public class OkHttpRequestParam
{
    // Map的Value类型用Object，是为了在请求的时候上传
    private Map<String, Object> params = new ConcurrentHashMap<>();

    public OkHttpRequestParam()
    {
        this(null);
    }

    public OkHttpRequestParam(Map<String, Object> source)
    {
        if (source != null)
        {
            for (Map.Entry<String, Object> entry : source.entrySet())
                put(entry.getKey(), entry.getValue());
        }
    }

    public void put(@NonNull String key, @NonNull Object value)
    {
        params.put(key, value);
    }

    public void clear()
    {
        params.clear();
    }

    public int size()
    {
        return params.size();
    }

    public Set<Map.Entry<String, Object>> entrySet()
    {
        return params.entrySet();
    }

    public boolean hasParams()
    {
        return size() > 0;
    }
}
