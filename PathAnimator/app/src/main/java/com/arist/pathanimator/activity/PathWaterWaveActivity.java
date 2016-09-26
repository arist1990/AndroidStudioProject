package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.PathTestView;
import com.arist.pathanimator.view.PathWaterWaveView;

public class PathWaterWaveActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_water_wave);

        init();

    }

    private PathWaterWaveView pathWaterWaveView;
    private void init(){
        pathWaterWaveView = (PathWaterWaveView) findViewById(R.id.pathWaterWaveView);
    }

    public void onStart(View view){

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pathWaterWaveView.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(10000);
        valueAnimator.start();

//        pathWaterWaveView.setProgress(0.5f);

    }


}
