package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.GradientTestView;
import com.arist.pathanimator.view.PathTestView2;

public class GradientTestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gradient_test);

        init();

    }

    private GradientTestView gradientTestView;
    private void init(){
        gradientTestView = (GradientTestView) findViewById(R.id.gradientTestView);
    }

    public void onStart(View view){
        gradientTestView.startLoading();
    }

    public void onStop(View view){
        gradientTestView.stopLoading();
    }

}
