package com.arist.pathanimator.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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

/**
 * Created by arist on 16/9/11.
 */
public class AiQiYiLoadingView extends View {

    public AiQiYiLoadingView(Context context) {
        super(context);
    }

    public AiQiYiLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AiQiYiLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ValueAnimator valueAnimator;
    private ValueAnimator fixAnimator;
    private int duration = 1200;
    private float progress;
    public void startLoading(){

        stopLoading();

        int delay = 0;
        if (progress > 0 && progress < 1) {
            delay = (int) ((1-progress) * duration);

            fixAnimator = ValueAnimator.ofFloat(progress, 1);
            fixAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    progress = (float) animation.getAnimatedValue();
                    invalidate();

                }
            });
            fixAnimator.setDuration(delay);
            fixAnimator.setInterpolator(new LinearInterpolator());
            fixAnimator.setRepeatCount(ValueAnimator.INFINITE);
            fixAnimator.start();
        }

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                progress = (float) animation.getAnimatedValue();
                invalidate();

            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new LinearInterpolator());

//        valueAnimator.setInterpolator(new OvershootInterpolator());
//        valueAnimator.setInterpolator(new BounceInterpolator());
//        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        valueAnimator.setInterpolator(new DecelerateInterpolator());
//        valueAnimator.setInterpolator(new AnticipateOvershootInterpolator());
//        valueAnimator.setInterpolator(new CycleInterpolator(2));        // 杂乱无章
//        valueAnimator.setInterpolator(new AccelerateInterpolator());    // 最有动感
//        valueAnimator.setInterpolator(new AnticipateInterpolator());    // 有动感

        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setStartDelay(delay);
        valueAnimator.start();

    }

    public void stopLoading(){

        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (fixAnimator != null) {
            fixAnimator.cancel();
        }

    }

    private float startAngle;   // 起始角度
    private float swipAngle;        // 扫过的角度
    private float radius;   // 半径
    private float lineWidth;   // 线宽

    private Paint paint;

    private Path pathRing;
    private Path pathPause;

    private float radiusShap;   // 中间暂停开始的大小

    private void init(){
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        startAngle = 0.0f;
        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        radius = getWidth() / 2 - lineWidth;

        radiusShap = radius / 2.0f;
//        radiusShap = radius;

        paint = new Paint();
        paint.setColor(Color.parseColor("#00CB05"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setStrokeCap(Paint.Cap.SQUARE);

        pathRing = new Path();

        pathPause = new Path();

        pathPause.moveTo(-radiusShap/2, -radiusShap*1.732f/2);
        pathPause.lineTo(radiusShap, 0);
        pathPause.lineTo(-radiusShap/2, radiusShap*1.732f/2);
        pathPause.lineTo(-radiusShap/2, -radiusShap*1.732f/2);

        pathPause.close();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        long t = System.nanoTime();

        if (paint == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(-90.0f);

        // 分三个阶段
        // 第一阶段0-0.5 , startAngle为0,swipeAngle增大从0到360度,中间三角形不变
        // 第二阶段0.5-1.0 , startAngle为变量 , endAngle为360, swipeAngle减小从360到0度,中间三角形旋转360度

        float rotation = 90.0f;

        if (progress <= 0.5f) {
            startAngle = 0;
            swipAngle = progress*360.f*2;
        } else {
            swipAngle = (1.0f - progress)*360.f*2;
            startAngle = 360.f - swipAngle;

            rotation -= (1.0f - progress)*360.f*2;
        }

        pathRing.reset();
        pathRing.addArc(new RectF(-radius, -radius, radius, radius), startAngle, swipAngle);

        canvas.drawPath(pathRing, paint);

        // 测试圆
        /*
        int color = paint.getColor();
        float stokeWidth = paint.getStrokeWidth();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1.0f);

        Path p = new Path();
        p.addCircle(0, 0, radiusShap, Path.Direction.CCW);
        canvas.drawPath(p, paint);

        paint.setColor(color);
        paint.setStrokeWidth(stokeWidth);
        */


        drawTriangle(canvas, radiusShap, rotation);

        Log.e("info", "耗时:" + (System.nanoTime() - t) + "纳秒");

    }

    private void drawTriangle(Canvas canvas, float width, float rotation){
        canvas.save();

        canvas.rotate(rotation);

        Paint.Style style = paint.getStyle();

        paint.setStyle(Paint.Style.FILL);

        canvas.drawPath(pathPause, paint);

        paint.setStyle(style);

        canvas.restore();
    }

}
