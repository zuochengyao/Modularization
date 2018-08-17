package com.modularization.common.util;

import android.annotation.SuppressLint;

import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtil
{
    /**
     * @return trust all the https point
     */
    public static SSLSocketFactory getSSLSocketFactory()
    {
        SSLSocketFactory ssfFactory = null;
        try
        {
            // 与服务保持一致的算法类型，可选SSL or TSL
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { getX509TrustManager() }, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    /**
     * @return 生成一个信任管理器
     */
    @SuppressLint("TrustAllX509TrustManager")
    public static X509TrustManager getX509TrustManager()
    {
        return new X509TrustManager()
        {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
            {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
            {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers()
            {
                return new java.security.cert.X509Certificate[]{ };
            }
        };
    }
}
