package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.PathTestView;

public class PathTestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_test);

        init();

    }

    private PathTestView pathTestView;
    private void init(){
        pathTestView = (PathTestView) findViewById(R.id.pathTestView);
    }

    public void onStart(View view){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pathTestView.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();

    }


}
