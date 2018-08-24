package com.modularization.app.application;

import com.modularization.app.controller.RealmManager;
import com.modularization.common.base.BaseApplication;

public class MainApplication extends BaseApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        RealmManager.init(this);
    }
}
