package com.zhangqiang.visiblehelper;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Author：zhangqiang
 * Date：2019/1/10 09:28:06
 * Email:852286406@share_qq.com
 * Github:https://github.com/holleQiang
 */
public class VisibleFragmentV4Impl implements VisibleFragment {

    @NonNull
    private Fragment fragment;

    public VisibleFragmentV4Impl(@NonNull Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean getUserVisibleHint() {
        return fragment.getUserVisibleHint();
    }

    @Override
    public boolean isResumed() {
        return fragment.isResumed();
    }

    @Override
    public boolean isHidden() {
        Fragment parentFragment = fragment.getParentFragment();
        while (parentFragment != null) {
            if (parentFragment.isHidden()) {
                return true;
            }
            parentFragment = parentFragment.getParentFragment();
        }
        return fragment.isHidden();
    }
}
