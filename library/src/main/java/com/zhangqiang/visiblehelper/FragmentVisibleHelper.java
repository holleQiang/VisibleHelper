package com.zhangqiang.visiblehelper;

/**
 * Author：zhangqiang
 * Date：2019/1/9 11:15:53
 * Email:852286406@share_qq.com
 * Github:https://github.com/holleQiang
 */
public final class FragmentVisibleHelper extends VisibleHelper {

    private VisibleFragment visibleFragment;
    private boolean mUserVisibleHint;
    private boolean started = false;

    public FragmentVisibleHelper(VisibleFragment visibleFragment) {
        this.visibleFragment = visibleFragment;
        mUserVisibleHint = visibleFragment.getUserVisibleHint();
    }

    public boolean isVisibleToUser() {
        return started && !visibleFragment.isHidden() && mUserVisibleHint;
    }

    public void onHiddenChanged(boolean hidden) {
        notifyVisibilityChange(!hidden);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        boolean isChanged = mUserVisibleHint ^ isVisibleToUser;
        mUserVisibleHint = isVisibleToUser;
        if (isChanged && started) {
            notifyVisibilityChange(isVisibleToUser);
        }
    }

    public void onStart() {
        started = true;
        if (visibleFragment.getUserVisibleHint() && !visibleFragment.isHidden()) {
            notifyVisibilityChange(true);
        }
    }

    public void onStop() {
        started = false;
        if (visibleFragment.getUserVisibleHint() && !visibleFragment.isHidden()) {
            notifyVisibilityChange(false);
        }
    }

    @Override
    public boolean isVisible() {
        return isVisibleToUser();
    }

}
