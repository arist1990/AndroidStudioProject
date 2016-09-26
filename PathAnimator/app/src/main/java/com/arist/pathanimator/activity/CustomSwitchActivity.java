package com.arist.pathanimator.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.CustomPointView;
import com.arist.pathanimator.view.CustomSwitch;

public class CustomSwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_switch);

        init();

    }

    private CustomSwitch customSwitch;
    private CheckBox checkBox;
    private void init(){
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        customSwitch = (CustomSwitch) findViewById(R.id.customSwitch);
        customSwitch.setOnSwitchChangeListener(new CustomSwitch.OnSwitchChangeListener() {
            @Override
            public void onChange(CustomSwitch customSwitch, CustomSwitch.State state) {
                Log.e("info", "onChange:" + state);
            }
        });
    }

    public void onStart(View view){
        CustomSwitch.State state = customSwitch.getState() == CustomSwitch.State.ON ? CustomSwitch.State.OFF : CustomSwitch.State.ON;
        customSwitch.updateState(state, checkBox.isChecked());

        float x = customSwitch.getX();
        float y = customSwitch.getY();

        if (customSwitch.getParent() == null || !(customSwitch.getParent() instanceof View)) {
            return;
        }

        View viewParent = (View) customSwitch.getParent();

        View decorView = getWindow().getDecorView();

        while (true) {
            Log.e("info", "viewParent:" + viewParent);
            x += viewParent.getX();
            y += viewParent.getY();
            Log.e("info", "x:" + x / getResources().getDisplayMetrics().density);
            Log.e("info", "y:" + y / getResources().getDisplayMetrics().density);
            Log.e("info", "===========");
            ViewParent p = viewParent.getParent();
            if (p != null && p instanceof View) {
                viewParent = (View) p;
            } else
                break;
        }

        Log.e("info", "decorView:" + decorView);

    }

}
