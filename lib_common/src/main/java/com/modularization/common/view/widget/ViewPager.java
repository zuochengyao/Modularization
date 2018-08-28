package com.modularization.common.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author 左程耀
 *
 * 封装ViewPager，可控制滑动
 */
public class ViewPager extends android.support.v4.view.ViewPager
{
    private boolean mSlideable = true;

    public ViewPager(@NonNull Context context)
    {
        super(context);
    }

    public ViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return mSlideable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return mSlideable && super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置Pager是否可滑动
     */
    public void setPagerSlideable(boolean slideable)
    {
        this.mSlideable = slideable;

    }

}
