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
public class PathTestView extends View {

    public PathTestView(Context context) {
        super(context);
    }

    public PathTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgress(float progress) {
        this.progress = progress;

        invalidate();
    }

    private float progress = 0.0f;

    private Path path;
    private Path pathProgress;

    private float progressRound1;   // 左侧圆占进度条的百分比
    private float progressRound2;

    private float lineWidth;
    private float padding;
    private Paint paint;
    private Paint paintProgress;
    private float radius;
    private float xStart, xEnd, _2width;
    private void init(){

        int width = getWidth();
        int height = getHeight();

        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);

        paintProgress = new Paint();
        paintProgress.setColor(Color.BLUE);
        paintProgress.setAntiAlias(true);
        paintProgress.setStyle(Paint.Style.FILL);

        _2width = width / 2 - padding;
        radius = height / 2 - padding;

        xStart = -_2width;
        xEnd = _2width;

        path = new Path();
        path.addRoundRect(new RectF(-_2width, -radius, _2width, radius), _2width, _2width, Path.Direction.CCW);

        pathProgress = new Path();

        progressRound1 = radius / (_2width * 2);
        progressRound2 = 1.0f - radius / (_2width * 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#aaaaaa"));

        if (path == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);


        pathProgress.reset();

        float currentX = progress * 2 * _2width - _2width;
        pathProgress.moveTo(-_2width, -radius);
        pathProgress.lineTo(-_2width, radius);
        pathProgress.lineTo(currentX, radius);
        pathProgress.lineTo(currentX, -radius);
        pathProgress.close();

        canvas.drawPath(pathProgress, paintProgress);


        canvas.drawPath(path, paint);

    }

}
