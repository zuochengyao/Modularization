package com.modularization.common.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.modularization.common.okhttp.exception.OkHttpException;
import com.modularization.common.okhttp.listener.OkHttpHandler;
import com.modularization.common.okhttp.listener.OkHttpListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author 左程耀
 *
 * 处理JSON的回调
 */
public class OkHttpJsonCallback implements Callback
{
    /**
     * 与服务器返回的字段映射关系
     */
    protected static final int RESULT_CODE_VALUE = 200;
    protected static final String RESULT_CODE = "errorCode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected static final String ERROR_MSG = "errorMsg";
    protected static final String EMPTY_MSG = "";

    /**
     * 自定义异常类型
     */
    protected static final int ERROR_NETWORK = -1; // the network relative error
    protected static final int ERROR_JSON = -2; // the JSON relative error
    protected static final int ERROR_OTHER = -3; // the unknown error

    private Handler mDeliveryHandler; // 消息转发
    private OkHttpListener mListener;
    private Class<?> mClass;
    private int mRequestId;
    private Gson gson;

    public OkHttpJsonCallback(OkHttpHandler handler)
    {
        this.mClass = handler.mClass;
        this.mListener = handler.mListener;
        this.mRequestId = handler.mRequestId;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
        this.gson = new GsonBuilder().serializeNulls().create();
    }


    @Override
    public void onFailure(Call call, final IOException e)
    {
        mDeliveryHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mListener.onFailure(mRequestId, new OkHttpException(ERROR_NETWORK, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, @NonNull Response response) throws IOException
    {
        final String jsonResult = response.body().string();
        mDeliveryHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                handleResponse(jsonResult);
            }
        });
    }

    private void handleResponse(Object response)
    {
        if (response == null || response.toString().trim().equals(""))
        {
            mListener.onFailure(mRequestId, new OkHttpException(ERROR_NETWORK, EMPTY_MSG));
            return;
        }
        try
        {
            JSONObject result = new JSONObject(response.toString());
            if (this.mClass == null)
                mListener.onSuccess(mRequestId, result);
            else
            {
                Object object = this.gson.fromJson(response.toString(), mClass);
                if (object != null)
                    mListener.onSuccess(mRequestId, object);
                else
                    mListener.onFailure(mRequestId, new OkHttpException(ERROR_JSON, response.toString()));
            }
        }
        catch (JSONException e)
        {
            mListener.onFailure(mRequestId, new OkHttpException(ERROR_OTHER, e.getMessage()));
        }

    }
}
