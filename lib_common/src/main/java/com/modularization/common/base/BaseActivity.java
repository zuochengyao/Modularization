package com.modularization.common.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.modularization.common.util.Log;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 为所有Activity提供公共的行为与事件
 */
public class BaseActivity extends AppCompatActivity
{
    public String TAG;
    protected RxPermissions mRxPermissions;
    protected String[] mAppPermissions = {
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // region Activity生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();
        mRxPermissions = new RxPermissions(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
    // endregion

    // region Methods

    /**
     * equals findViewById
     */
    protected <T extends View> T $(@IdRes int resId)
    {
        return findViewById(resId);
    }

    @SuppressLint("CheckResult")
    public void requestAppPermission()
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        {
            mRxPermissions.requestEach(mAppPermissions).subscribe(permission -> { // will emit all Permission objects
                if (permission.granted)
                {
                    // `permission.name` is granted !
                    Log.i(TAG, permission.name + " is Granted");
                }
                else if (permission.shouldShowRequestPermissionRationale)
                {
                    // Denied permission without ask never again
                    Log.i(TAG, permission.name + " is shouldShowRequestPermissionRationale");
                }
                else
                {
                    // Denied permission with ask never again
                    // Need to go to the settings
                    Log.i(TAG, permission.name + " is Denied");
                }
            });
        }
    }

    @SuppressLint("CheckResult")
    public void requestPermission(final String permission)
    {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        {
            mRxPermissions.request(permission).subscribe(granted -> { // Always true pre-M
                if (granted)
                {
                    // permission granted !
                    Log.i(TAG, permission + " is Granted");
                }
                else
                {
                    // permission denied
                    Log.i(TAG, permission + " is Denied");
                }
            });
        }
    }
    // endregion
}
