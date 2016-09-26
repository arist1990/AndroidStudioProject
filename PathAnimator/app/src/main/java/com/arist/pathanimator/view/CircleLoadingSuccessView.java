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
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by arist on 16/9/11.
 */
public class CircleLoadingSuccessView extends View {

    private OnCircleLoadingListener onCircleLoadingListener;
    public void setOnCircleLoadingListener(OnCircleLoadingListener listener){
        this.onCircleLoadingListener = listener;
    }

    private DownLoadingState downLoadingState = DownLoadingState.notStart;
    public void setDownLoadingState(DownLoadingState state) {
        this.downLoadingState = state;
        invalidate();
    }
    public DownLoadingState getDownLoadingState(){
        return this.downLoadingState;
    }

    public enum DownLoadingState{
        notStart, downloading, completed
    }

    public CircleLoadingSuccessView(Context context) {
        super(context);
    }

    public CircleLoadingSuccessView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleLoadingSuccessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgress(float progress) {
        this.progress = progress;

        if (this.progress <= 0.0f) {
            this.downLoadingState = DownLoadingState.notStart;
        }

        if (this.progress >= 1.0f) {
            this.downLoadingState = DownLoadingState.completed;

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    progressPathRight = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.setDuration(300);
            valueAnimator.start();
        }

        if (this.progress > 0 && this.downLoadingState == DownLoadingState.notStart) {
            this.downLoadingState = DownLoadingState.downloading;
        }

        invalidate();
    }

    private float progress = 0.0f;

    private Path pathCircle;
    private Path pathRight;
    private PathMeasure pathMeasureCircle;
    private PathMeasure pathMeasureRight;

    private float progressPathRight;

    private float radius;
    private float lineWidth;
    private Paint paint;
    private Paint paintText;
    private void init(){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        int width = getWidth();
        int height = getHeight();

        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setShader(new LinearGradient(-getWidth()/2, -getHeight()/2, getWidth(), getHeight()/2,
                Color.RED, Color.BLACK, Shader.TileMode.CLAMP));

        paintText = new Paint();
        paintText.setColor(Color.BLUE);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));

        radius = Math.min(width, height) / 2 - 2 * lineWidth;
        radius -= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

        Path p = new Path();
        p.addCircle(0, 0, radius, Path.Direction.CW);

        Matrix matrix = new Matrix();
        matrix.setRotate(-90.0f);

        pathCircle = new Path();
        pathCircle.addPath(p, matrix);

        pathRight = new Path();
        pathRight.moveTo(-radius / 2, radius / 8);
        pathRight.lineTo(0, radius / 2);
        pathRight.lineTo(radius / 2, -radius / 2);

        pathMeasureCircle = new PathMeasure(pathCircle, false);
        float length1 = pathMeasureCircle.getLength();

        pathMeasureRight = new PathMeasure(pathRight, false);
        float length2 = pathMeasureRight.getLength();

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

        if (this.downLoadingState == DownLoadingState.notStart) {
            // 画圆
            canvas.drawPath(pathCircle, paint);

            // 写两个字，开始
            paintText.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
            drawText(canvas, "开始");

        } else if (this.downLoadingState == DownLoadingState.downloading) {

            Path pathCircleDraw = new Path();
            boolean flag = pathMeasureCircle.getSegment(0, progress * pathMeasureCircle.getLength(), pathCircleDraw, true);

            canvas.drawPath(pathCircleDraw, paint);

            String text = (int)(progress * 100) + "%";

            paintText.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
            drawText(canvas, text);

        } else if (this.downLoadingState == DownLoadingState.completed) {
            // 画圆
            canvas.drawPath(pathCircle, paint);

            // 画勾
            Path pathRightDraw = new Path();
            boolean flag =  pathMeasureRight.getSegment(0, progressPathRight * pathMeasureRight.getLength(), pathRightDraw, true);

            canvas.drawPath(pathRightDraw, paint);
        }

    }

    /**
     * 居中画文字
     * @param canvas
     * @param text
     */
    private void drawText(Canvas canvas, String text) {
        // 保存画布状态
        canvas.save();

        // 画进度文字
        Rect rect = new Rect();
        paintText.getTextBounds(text, 0, text.length(), rect);

        canvas.translate(-rect.width() / 2, rect.height() / 2 - lineWidth);
        canvas.drawText(text, 0, 0, paintText);

        // 重置画布状态为保存前
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.downLoadingState == DownLoadingState.notStart) {
                // 开始下载
                if (this.onCircleLoadingListener != null) {
                    this.onCircleLoadingListener.onStart(this);
                    return true;
                }
            } else if (this.downLoadingState == DownLoadingState.downloading) {
                // 暂停下载
                if (this.onCircleLoadingListener != null) {
                    this.onCircleLoadingListener.onStop(this);
                    return true;
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public interface OnCircleLoadingListener {
        /**
         * 开始下载
         */
        public void onStart(CircleLoadingSuccessView circleLoadingSuccessView);

        /**
         * 终止下载
         */
        public void onStop(CircleLoadingSuccessView circleLoadingSuccessView);
    }

}
