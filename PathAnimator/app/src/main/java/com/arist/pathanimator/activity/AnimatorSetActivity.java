package com.arist.pathanimator.activity;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arist.pathanimator.R;
import com.arist.pathanimator.entity.FrameF;
import com.arist.pathanimator.entity.SizeFCustom;

public class AnimatorSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animate_set);

        init();

    }

    private ImageView imageView;
    private void init(){
        imageView = (ImageView) findViewById(R.id.iv);
    }

    public void onStartSeq(View view){

        imageView.setX(getDP(20));
        imageView.setY(getDP(20));

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(getDP(20), getDP(200));
        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                imageView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimatorX.setDuration(1000);

        ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(getDP(20), getDP(200));
        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                imageView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimatorY.setDuration(1000);

        animatorSet.playSequentially(valueAnimatorX, valueAnimatorY);
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();

    }

    public void onStartTog(View view){

        imageView.setX(getDP(20));
        imageView.setY(getDP(20));

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(getDP(20), getDP(200));
        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                imageView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimatorX.setDuration(1000);

        ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(getDP(20), getDP(200));
        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                imageView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimatorY.setDuration(1000);

        animatorSet.playTogether(valueAnimatorX, valueAnimatorY);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    private float getDP(int num){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
    }

}
