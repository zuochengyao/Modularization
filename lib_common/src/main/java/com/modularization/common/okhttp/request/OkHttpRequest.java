package com.modularization.common.okhttp.request;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author 左程耀
 *
 * 网络请求封装类
 */
public class OkHttpRequest
{
    /**
     * @param baseUrl post url
     * @param params post params
     * @return 返回创建好的一个post请求
     */
    public static Request createPostRequest(@NonNull final String baseUrl, @NonNull OkHttpRequestParam params)
    {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            if (entry.getValue() instanceof String)
                builder.add(entry.getKey(), entry.getValue().toString());
        }
        FormBody formBody = builder.build();
        return new Request.Builder().url(baseUrl).post(formBody).build();
    }

    public static Request createFilePostRequest(@NonNull final String baseUrl, @NonNull OkHttpRequestParam params, String contentType)
    {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            Object value = entry.getValue();
            if (value instanceof File)
            {
                File file = (File) value;
                builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse(contentType), file));
            }
            else if (value instanceof byte[])
            {
                byte[] data = (byte[]) value;
                builder.addFormDataPart(entry.getKey(), "faceid_image", RequestBody.create(MediaType.parse(contentType), data));
            }
            else
                builder.addFormDataPart(entry.getKey(), value.toString());
        }
        return new Request.Builder().url(baseUrl).post(builder.build()).build();
    }

    /**
     * @param fullUrl get url
     * @return 返回创建好的一个get请求
     */
    public static Request createGetRequest(@NonNull final String fullUrl)
    {
        return new Request.Builder().url(fullUrl).get().build();
    }

    /**
     * @param baseUrl get url
     * @param params get params
     * @return 返回创建好的一个get请求
     */
    public static Request createGetRequest(@NonNull final String baseUrl, @NonNull OkHttpRequestParam params)
    {
        StringBuilder sb = new StringBuilder(baseUrl).append("?");
        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            if (entry.getValue() instanceof String)
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
        }
        return createGetRequest(sb.toString().substring(0, sb.length() - 1));
    }
}
