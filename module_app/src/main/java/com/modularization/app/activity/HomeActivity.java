package com.modularization.app.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.FrameLayout;

import com.modularization.app.R;
import com.modularization.app.view.fragment.main.HomeFragment;
import com.modularization.app.view.fragment.main.MessageFragment;
import com.modularization.app.view.fragment.main.MineFragment;
import com.modularization.common.base.BaseActivity;

/**
 * 创建首页所有的fragment，以及fragment切换
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener
{
    private FrameLayout mContainer;
    private BottomNavigationView mNavigation;

    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrentFragment;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 初始化页面所有控件
        doInitView();
        // 初始化页面所有Fragment
        doInitFragment();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            default:
                break;
        }
    }

    private void doInitView()
    {
        mContainer = $(R.id.container_layout);
        mNavigation = $(R.id.bottom_nav);
        mNavigation.setOnNavigationItemSelectedListener(item -> switchFragment(item.getItemId()));
    }

    private void doInitFragment()
    {
        fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // 添加默认显示的fragment
        mHomeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.container_layout, mHomeFragment);
        mCurrentFragment = mHomeFragment;
        fragmentTransaction.commit();
    }

    // 切换fragment
    private boolean switchFragment(int itemId)
    {
        boolean flag = true;
        FragmentTransaction transaction = fm.beginTransaction();
        switch (itemId)
        {
            case R.id.navigation_home:
                hideFragment(mMessageFragment, transaction);
                hideFragment(mMineFragment, transaction);
                showFragment(mHomeFragment, transaction);
                break;
            case R.id.navigation_message:
                hideFragment(mHomeFragment, transaction);
                hideFragment(mMineFragment, transaction);
                if (mMessageFragment == null)
                {
                    mMessageFragment = new MessageFragment();
                    transaction.add(R.id.container_layout, mMessageFragment);
                }
                showFragment(mMessageFragment, transaction);
                break;
            case R.id.navigation_mine:
                hideFragment(mHomeFragment, transaction);
                hideFragment(mMessageFragment, transaction);
                if (mMineFragment == null)
                {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.container_layout, mMineFragment);
                }
                showFragment(mMineFragment, transaction);
                break;
            default:
                flag = false;
                break;
        }
        transaction.commit();
        return flag;
    }

    private void showFragment(Fragment fragment, FragmentTransaction ft)
    {
        ft.show(fragment);
        mCurrentFragment = fragment;
    }

    private void hideFragment(Fragment fragment, FragmentTransaction ft)
    {
        if (fragment != null) ft.hide(fragment);
    }
}
