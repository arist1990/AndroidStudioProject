package com.arist.pathanimator.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.arist.pathanimator.R;

/**
 * Created by arist on 16/9/20.
 */
public class CustomSwitch extends View {

    private OnSwitchChangeListener onSwitchChangeListener;

    public OnSwitchChangeListener getOnSwitchChangeListener() {
        return onSwitchChangeListener;
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        this.onSwitchChangeListener = onSwitchChangeListener;
    }

    private static final int DEFAULT_ANIMATION_DURATION = 300;
    private int animationDuration;

    public State mState = State.OFF;
    private boolean isAnimating;    // 正在动画中，不能改
    private float x = 0.0f;
    private float orix;
    private int colorCurrent;

    public State getState() {
        return mState;
    }

    public void updateState(final State state, final boolean animated) {

        if (isAnimating)
            return;

        if (this.mState != state) {
            if (animated) {
                // 需要动画时，状态转换
                int color1;
                int color2;
                float x1;
                float x2;
                if (this.mState == State.ON) {
                    this.mState = State.ANI_ON_TO_OFF;
                    color1 = colorOn;
                    color2 = colorOff;
                    x1 = orix;
                    x2 = -orix;
                } else {
                    this.mState = State.ANI_OFF_TO_ON;
                    color1 = colorOff;
                    color2 = colorOn;
                    x1 = -orix;
                    x2 = orix;
                }
                // 开始执行动画
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(x1, x2);
                valueAnimator.setInterpolator(new OvershootInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        x = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isAnimating = true;
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimating = false;
                        if (mState == State.ANI_ON_TO_OFF) {
                            mState = State.OFF;
                        } if (mState == State.ANI_OFF_TO_ON){
                            mState = State.ON;
                        }
                        invalidate();
                        if (onSwitchChangeListener != null) {
                            onSwitchChangeListener.onChange(CustomSwitch.this, mState);
                        }
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        isAnimating = false;
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                });
                valueAnimator.setDuration(animationDuration);
                valueAnimator.start();

                ValueAnimator colorAnimator = valueAnimator.ofObject(new ArgbEvaluator(), color1, color2);
                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        colorCurrent = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                colorAnimator.setDuration(animationDuration);
                colorAnimator.start();

            } else {
                if (this.mState == State.ON) {
                    this.mState = State.OFF;
                    x = -orix;
                } else {
                    this.mState = State.ON;
                    x = orix;
                }

                invalidate();

                if (onSwitchChangeListener != null) {
                    onSwitchChangeListener.onChange(CustomSwitch.this, mState);
                }
            }
        }

    }

    public enum State{
        ON,
        OFF,
        ANI_ON_TO_OFF,
        ANI_OFF_TO_ON
    }

    public CustomSwitch(Context context) {
        super(context);

        initAttr(context, null);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttr(context, attrs);
    }

    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs){
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSwitch);
            this.colorOn = a.getColor(R.styleable.CustomSwitch_colorOn, Color.parseColor("#FF4081"));
            this.colorOff = a.getColor(R.styleable.CustomSwitch_colorOff, Color.LTGRAY);
            boolean isOn = a.getBoolean(R.styleable.CustomSwitch_isOn, false);
            this.mState = isOn ? State.ON : State.OFF;
            this.animationDuration = a.getInt(R.styleable.CustomSwitch_animationDuration, DEFAULT_ANIMATION_DURATION);
        }
    }

    private int colorOn;
    private int colorOff;

    public int getColorOn() {
        return colorOn;
    }

    public void setColorOn(int colorOn) {
        this.colorOn = colorOn;
        invalidate();
    }

    public int getColorOff() {
        return colorOff;
    }

    public void setColorOff(int colorOff) {
        this.colorOff = colorOff;
        invalidate();
    }

    private Paint mPaint;
    private Path path;
    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));

        path = new Path();

        x = mState == State.ON ? orix : -orix;
    }

    private float roundRectRadius;
    private float circleRadius;
    private float padding;

    @Override
    protected void onDraw(Canvas canvas) {

        if (padding <= 0) {
            padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        }

        float width = getWidth() - padding * 2;
        float height = getHeight() - padding;

        // 外层轮廓的圆角弧度
        roundRectRadius = height / 2.0f;

        // 内层小圆的圆角弧度
//        circleRadius = roundRectRadius - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
        circleRadius = roundRectRadius * 0.6f;

        // 圆点 起点与终点的x
        orix = width / 2.0f - roundRectRadius;

        if (mPaint == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);

        path.reset();
        path.addRoundRect(new RectF(-width / 2, -height / 2, width / 2, height / 2), roundRectRadius, roundRectRadius, Path.Direction.CCW);

        if (this.mState == State.ON) {
            colorCurrent = colorOn;
        }
        if (this.mState == State.OFF) {
            colorCurrent = colorOff;
        }

        mPaint.setColor(colorCurrent);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);

//        if (this.mState == State.OFF) {
//            // 画环
//            mPaint.setStyle(Paint.Style.STROKE);
//            canvas.drawCircle(x + mPaint.getStrokeWidth(), 0, circleRadius, mPaint);
//        } else {
//            // 画圆
//            mPaint.setStyle(Paint.Style.FILL);
//            canvas.drawCircle(x, 0, circleRadius, mPaint);
//        }

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, 0, circleRadius, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!isAnimating) {
                updateState(this.mState == State.ON ? State.OFF : State.ON, true);
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    public interface OnSwitchChangeListener {
        public void onChange(CustomSwitch customSwitch, State state);
    }

}
