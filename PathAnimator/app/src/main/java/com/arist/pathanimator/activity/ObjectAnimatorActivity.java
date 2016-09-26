package com.arist.pathanimator.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.CustomPointView;

public class ObjectAnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_object_animate);

        init();

    }

    private CustomPointView customPointView;
    private void init(){
        customPointView = (CustomPointView) findViewById(R.id.customPointView);
    }

    public void onStart(View view){
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(customPointView, "index", CustomPointView.VALUE_START, CustomPointView.VALUE_END, CustomPointView.VALUE_END * 3 / 4, CustomPointView.VALUE_END);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(customPointView, "index", CustomPointView.VALUE_START, CustomPointView.VALUE_END);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private float getDP(int num){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
    }

}
