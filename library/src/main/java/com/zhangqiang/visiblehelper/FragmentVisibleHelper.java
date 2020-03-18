package com.zhangqiang.visiblehelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Author：zhangqiang
 * Date：2019/1/9 11:15:53
 * Email:852286406@share_qq.com
 * Github:https://github.com/holleQiang
 */
public final class FragmentVisibleHelper extends VisibleHelper {

    private Fragment fragment;
    private boolean mStarted;
    private boolean mVisible;

    public FragmentVisibleHelper(Fragment fragment) {
        this.fragment = fragment;
        mVisible = isVisibleToUser();
    }

    private boolean isVisibleToUser() {
        return mStarted && !isHidden(fragment) && getUserVisibleHint(fragment);
    }

    public void onHiddenChanged() {
        final boolean newStatus = isVisibleToUser();
        if (mVisible != newStatus) {
            mVisible = newStatus;
            notifyVisibilityChange(newStatus);
        }
        if (fragment.getHost() != null) {
            FragmentManager fragmentManager = fragment.getChildFragmentManager();
            fragmentManager.executePendingTransactions();
            List<Fragment> fragments = fragmentManager.getFragments();
            if (!fragments.isEmpty()) {
                for (Fragment childFragment : fragments) {
                    if (childFragment instanceof VisibleHelperOwner) {
                        VisibleHelper visibleHelper = ((VisibleHelperOwner) childFragment).getVisibleHelper();
                        if (visibleHelper instanceof FragmentVisibleHelper) {
                            ((FragmentVisibleHelper) visibleHelper).onHiddenChanged();
                        }
                    }
                }
            }
        }
    }

    public void setUserVisibleHint() {
        final boolean newStatus = isVisibleToUser();
        if (mVisible != newStatus) {
            mVisible = newStatus;
            notifyVisibilityChange(newStatus);
        }
        if (fragment.getHost() != null) {
            FragmentManager fragmentManager = fragment.getChildFragmentManager();
            fragmentManager.executePendingTransactions();
            List<Fragment> fragments = fragmentManager.getFragments();
            if (!fragments.isEmpty()) {
                for (Fragment childFragment : fragments) {
                    if (childFragment instanceof VisibleHelperOwner) {
                        VisibleHelper visibleHelper = ((VisibleHelperOwner) childFragment).getVisibleHelper();
                        if (visibleHelper instanceof FragmentVisibleHelper) {
                            ((FragmentVisibleHelper) visibleHelper).setUserVisibleHint();
                        }
                    }
                }
            }
        }
    }

    public void onStart() {
        mStarted = true;
        boolean newStatus = isVisibleToUser();
        if (mVisible != newStatus) {
            mVisible = newStatus;
            notifyVisibilityChange(newStatus);
        }
    }

    public void onStop() {
        mStarted = false;
        boolean newStatus = isVisibleToUser();
        if (mVisible != newStatus) {
            mVisible = newStatus;
            notifyVisibilityChange(newStatus);
        }
    }

    @Override
    public boolean isVisible() {
        return mVisible;
    }


    private static boolean isHidden(Fragment fragment) {
        Fragment temp = fragment;
        while (temp != null) {
            if (temp.isHidden()) {
                return true;
            }
            temp = temp.getParentFragment();
        }
        return false;
    }

    private static boolean getUserVisibleHint(Fragment fragment) {
        Fragment temp = fragment;
        while (temp != null) {
            if (!temp.getUserVisibleHint()) {
                return false;
            }
            temp = temp.getParentFragment();
        }
        return true;
    }
}
