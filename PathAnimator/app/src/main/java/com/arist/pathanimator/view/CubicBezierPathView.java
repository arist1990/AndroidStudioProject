package com.arist.pathanimator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by arist on 16/9/1.
 */
public class CubicBezierPathView extends View {

    private PointF pointStart;
    private PointF pointEnd;

    private Paint paint = new Paint();
    private Paint paintDot = new Paint();

    public CubicBezierPathView(Context context) {
        super(context);
    }

    public CubicBezierPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CubicBezierPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Path path = new Path();

    public Path getPath(){
        return this.path;
    }

    private Boolean mode = true;    // 当前的控制点是否为第一个控制点
    private PointF pointControl1;   // 第一个控制点
    private PointF pointControl2;   // 第一个控制点
    public void setMode(boolean mode){
        this.mode = mode;
    }

    private int dotSize = 0;
    private int lineSize = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (pointStart == null) {
            dotSize = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            lineSize = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));

            pointStart = new PointF(dotSize / 2, this.getHeight() / 2 - dotSize / 2);
            pointEnd = new PointF(this.getWidth() - dotSize / 2, this.getHeight() / 2 - dotSize / 2);
            pointControl1 = new PointF(pointEnd.x / 4 + pointStart.x / 4, pointStart.y);
            pointControl2 = new PointF(pointEnd.x * 3 / 4 + pointStart.x * 3 / 4, pointStart.y);

            paint.setStrokeWidth(lineSize);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);

            paintDot.setColor(Color.RED);
            paintDot.setStyle(Paint.Style.FILL);
            paintDot.setAntiAlias(true);
        }


        path.reset();
        path.moveTo(pointStart.x, pointStart.y);
        path.cubicTo(pointControl1.x, pointControl1.y, pointControl2.x, pointControl2.y, pointEnd.x, pointEnd.y);

        canvas.drawPath(path, paint);


        // 画左边点
        canvas.drawCircle(pointStart.x, pointStart.y, dotSize/2, paintDot);
        // 画右边点
        canvas.drawCircle(pointEnd.x, pointEnd.y, dotSize/2, paintDot);

        // 画手指点
        canvas.drawCircle(pointControl1.x, pointControl1.y, dotSize / 2, paintDot);
        canvas.drawCircle(pointControl2.x, pointControl2.y, dotSize / 2, paintDot);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mode) {
            pointControl1 = new PointF(event.getX(), event.getY());
        } else {
            pointControl2 = new PointF(event.getX(), event.getY());
        }

        invalidate();

        return true;
    }


}
