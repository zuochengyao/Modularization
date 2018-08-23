package com.modularization.app.view.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.modularization.app.R;
import com.modularization.common.base.BaseFragment;

public class MineFragment extends BaseFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.module_app_fragment_mine);
        return textView;
    }
}
