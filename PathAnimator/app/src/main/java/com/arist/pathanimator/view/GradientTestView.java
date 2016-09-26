package com.arist.pathanimator.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.arist.pathanimator.animator.CustomValueAnimator;

import java.util.IllegalFormatFlagsException;

/**
 * Created by arist on 16/9/11.
 */
public class GradientTestView extends View {

    public GradientTestView(Context context) {
        super(context);
    }

    public GradientTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint;
    private MyOnGestureListener myOnGestureListener;
    private GestureDetector gestureDetector;
    private float degress;
    private float degressStep = 2.0f;
    private void init(){

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);

        // 线性渐变
        paint.setShader(new LinearGradient(0, 0, getWidth(), getHeight(),
                Color.RED, Color.BLACK, Shader.TileMode.CLAMP));

        paint.setShader(new SweepGradient(0, 0,
                new int[]{Color.RED, Color.YELLOW, Color.RED},
                new float[]{0.0f, 0.5f, 1.0f}));

//        paint.setShader(new SweepGradient(0, 0,
//                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.RED, Color.RED},
//                new float[]{0.0f, 0.3f, 0.6f, 0.9f, 1.0f}));

        myOnGestureListener = new MyOnGestureListener();
        gestureDetector = new GestureDetector(getContext(), myOnGestureListener);

    }

    private ValueAnimator valueAnimator;
    public void startLoading(){
        valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degress += degressStep;

                if (degress > 360.f)
                    degress -= 360f;

                if (degress < -360.f)
                    degress += 360f;

                invalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.start();
    }

    public void stopLoading(){
        if (valueAnimator != null)
            valueAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (paint == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);

        canvas.rotate(degress);

        canvas.drawCircle(0, 0, getWidth() / 2, paint);


//        // 会卡顿
//        degress += degressStep;
//        if (degress > 360.f)
//            degress -= 360f;
//        postInvalidate();

    }

    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.e("info", "velocityX:" + velocityX + " - velocityY:" + velocityY);
            if (velocityX > 100) {
                degressStep -= 2.0f;
            }

            if (velocityX < -100) {
                degressStep += 2.0f;
            }

            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

}
