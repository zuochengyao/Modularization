package com.modularization.app.controller;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmManager
{
    private static final String DB_NAME_REALM = "modularization.realm";

    private static Realm mRealm;

    public static Realm getRealm()
    {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder().name(DB_NAME_REALM)/* 指定数据库名字 */.build());
        return mRealm;
    }

    //负责初始化整个Realm数据库
    public static void init(Context context)
    {
        Realm.init(context);
        Log.e("realm", getRealm().getPath());
    }

    //关闭Realm数据库
    public static void closeRealm()
    {
        if (mRealm != null && !mRealm.isClosed())
        {
            mRealm.close();
        }
    }
}
