package com.zhangqiang.visiblehelper.sample;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.zhangqiang.visiblehelper.FragmentVisibleHelper;
import com.zhangqiang.visiblehelper.VisibleHelper;
import com.zhangqiang.visiblehelper.VisibleHelperOwner;

public class BaseFragment extends Fragment implements VisibleHelperOwner {

    private FragmentVisibleHelper fragmentVisibleHelper = new FragmentVisibleHelper(this);


    @NonNull
    @Override
    public VisibleHelper getVisibleHelper() {
        return fragmentVisibleHelper;
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentVisibleHelper.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        fragmentVisibleHelper.onStop();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        fragmentVisibleHelper.onHiddenChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        fragmentVisibleHelper.setUserVisibleHint();
    }
}
