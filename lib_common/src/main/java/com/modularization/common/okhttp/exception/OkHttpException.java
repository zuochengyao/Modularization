package com.modularization.common.okhttp.exception;

public class OkHttpException extends Exception
{
    private static final long serialVersionUID = 1L;

    private int errorCode;

    private Object errorMsg;

    public OkHttpException(int code, Object msg)
    {
        this.errorCode = code;
        this.errorMsg = msg;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public Object getErrorMsg()
    {
        return errorMsg;
    }
}
