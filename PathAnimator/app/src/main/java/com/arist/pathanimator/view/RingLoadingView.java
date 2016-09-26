package com.arist.pathanimator.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
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
import com.arist.pathanimator.animator.CustomValueAnimator;

import java.util.ArrayList;

/**
 * Created by arist on 16/9/11.
 */
public class RingLoadingView extends View {

    public RingLoadingView(Context context) {
        super(context);
    }

    public RingLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RingLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ValueAnimator valueAnimator;
    private ValueAnimator colorAnimator;
    private int color = Color.RED;
    private boolean isAdd = true;
    public void startLoading(){

        stopLoading();

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Log.e("info", "onAnimationUpdate:" + animation.getAnimatedValue());

                startAngle += startAngleSingle;

                if (startAngle >= 360.f) {
                    startAngle -= 360.f;
                }

                float newAngle = swipAngle + swipAngleSingle * (isAdd ? 1 : -1);
                if (newAngle < 0) {
                    isAdd = true;
                    newAngle += swipAngleSingle*2;
                } else if (newAngle > 360.0f){
                    isAdd = false;
                    newAngle -= swipAngleSingle*2;
                }

                swipAngle = newAngle;

                invalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();

        colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.rgb(255, 0, 0), Color.rgb(200, 0, 0));
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                color = (int) animation.getAnimatedValue();

                invalidate();
            }
        });
        colorAnimator.setDuration(5000);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.start();

    }

    public void stopLoading(){

        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (colorAnimator != null) {
            colorAnimator.cancel();
        }

    }

    private float startAngle;   // 起始角度
    private float startAngleSingle = 3.0f;   // 起始角度增值
    private float swipAngle = 0.f;        // 扫过的角度
    private float swipAngleSingle = 1.0f;   // 扫过角度每次增值
    private float radius;   // 半径
    private float lineWidth;   // 线宽

    private Paint paint;
    private Paint paintShader;

    private Path pathBack;
    private Path pathRing;

    private void init(){
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        startAngle = 0.0f;
        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

        radius = getWidth() / 2 - lineWidth;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);

//        paint.setShadowLayer(1, ballSpacing/2, ballSpacing/2, Color.LTGRAY);

//        paint.setShader(new LinearGradient(-getWidth()/2, 0, getWidth()/2, 0,
//                getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary), Shader.TileMode.CLAMP));

        paintShader = new Paint(paint);

        /*
        paintShader.setShader(new SweepGradient(0.0f, 0.0f,
                new int[]{Color.RED,
//                        Color.parseColor("#ff4400"),
                        Color.parseColor("#ff9900"),
//                        Color.parseColor("#ff4400"),
                        Color.parseColor("#ff0000")},
//                new float[]{0.0f, 0.1f, 5.0f, 9.0f, 1.0f}
                new float[]{0.0f, 5.0f, 1.0f}
        ));
        */

        paintShader.setShader(new SweepGradient(0, 0,
                new int[]{Color.RED, Color.YELLOW, Color.RED},
                new float[]{0.0f, 0.5f, 1.0f}));

        pathBack = new Path();
        pathBack.addCircle(0, 0, radius, Path.Direction.CW);

        pathRing = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (paint == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);

        /*
        // 动态绘制圆点
        for (int i = 0; i < ballXList.size(); i++) {
            float x = ballXList.get(i);

            int color = ballColorList.get(i);
            paint.setColor(ballColorList.get(i));

            // 设置每个小球的阴影
            paint.setShadowLayer(1, ballSpacing/4, ballSpacing/4,
                    Color.argb(0x99, Color.red(color), Color.green(color), Color.blue(color)));

            canvas.drawCircle(x, 0, ballRadiusList.get(i), paint);
        }
        */

        pathRing.reset();;
        pathRing.addArc(new RectF(-radius, -radius, radius, radius), startAngle, swipAngle);

        paint.setColor(Color.LTGRAY);
        canvas.drawPath(pathBack, paint);

//        paint.setColor(color);
//        canvas.drawPath(pathRing, paint);
        paintShader.setColor(color);
        canvas.drawPath(pathRing, paintShader);

    }

}
