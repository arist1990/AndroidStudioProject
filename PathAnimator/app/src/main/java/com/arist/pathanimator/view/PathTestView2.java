package com.arist.pathanimator.view;

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
import android.view.View;

/**
 * Created by arist on 16/9/11.
 */
public class PathTestView2 extends View {

    public PathTestView2(Context context) {
        super(context);
    }

    public PathTestView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathTestView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgress(float progress) {
        this.progress = progress;

        invalidate();
    }

    private float progress = 0.0f;

    private Path path;
    private PathMeasure pathMeasure;

    private float lineWidth;    // 线宽
    private float padding;  // 控件的内间距
    private Paint paint;
    private float radius;

    private void init(){

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        int width = getWidth();
        int height = getHeight();

        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 60, getResources().getDisplayMetrics()));

        paint.setShader(new LinearGradient(-getWidth()/2, 0, getWidth()/2, 0,
                Color.RED, Color.BLACK, Shader.TileMode.CLAMP));
        paint.setShader(new SweepGradient(0, 0,
                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.RED}, new float[]{0.0f, 0.3f, 0.7f, 1.0f}));
        paint.setShader(new SweepGradient(0, 0,
                new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.GREEN}, new float[]{0.0f, 0.3f, 0.70f, 1.0f}));

        radius = Math.max(width, height) / 2 - padding - lineWidth / 2;

        path = new Path();
        path.addCircle(0, 0, radius, Path.Direction.CW);
//        path.addArc(new RectF(-radius, -radius, radius, radius), 0, 180.0f);

        pathMeasure = new PathMeasure(path, false);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.parseColor("#aaaaaa"));

        if (path == null) {
            init();
        }


        int saveCount = canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), paint, Canvas.ALL_SAVE_FLAG);

        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(-90);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
//        canvas.drawPath(path, paint);

        Path pathProgress = new Path();

        boolean flag = pathMeasure.getSegment(0, progress*pathMeasure.getLength(), pathProgress, true);

        paint.setColor(Color.RED);
        canvas.drawPath(pathProgress, paint);

        canvas.restoreToCount(saveCount);


        String text = (int)(progress * 100) + "%";
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);
        paint.setStrokeWidth(lineWidth);

    }

}
