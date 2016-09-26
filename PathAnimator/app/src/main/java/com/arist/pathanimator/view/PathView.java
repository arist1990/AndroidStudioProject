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

import java.util.ArrayList;

/**
 * Created by arist on 16/9/1.
 */
public class PathView extends View {

    private Paint paint = new Paint();

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ArrayList<Path> pathArrayList = new ArrayList<>();

    public ArrayList<Path> getPathArrayList(){
        return this.pathArrayList;
    }

    private int lineSize = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (lineSize == 0) {
            lineSize = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));

            paint.setStrokeWidth(lineSize);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);

        }

        for (Path path : pathArrayList)
            canvas.drawPath(path, paint);

    }

    private PointF pointFLast;  // 上一个点
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Path path = pathArrayList.get(pathArrayList.size()-1);
//            path.lineTo(event.getX(), event.getY());

            path.quadTo(pointFLast.x, pointFLast.y, event.getX(), event.getY());

            pointFLast = new PointF(event.getX(), event.getY());
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Path path = new Path();
            path.moveTo(event.getX(), event.getY());
            pathArrayList.add(path);

            pointFLast = new PointF(event.getX(), event.getY());
        }

        postInvalidate();

        return true;
    }


}
