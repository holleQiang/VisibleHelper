package com.zhangqiang.visiblehelper.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentInPager extends BaseFragment {

    FragmentLeft fragment = new FragmentLeft();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_in_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fl_fragment_container, fragment)
                        .commitAllowingStateLoss();
            }
        });
        view.findViewById(R.id.tv_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commitAllowingStateLoss();
            }
        });
    }

    private void log() {
        Fragment parentFragment = getParentFragment();
        Log.i("Test", parentFragment + "============" + getVisibleHelper().isVisible());
    }
}
