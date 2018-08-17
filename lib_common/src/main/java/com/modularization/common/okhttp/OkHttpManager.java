package com.modularization.common.okhttp;

import com.modularization.common.okhttp.response.OkHttpJsonCallback;
import com.modularization.common.util.HttpsUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 左程耀
 * 网络请求封装类，包括：请求发送、参数配置、https支持等
 */
public class OkHttpManager
{
    /** 连接超时时长 单位：秒 */
    private static final int TIMEOUT_CONNECT = 30;
    /** 读 超时时长 单位：秒 */
    private static final int TIMEOUT_READ = 30;
    /** 写 超时时长 单位：秒 */
    private static final int TIMEOUT_WRITE = 30;

    private static okhttp3.OkHttpClient mOkHttpClient;
    private static volatile OkHttpManager mInstance;

    private OkHttpManager()
    {

    }

    public static OkHttpManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpManager.class)
            {
                if (mInstance == null)
                    mInstance = new OkHttpManager();
            }
        }
        return mInstance;
    }

    static
    {
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS);
        builder.followRedirects(true);
        // support https
        builder.hostnameVerifier(new HostnameVerifier()
        {
            @Override
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }
        });
        // trust all the https point
        builder.sslSocketFactory(HttpsUtil.getSSLSocketFactory(), HttpsUtil.getX509TrustManager());
        mOkHttpClient = builder.build();
    }

    public static Call SyncRequest(Request request, OkHttpJsonCallback callback)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static Response AsyncRequest(Request request) throws IOException
    {
        return mOkHttpClient.newCall(request).execute();
    }
}
