package com.zhangqiang.visiblehelper.sample;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhangqiang.holderfragment.HolderFragment;
import com.zhangqiang.visiblehelper.ActivityVisibleHelper;
import com.zhangqiang.visiblehelper.OnVisibilityChangeListener;
import com.zhangqiang.visiblehelper.VisibleHelper;
import com.zhangqiang.visiblehelper.VisibleHelperOwner;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    public static final String TAG_LEFT = "left";
    public static final String TAG_RIGHT = "right";
    private View btnLeft;
    private View btnRight;

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
        btnLeft = findViewById(R.id.bt_tab_left);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFragment(TAG_LEFT);
            }
        });
        btnRight = findViewById(R.id.bt_tab_right);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFragment(TAG_RIGHT);
            }
        });
        showFragment(TAG_LEFT);
    }

    private void showFragment(String tag) {

        HolderFragment holderFragment = HolderFragment.forActivity(this);
        Map<String, Fragment> fragments = (Map<String, Fragment>) holderFragment.getTag("fragments");
        if (fragments == null) {
            fragments = new HashMap<>();
            holderFragment.setTag("fragments", fragments);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragments.get(tag);
        if (fragment == null) {
            if (TAG_LEFT.equals(tag)) {
                fragment = new FragmentLeft();
            } else if (TAG_RIGHT.equals(tag)) {
                fragment = new FragmentRight();
            } else {
                throw new IllegalArgumentException("unknown tag: " + tag);
            }
            fragments.put(tag, fragment);
            transaction.add(R.id.fl_fragment_container, fragment);
        } else {
            transaction.show(fragment);
        }
        for (Map.Entry<String, Fragment> entry : fragments.entrySet()) {
            if (!entry.getKey().equals(tag)) {
                transaction.hide(entry.getValue());
            }
        }
        transaction.commitAllowingStateLoss();


        if (TAG_LEFT.equals(tag)) {
            btnLeft.setSelected(true);
            btnRight.setSelected(false);
        } else if (TAG_RIGHT.equals(tag)) {
            btnRight.setSelected(true);
            btnLeft.setSelected(false);
        }
    }

}
