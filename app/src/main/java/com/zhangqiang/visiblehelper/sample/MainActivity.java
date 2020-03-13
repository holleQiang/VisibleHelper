package com.zhangqiang.visiblehelper.sample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zhangqiang.visiblehelper.ActivityVisibleHelper;
import com.zhangqiang.visiblehelper.OnVisibilityChangeListener;
import com.zhangqiang.visiblehelper.VisibleHelper;
import com.zhangqiang.visiblehelper.VisibleHelperOwner;

public class MainActivity extends AppCompatActivity implements VisibleHelperOwner {

    ActivityVisibleHelper visibleHelper = new ActivityVisibleHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVisibleHelper().addVisibilityChangeListener(new OnVisibilityChangeListener() {
            @Override
            public void onVisibilityChange(boolean isVisible) {
                Toast.makeText(MainActivity.this, "isVisible" + isVisible, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        visibleHelper.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        visibleHelper.onStop();
    }

    @NonNull
    @Override
    public VisibleHelper getVisibleHelper() {
        return visibleHelper;
    }
}
