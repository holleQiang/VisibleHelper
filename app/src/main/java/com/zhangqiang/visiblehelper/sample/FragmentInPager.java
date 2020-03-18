package com.zhangqiang.visiblehelper.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangqiang.visiblehelper.OnVisibilityChangeListener;

public class FragmentInPager extends BaseFragment {

    private TextView tvStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_in_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvStatus = view.findViewById(R.id.tv_status);
        tvStatus.setText("visible:" + getVisibleHelper().isVisible());
        log();
        getVisibleHelper().addVisibilityChangeListener(new OnVisibilityChangeListener() {
            @Override
            public void onVisibilityChange(boolean isVisible) {
                tvStatus.setText("visible:" + isVisible);
                log();
            }
        });
    }

    private void log() {
        Fragment parentFragment = getParentFragment();
        Log.i("Test", parentFragment + "============" + getVisibleHelper().isVisible());
    }
}
