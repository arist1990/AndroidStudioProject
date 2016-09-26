package com.arist.pathanimator.view;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.arist.pathanimator.R;
import com.arist.pathanimator.animator.CustomValueAnimator;

import java.util.ArrayList;

/**
 * Created by arist on 16/9/11.
 */
public class BallLoadingView extends View {

    public BallLoadingView(Context context) {
        super(context);
    }

    public BallLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BallLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ArrayList<ValueAnimator> animatorArrayList = new ArrayList<>();
    private ArrayList<ValueAnimator> animatorColorArrayList = new ArrayList<>();
    public void startLoading(){

        stopLoading();

        for (int i = 0; i < ballRadiusList.size(); i++) {
            CustomValueAnimator valueAnimator = CustomValueAnimator.ofFloat(radiusBallMin, radiusBallMax);
            valueAnimator.setTag("" + i);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int tag = Integer.parseInt(((CustomValueAnimator)animation).getTag() + "");
                    float progress = (float) animation.getAnimatedValue();
                    ballRadiusList.set(tag, progress);

                    invalidate();
                }
            });
            valueAnimator.setDuration(1500);
            valueAnimator.setStartDelay(valueAnimator.getDuration() / ballCount * i);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

            valueAnimator.setInterpolator(new OvershootInterpolator());
            valueAnimator.setInterpolator(new BounceInterpolator());
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setInterpolator(new AnticipateOvershootInterpolator());
            valueAnimator.setInterpolator(new CycleInterpolator(2));        // 杂乱无章
            valueAnimator.setInterpolator(new AccelerateInterpolator());    // 最有动感
            valueAnimator.setInterpolator(new AnticipateInterpolator());    // 有动感

            valueAnimator.start();
            animatorArrayList.add(valueAnimator);

            // 控制颜色变换
            CustomValueAnimator valueAnimatorColor = CustomValueAnimator.ofObject(new ArgbEvaluator(),
                    getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary));
            valueAnimatorColor.setTag("" + i);
            valueAnimatorColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int tag = Integer.parseInt(((CustomValueAnimator)animation).getTag() + "");

                    // 计算颜色
                    ballColorList.set(tag, (Integer) animation.getAnimatedValue());

//                    Log.e("info", "color:" + String.format("%x", (Integer) animation.getAnimatedValue()));

                    invalidate();
                }
            });
            valueAnimatorColor.setDuration(3000);
            valueAnimatorColor.setStartDelay(valueAnimatorColor.getDuration() / ballCount * i);
            valueAnimatorColor.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimatorColor.setRepeatMode(ValueAnimator.REVERSE);

            valueAnimatorColor.setInterpolator(new LinearInterpolator());

            valueAnimatorColor.start();

            animatorColorArrayList.add(valueAnimatorColor);
        }

    }

    public void stopLoading(){
        for (ValueAnimator valueAnimator : animatorArrayList) {
            valueAnimator.cancel();
        }
        animatorArrayList.clear();

        for (ValueAnimator valueAnimator : animatorColorArrayList) {
            valueAnimator.cancel();
        }
        animatorColorArrayList.clear();
    }

    private ArrayList<Float> ballXList;         // 球的x坐标
    private ArrayList<Float> ballRadiusList;    // 球的动态半径
    private ArrayList<Integer> ballColorList;    // 球的颜色

    private float ballSpacing;
    private float radiusBallMax;   // 球最大半径
    private float radiusBallMin;   // 球最小半径
    private int ballCount;  // 球的数量

    private Paint paint;

    private void init(){
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        ballSpacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        radiusBallMax = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
        radiusBallMin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

        ballCount = 5;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

//        paint.setShadowLayer(1, ballSpacing/2, ballSpacing/2, Color.LTGRAY);

//        paint.setShader(new LinearGradient(-getWidth()/2, 0, getWidth()/2, 0,
//                getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary), Shader.TileMode.CLAMP));

        ballXList = new ArrayList<>();
        ballRadiusList = new ArrayList<>();
        ballColorList = new ArrayList<>();

        // 所有球的总长度为 2*radiusBallMax*ballCount + (ballCount-1)*ballSpacing

        float allWidth = 2*radiusBallMax*ballCount + (ballCount-1)*ballSpacing;
        float startX = 0 - allWidth / 2;

        for (int i = 0; i < ballCount; i++) {
            float x = startX + radiusBallMax + (2*radiusBallMax + ballSpacing) * i;
            ballXList.add(x);

            ballRadiusList.add(radiusBallMin);
            ballColorList.add(getResources().getColor(R.color.colorAccent));
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (paint == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);

        // 动态绘制圆点
        for (int i = 0; i < ballXList.size(); i++) {
            float x = ballXList.get(i);

            int color = ballColorList.get(i);
            paint.setColor(ballColorList.get(i));

            // 设置每个小球的阴影
            paint.setShadowLayer(1, ballSpacing/4, ballSpacing/4,
                    Color.argb(0x99, Color.red(color), Color.green(color), Color.blue(color)));

            canvas.drawCircle(x, 0, ballRadiusList.get(i), paint);

        }

    }

}
