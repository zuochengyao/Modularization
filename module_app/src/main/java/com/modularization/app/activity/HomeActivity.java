package com.modularization.app.activity;

import android.app.Activity;
import android.os.Bundle;

import com.modularization.app.R;


public class HomeActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
