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
public class MusicPlayProgressView extends View {

    private OnPlayingListener onPlayingListener;
    public void setOnPlayingListener(OnPlayingListener listener){
        this.onPlayingListener = listener;
    }

    private PlayingState playingState = PlayingState.none;
    public void setPlayingState(PlayingState state) {
        this.playingState = state;
        invalidate();
    }
    public PlayingState getPlayingState(){
        return this.playingState;
    }

    // 播放状态
    public enum PlayingState{
        none, playing
    }

    public MusicPlayProgressView(Context context) {
        super(context);
    }

    public MusicPlayProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicPlayProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgress(float progress) {
        this.progress = progress;

        invalidate();
    }

    public float getProgress() {
        return this.progress;
    }

    private float progress = 0.0f;

    private Path pathCircle;
    private Path pathProgress;
    private PathMeasure pathMeasureProgress;
    private Path pathPause;
    private Path pathPlaying;

    private float radius;
    private float lineWidth;
    private float lineWidthProgress;
    private Paint paint;

    private float radiusShap;   // 中间暂停开始的大小

    /**
     * 外层圆圈1dp,内层进度2dp
     */
    private void init(){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        int width = getWidth();
        int height = getHeight();

        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        lineWidthProgress = lineWidth * 2;

        paint = new Paint();
        paint.setColor(Color.parseColor("#FF00CD7E"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.SQUARE);

        radius = Math.min(width, height) / 2 - 2 * lineWidth;
        radius -= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

        radiusShap = radius / 2.0f;

        pathCircle = new Path();
        pathCircle.addCircle(0, 0, radius, Path.Direction.CW);

        pathProgress = new Path();
        pathProgress.addCircle(0, 0, radius - lineWidth, Path.Direction.CW);

        pathMeasureProgress = new PathMeasure(pathProgress, false);

        pathPause = new Path();

        pathPause.moveTo(-radiusShap/2, -radiusShap*1.732f/2);
        pathPause.lineTo(radiusShap, 0);
        pathPause.lineTo(-radiusShap/2, radiusShap*1.732f/2);
        pathPause.lineTo(-radiusShap/2, -radiusShap*1.732f/2);

        pathPause.close();

        pathPlaying = new Path();
        pathPlaying.moveTo(-radiusShap/2f, -radiusShap/1.5f);
        pathPlaying.lineTo(-radiusShap/2f, radiusShap/1.5f);

        pathPlaying.moveTo(radiusShap/2f, -radiusShap/1.5f);
        pathPlaying.lineTo(radiusShap/2f, radiusShap/1.5f);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (pathCircle == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(lineWidth);
        canvas.drawPath(pathCircle, paint);

        canvas.save();

        paint.setStrokeWidth(lineWidthProgress);
        Path pathProgressToDraw = new Path();
        pathMeasureProgress.getSegment(0, progress*pathMeasureProgress.getLength(), pathProgressToDraw, true);
        canvas.rotate(-90.f);
        canvas.drawPath(pathProgressToDraw, paint);

        canvas.restore();

        if (this.playingState == PlayingState.none) {
            // 画pathPause
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(pathPause, paint);
        } else {
            // 画pathPlaying
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(lineWidthProgress * 2);
            canvas.drawPath(pathPlaying, paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.playingState == PlayingState.none) {
                // 开始播放
                if (this.onPlayingListener != null) {
                    this.onPlayingListener.onStart(this);
                    return true;
                }
            } else if (this.playingState == PlayingState.playing) {
                // 暂停播放
                if (this.onPlayingListener != null) {
                    this.onPlayingListener.onPause(this);
                    return true;
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public interface OnPlayingListener {
        /**
         * 开始播放
         */
        public void onStart(MusicPlayProgressView view);

        /**
         * 暂停播放
         */
        public void onPause(MusicPlayProgressView view);
    }

}
