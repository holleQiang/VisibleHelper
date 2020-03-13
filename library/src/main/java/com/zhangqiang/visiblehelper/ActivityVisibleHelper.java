package com.zhangqiang.visiblehelper;

public class ActivityVisibleHelper extends VisibleHelper {

    private boolean isVisible = false;

    public void onStart() {
        if (!isVisible) {
            isVisible = true;
            notifyVisibilityChange(true);
        }
    }

    public void onStop() {
        if (isVisible) {
            isVisible = false;
            notifyVisibilityChange(false);
        }
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }
}
