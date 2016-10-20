package com.arist.pathanimator.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.arist.pathanimator.R;

import java.util.ArrayList;

/**
 * 有动画的PageControl
 * Created by arist on 16/9/20.
 */
public class PageControlAnimated extends View {

    private static final int DEFAULT_ANIMATION_DURATION = 300;
    private int animationDuration;  // 动画效果周期

    private boolean isAnimating;    // 正在动画中，不能改
    private float mOrix;
    private int mCount;     // 总数
    private int mCurrentIndex;  // 当前索引
    private float mHeight;  // 小点的高度

    public int getmCurrentIndex() {
        return mCurrentIndex;
    }

    public void setmCurrentIndex(int mCurrentIndex) {
        this.mCurrentIndex = mCurrentIndex;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        if (mCount < 0)
            return;

        this.mCount = mCount;

        this.mCurrentIndex = 0;

        updateOrix();

        invalidate();
    }

    private long t;
    public void setCurrentIndex(int index, final boolean animated) {

        if (isAnimating || index < 0 && index >= mCount)
            return;

        mCurrentIndex = index;

        mList.get(0).end = mCurrentIndex * (mHeight + mSpacing);

        if (!animated) {
            mList.get(0).start = mList.get(0).end;
            mList.get(0).current = mList.get(0).end;
        }

        // 更新坐标
        for (int i = 1; i < mList.size(); i++) {
            Ball ball = mList.get(i);

            float end = (i - 1) * (mHeight + mSpacing) + (mCurrentIndex >= i ? 0 : mHeight * 2 + mSpacing);

            ball.end = end;

            if (!animated) {
                ball.start = ball.end;
                ball.current = ball.end;
            }

        }

        // 刷新View
        if (animated) {
            // 有动画

            // 开始执行动画
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setInterpolator(new OvershootInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
//                    float progress = (float) animation.getAnimatedFraction();
                    float progress = (float) animation.getAnimatedValue();
                    Log.e("info", "progress:" + progress);

                    for (int i = 0; i < mList.size(); i++) {
                        Ball ball = mList.get(i);
                        ball.current = ball.start + (ball.end - ball.start) * progress;
                    }

                    invalidate();
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimating = true;
                    t = System.currentTimeMillis();
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimating = false;
                    Log.e("info", "花费时间:" + (System.currentTimeMillis() - t) + "毫秒");
                    updateBallStart();
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                    isAnimating = false;
                    updateBallStart();
                }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
            valueAnimator.setDuration(animationDuration);
            valueAnimator.start();

        } else {
            // 无动画

            invalidate();
        }

    }

    private void updateBallStart(){
        for (int i = 0; i < mList.size(); i++) {
            Ball ball = mList.get(i);
            ball.start = ball.end;
        }
    }

    public PageControlAnimated(Context context) {
        super(context);

        initAttr(context, null);
    }

    public PageControlAnimated(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttr(context, attrs);
    }

    public PageControlAnimated(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs){
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSwitch);
            this.colorOn = a.getColor(R.styleable.CustomSwitch_colorOn, Color.parseColor("#FF4081"));
            this.colorOff = a.getColor(R.styleable.CustomSwitch_colorOff, Color.parseColor("#33FF4081"));
            this.animationDuration = a.getInt(R.styleable.CustomSwitch_animationDuration, DEFAULT_ANIMATION_DURATION);
        }
    }

    private int colorOn;    // 选中的颜色
    private int colorOff;   // 未选中的颜色

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
    private float mSpacing; // 小球之间的间距
    private ArrayList<Ball> mList = new ArrayList<>();     // 普通球
    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        mSpacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

        mCount = 10;

        updateOrix();

    }

    // 计算原点的位置
    private void updateOrix(){

        mList.clear();

        if (mCount <= 0)
            return;

        float width = mHeight * (mCount + 1) + mSpacing * (mCount - 1);

        mOrix = getWidth() / 2 - width / 2;

        float x = 0;

        for (int i = 0; i < mCount; i++) {
            Ball ball = new Ball();
            ball.isRoundRect = i == 0;
            float w = mCurrentIndex == i ? mHeight * 2 : mHeight;

            ball.start = x;
            ball.current = x;

            mList.add(ball);

            x += w + mSpacing;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mPaint == null) {
            init();
        }

        if (mCurrentIndex < 0 && mCurrentIndex >= mCount)
            return;

        canvas.translate(mOrix, getHeight() / 2);

        for (int i = mCount-1; i >= 0; i--) {
            Ball ball = mList.get(i);

            mPaint.setColor(i == 0 ? colorOn : colorOff);

            ball.update(canvas, mPaint, mHeight);

        }

    }

    public interface OnSwitchChangeListener {
        public void onChange(PageControlAnimated view, int index);
    }

    private class Ball extends Object{
        public float start;
        public float current;
        public float end;
        public boolean isRoundRect; // 是否是圆角矩形

        public void update(Canvas canvas, Paint paint, float height){
            canvas.drawRoundRect(new RectF(current, -height / 2, current + (isRoundRect ? 2 : 1) * height , height / 2),
                    mHeight / 2, mHeight / 2, mPaint);
        }
    }

}
