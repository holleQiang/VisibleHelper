package com.zhangqiang.visiblehelper;

public class ActivityVisibleHelper extends VisibleHelper {

    private boolean isVisible = false;

    public void onPause() {
        if (isVisible) {
            isVisible = false;
            notifyVisibilityChange(false);
        }
    }

    public void onResume() {
        if (!isVisible) {
            isVisible = true;
            notifyVisibilityChange(true);
        }
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }
}
