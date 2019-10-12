package com.zhangqiang.visiblehelper;

import java.util.ArrayList;
import java.util.List;

public abstract class VisibleHelper {

    private List<OnVisibilityChangeListener> listeners;

    public abstract boolean isVisible();

    protected void notifyVisibilityChange(boolean visible) {
        if (listeners != null) {
            for (int i = listeners.size() - 1; i >= 0; i--) {
                OnVisibilityChangeListener listener = listeners.get(i);
                listener.onVisibilityChange(visible);
            }
        }
    }

    public void addVisibilityChangeListener(OnVisibilityChangeListener visibilityChangeListener) {
        if (this.listeners == null) {
            listeners = new ArrayList<>();
        }
        this.listeners.add(visibilityChangeListener);
    }

    public void removeVisibilityChangeListener(OnVisibilityChangeListener visibilityChangeListener) {
        if (this.listeners == null) {
            return;
        }
        this.listeners.remove(visibilityChangeListener);
    }
}
