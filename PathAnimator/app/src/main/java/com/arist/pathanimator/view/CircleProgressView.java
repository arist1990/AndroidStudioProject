package com.arist.pathanimator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by arist on 16/9/11.
 */
public class CircleProgressView extends View {

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgress(float progress) {
        this.progress = progress;

        invalidate();
    }

    private float progress = 0.0f;

    private Path pathCircle;
    private PathMeasure pathMeasureCircle;

    private float radius;
    private float lineWidth;
    private Paint paint;
    private void init(){

        int width = getWidth();
        int height = getHeight();

        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);

        radius = Math.min(width, height) / 2 - 2 * lineWidth;
        radius -= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

        Path p = new Path();
//        p.addArc(0, 0, radius, Path.Direction.CW);
//        p.addArc(new RectF());

        Matrix matrix = new Matrix();
        matrix.setRotate(-90.0f);

        pathCircle = new Path();
//        pathCircle.addArc(new RectF(-radius, -radius, radius, radius), );
        pathCircle.addPath(p, matrix);

        pathMeasureCircle = new PathMeasure(pathCircle, false);
        float length1 = pathMeasureCircle.getLength();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#aaaaaa"));

        if (pathCircle == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);
        paint.setStrokeWidth(lineWidth);

        Path pathCircleDraw = new Path();
        boolean flag = pathMeasureCircle.getSegment(0, progress * pathMeasureCircle.getLength(), pathCircleDraw, true);

        canvas.drawPath(pathCircleDraw, paint);

    }

}
