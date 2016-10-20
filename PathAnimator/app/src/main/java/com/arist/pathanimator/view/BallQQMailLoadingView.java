package com.arist.pathanimator.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.arist.pathanimator.R;
import com.arist.pathanimator.activity.ObjectAnimatorActivity;

import java.util.ArrayList;

/**
 * Created by arist on 16/9/11.
 */
public class BallQQMailLoadingView extends View {

    public BallQQMailLoadingView(Context context) {
        super(context);
    }

    public BallQQMailLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BallQQMailLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimatorColor;
    private float progress = 1.0f; // 用于控制进度
    private int duration = 1000;
    private int repeatCount = 0;
    public void startLoading(){

        stopLoading();

        valueAnimator = ValueAnimator.ofFloat(0.f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                progress = (float) animation.getAnimatedValue();

                invalidate();
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        valueAnimator.start();

        valueAnimatorColor = ValueAnimator.ofFloat(0.f, 1.0f);
//        valueAnimatorColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//
//                progress = (float) animation.getAnimatedValue();
//
//                invalidate();
//            }
//        });
        valueAnimatorColor.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("info", "onAnimationStart");
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("info", "onAnimationEnd");
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e("info", "onAnimationCancel");
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                int temp = color2;
                if (repeatCount % 2 == 0) {
                    color2 = color1;
                    color1 = temp;
                } else {
                    color2 = color3;
                    color3 = temp;
                }
                repeatCount++;
                Log.e("info", "onAnimationRepeat");
            }
        });
        valueAnimatorColor.setDuration(valueAnimator.getDuration() / 2);
        valueAnimatorColor.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimatorColor.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimatorColor.start();

    }

    public void stopLoading(){
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }

    private float radiusBall;   // 球最半径

    private Paint paint;
    private int color1, color2, color3;

    private void init(){
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        radiusBall = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

        color1 = Color.GRAY;
        color2 = getResources().getColor(R.color.colorAccent);
        color3 = getResources().getColor(R.color.colorPrimary);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (paint == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);

        // 画第一个球
//        if (progress < 0.5f) {
//            paint.setColor(color1);
//        } else {
//            paint.setColor(color3);
//        }
        paint.setColor(color1);
        float x1 = -4 * radiusBall + progress * 8 * radiusBall;
        canvas.drawCircle(x1, 0, radiusBall, paint);

        // 画第三个球
//        if (progress >= 0.5f) {
//            paint.setColor(color1);
//        } else {
//            paint.setColor(color3);
//        }
        paint.setColor(color3);
        float x3 = 4 * radiusBall - progress * 8 * radiusBall;
        canvas.drawCircle(x3, 0, radiusBall, paint);

        // 画第二个球
        paint.setColor(color2);
        canvas.drawCircle(0, 0, radiusBall, paint);

    }

}
