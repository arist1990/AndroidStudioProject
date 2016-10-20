package com.arist.pathanimator.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.arist.pathanimator.R;

/**
 * Created by arist on 16/9/11.
 */
public class IOSLoadingView extends View {

    public IOSLoadingView(Context context) {
        super(context);
    }

    public IOSLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IOSLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint;
    private float degress = 0;
    private int total = 12;
    private float degreeSingle = 360.f / total;
    private float lineLength;
    private void init(){

        setLayerType(LAYER_TYPE_SOFTWARE, null);

//        lineLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());

        lineLength = getWidth() / 6;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(lineLength / 3);

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

                if (degress >= 360.f)
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

        int startAlpha = 55;

        for (int i = 0; i < total; i++) {
            canvas.rotate(degreeSingle);

            int alpha = (int) (startAlpha + i*((255.0f - startAlpha) / total));

            if (alpha > 255)
                alpha = 255;
            int color = getResources().getColor(R.color.colorPrimaryDark);
            paint.setColor(Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color)));
//            paint.setColor(Color.argb(alpha, 0, 0, 0));
//            paint.setColor(Color.argb(alpha, 255, 255, 255));
            canvas.drawLine(getWidth() / 4 - lineLength/2, 0, getWidth() / 4 + lineLength/2, 0, paint);

        }

        canvas.restore();

    }

}
