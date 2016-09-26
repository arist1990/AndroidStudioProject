package com.arist.pathanimator.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by arist on 16/9/1.
 */
public class PathWaterWaveView extends View {

    private Paint paint = new Paint();

    public PathWaterWaveView(Context context) {
        super(context);
    }

    public PathWaterWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathWaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float progress = 0.0f;

    public void setProgress(float progress){
        this.progress = progress;
        postInvalidate();
    }

    private ValueAnimator valueAnimator;
    private float animateValue; // 这个决定波浪的位置
    private float yOffset;  // 决定波浪幅度
    private int lineSize = 0;
    private Path pathClip;
    private Path path;
    @Override
    protected void onDraw(Canvas canvas) {

        if (progress <= 0)
            return;

        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2;

        // 将画布平移
        canvas.translate(width / 2, height / 2);

        if (lineSize == 0) {
            lineSize = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));

            paint.setStrokeWidth(lineSize);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);

            pathClip = new Path();

            path = new Path();

            valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    animateValue = (float) valueAnimator.getAnimatedValue();
                    if (progress < 1.0f)
                        postInvalidate();
                }
            });
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.start();

        }

        float y =  radius - radius * 2 * progress;    // y坐标
        yOffset = radius / 8;

        // 计算pathClip
        pathClip.reset();
        pathClip.addCircle(0, 0, radius, Path.Direction.CCW);

        // 计算path
        path.reset();
        path.moveTo(-radius, y);
        path.lineTo(-radius, radius);
        path.lineTo(radius, radius);
        path.lineTo(radius, y);

        float endX = radius + animateValue * 2 * radius;
        path.lineTo(endX, y);
        float startX = endX - 4 * radius;   // 起点与终点相差4*radius

        Log.e("info", "startX:" + startX);

        // 计算各个控制点，最后一个就是起点
        for (int i = 1; i < 5; i++) {
            float control = endX - i * radius;
            float arcX = control + radius / 2;
            float arcY = (i % 2 == 1 ? -1 : 1) * yOffset + y;

            path.quadTo(arcX, arcY, control, y);

        }

        path.close();

        canvas.drawPath(path, paint);

    }

}
