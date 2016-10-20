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
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by arist on 16/9/11.
 */
public class RingLoadingView2 extends View {

    public RingLoadingView2(Context context) {
        super(context);
    }

    public RingLoadingView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RingLoadingView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ValueAnimator valueAnimator;
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

                invalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();

    }

    public void stopLoading(){

        if (valueAnimator != null) {
            valueAnimator.cancel();
        }

    }

    private float startAngle;   // 起始角度
    private float startAngleSingle = 3.0f;   // 起始角度增值
    private float swipAngle = 60.f;        // 扫过的角度
    private float radius;   // 半径
    private float lineWidth;   // 线宽

    private Paint paint;

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
        paint.setStrokeCap(Paint.Cap.ROUND);

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
        canvas.rotate(-90.0f);

        pathRing.reset();;
        pathRing.addArc(new RectF(-radius, -radius, radius, radius), startAngle, swipAngle);

        paint.setColor(Color.LTGRAY);
        canvas.drawPath(pathBack, paint);

        paint.setColor(Color.DKGRAY);
        canvas.drawPath(pathRing, paint);

    }

}
