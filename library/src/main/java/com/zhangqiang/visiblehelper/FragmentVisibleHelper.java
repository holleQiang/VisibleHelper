package com.zhangqiang.visiblehelper;

/**
 * Author：zhangqiang
 * Date：2019/1/9 11:15:53
 * Email:852286406@share_qq.com
 * Github:https://github.com/holleQiang
 */
public final class FragmentVisibleHelper extends VisibleHelper{

    private VisibleFragment visibleFragment;
    private boolean mUserVisibleHint;

    public FragmentVisibleHelper(VisibleFragment visibleFragment) {
        this.visibleFragment = visibleFragment;
        mUserVisibleHint = visibleFragment.getUserVisibleHint();
    }

    public boolean isVisibleToUser() {
        return visibleFragment.isResumed() && !visibleFragment.isHidden() && mUserVisibleHint;
    }

    public void onHiddenChanged(boolean hidden) {
        notifyVisibilityChange(!hidden);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        boolean isChanged = mUserVisibleHint ^ isVisibleToUser;
        if (isChanged && visibleFragment.isResumed()) {
            notifyVisibilityChange(isVisibleToUser);
        }
        mUserVisibleHint = isVisibleToUser;
    }

    public void onPause() {
        if (visibleFragment.getUserVisibleHint() && !visibleFragment.isHidden()) {
            notifyVisibilityChange(false);
        }
    }

    public void onResume() {
        if (visibleFragment.getUserVisibleHint() && !visibleFragment.isHidden()) {
            notifyVisibilityChange(true);
        }
    }

    @Override
    public boolean isVisible() {
        return isVisibleToUser();
    }

}
