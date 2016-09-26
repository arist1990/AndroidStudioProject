package com.arist.pathanimator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by arist on 16/9/1.
 */
public class QuadBezierPathView extends View {

    private PointF pointCurrent;
    private PointF pointStart;
    private PointF pointEnd;

    private Paint paint = new Paint();
    private Paint paintDot = new Paint();

    public QuadBezierPathView(Context context) {
        super(context);
    }

    public QuadBezierPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuadBezierPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Path path = new Path();

    public Path getPath(){
        return this.path;
    }

    private int dotSize = 0;
    private int lineSize = 0;
    private int _10dp;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (pointStart == null) {
            _10dp = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));

            dotSize = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            lineSize = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));

            pointStart = new PointF(dotSize / 2 + _10dp * 10, this.getHeight() / 2 - dotSize / 2);
            pointEnd = new PointF(this.getWidth() - dotSize / 2 - _10dp * 10, this.getHeight() / 2 - dotSize / 2);
            pointCurrent = new PointF(pointStart.x / 2 + pointEnd.x / 2, pointStart.y / 2 + pointEnd.y / 2);

            paint.setStrokeWidth(lineSize);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);

            paintDot.setColor(Color.RED);
            paintDot.setStyle(Paint.Style.FILL);
            paintDot.setAntiAlias(true);
        }

        if (pointCurrent != null) {

            path.reset();
            path.moveTo(pointStart.x, pointStart.y);
            path.quadTo(pointCurrent.x, pointCurrent.y, pointEnd.x, pointEnd.y);

            canvas.drawPath(path, paint);

            // 画手指点
            canvas.drawCircle(pointCurrent.x, pointCurrent.y, dotSize / 2, paintDot);

        }

        // 画左边点
        canvas.drawCircle(pointStart.x, pointStart.y, dotSize/2, paintDot);
        // 画右边点
        canvas.drawCircle(pointEnd.x, pointEnd.y, dotSize/2, paintDot);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        pointCurrent = new PointF(event.getX(), event.getY());

        invalidate();

        return true;
    }


}
