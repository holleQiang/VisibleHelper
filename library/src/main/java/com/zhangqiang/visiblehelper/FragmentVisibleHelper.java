package com.zhangqiang.visiblehelper;

import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Author：zhangqiang
 * Date：2019/1/9 11:15:53
 * Email:852286406@share_qq.com
 * Github:https://github.com/holleQiang
 */
public final class FragmentVisibleHelper extends VisibleHelper {

    public static final int TRIGGER_INITIAL = -1;
    public static final int TRIGGER_STARTED = 0;
    public static final int TRIGGER_HIDDEN_CHANGED = 1;
    public static final int TRIGGER_SET_USER_VISIBLE_HINT = 2;
    private Fragment fragment;
    private boolean mStarted;
    private boolean mVisible;
    @Trigger
    private int mTrigger = TRIGGER_INITIAL;

    public FragmentVisibleHelper(Fragment fragment) {
        this.fragment = fragment;
        mVisible = isVisibleToUser();
    }

    private boolean isVisibleToUser() {
        return mStarted && !isHidden(fragment) && getUserVisibleHint(fragment);
    }

    public void onHiddenChanged() {
        checkStatusAndNotify(TRIGGER_HIDDEN_CHANGED);
        if (fragment.getHost() != null) {
            FragmentManager fragmentManager = fragment.getChildFragmentManager();
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
        checkStatusAndNotify(TRIGGER_SET_USER_VISIBLE_HINT);
        if (fragment.getHost() != null) {
            FragmentManager fragmentManager = fragment.getChildFragmentManager();
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
        checkStatusAndNotify(TRIGGER_STARTED);
    }

    public void onStop() {
        mStarted = false;
        checkStatusAndNotify(TRIGGER_STARTED);
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

    private void checkStatusAndNotify(int trigger) {

        boolean newStatus = isVisibleToUser();
        if (mVisible != newStatus) {
            mVisible = newStatus;
            mTrigger = trigger;
            notifyVisibilityChange(newStatus);
        }
    }

    @Trigger
    public int getTrigger() {
        return mTrigger;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {TRIGGER_INITIAL,
            TRIGGER_HIDDEN_CHANGED,
            TRIGGER_SET_USER_VISIBLE_HINT,
            TRIGGER_STARTED})
    @interface Trigger {

    }
}
