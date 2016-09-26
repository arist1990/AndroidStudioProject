package com.arist.pathanimator.view;

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
import android.view.animation.LinearInterpolator;

/**
 * Created by arist on 16/9/11.
 */
public class CountDownView extends View {

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint;
    private float degress = -90;
    private int total = 200;
    private float degreeSingle = 360.f / total;
    private float lineLength;
    private Path pathArrow;
    private void init(){

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        lineLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        paint.setStrokeCap(Paint.Cap.ROUND);

        pathArrow = new Path();
        float startX = getWidth() / 2 - 2.5f*lineLength;
        pathArrow.moveTo(startX, 0);
        pathArrow.lineTo(startX + lineLength, -lineLength / 2);
        pathArrow.lineTo(startX + lineLength, lineLength / 2);
        pathArrow.lineTo(startX, 0);
        pathArrow.close();

        // 线性渐变
//        paint.setShader(new LinearGradient(0, 0, getWidth(), getHeight(),
//                Color.RED, Color.BLACK, Shader.TileMode.CLAMP));
//
//        paint.setShader(new SweepGradient(0, 0,
//                new int[]{Color.RED, Color.YELLOW, Color.RED},
//                new float[]{0.0f, 0.5f, 1.0f}));

//        paint.setShader(new SweepGradient(0, 0,
//                new int[]{Color.GREEN, Color.GREEN, Color.BLUE, Color.RED, Color.RED},
//                new float[]{0.0f, 0.3f, 0.6f, 0.9f, 1.0f}));

    }

    private ValueAnimator valueAnimator;
    public void startLoading(){

        if (valueAnimator != null && valueAnimator.isRunning())
            return;

        valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degress += degreeSingle / 4;

                if (degress >= 270.f)
                    degress = degress - 360.f;

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

        canvas.save();

        int i1 = (int) (degress * 10000);
        int i2 = (int) (degreeSingle * 10000);

//        canvas.rotate(i1 / i2 * degreeSingle);
        canvas.rotate(((int)(i1 / i2)) * degreeSingle);

        paint.setStyle(Paint.Style.STROKE);

        int startAlpha = 80;
        int count = 100;

        for (int i = 0; i < total; i++) {
            canvas.rotate(degreeSingle);

            int alpha = startAlpha;
            if (i > total - count) {
                alpha = (int) (startAlpha + (255 - startAlpha) * 1.0f / (count) * (i+1 - (total - count)));
                if (alpha > 255)
                    alpha = 255;
            }
            paint.setColor(Color.argb(alpha, 255, 255, 255));
            canvas.drawLine(getWidth() / 2 - 4*lineLength, 0, getWidth() / 2 - 3*lineLength, 0, paint);

        }

        canvas.restore();

        canvas.rotate(degress);
        canvas.rotate(360.f);

        // 最后一个那里画箭头
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        canvas.drawPath(pathArrow, paint);
        Log.e("info", "画箭头");

    }

}
