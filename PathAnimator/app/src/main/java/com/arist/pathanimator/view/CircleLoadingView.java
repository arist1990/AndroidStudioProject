package com.arist.pathanimator.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.arist.pathanimator.animator.CustomValueAnimator;

import java.util.ArrayList;

/**
 * Created by arist on 16/9/11.
 */
public class CircleLoadingView extends View {

    public CircleLoadingView(Context context) {
        super(context);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ArrayList<ValueAnimator> animatorArrayList = new ArrayList<>();
    public void startLoading(){

        for (int i = 0; i < pathMeasuresArrayList.size(); i++) {
            CustomValueAnimator valueAnimator = CustomValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setTag("" + i);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int tag = Integer.parseInt(((CustomValueAnimator)animation).getTag() + "");
                    float progress = (float) animation.getAnimatedValue();
                    progressList.set(tag, progress);

                    invalidate();
                }
            });
            valueAnimator.setDuration(3000 - i * 1000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());

            valueAnimator.start();

            animatorArrayList.add(valueAnimator);
        }

    }

    public void stopLoading(){
        for (ValueAnimator valueAnimator : animatorArrayList) {
            valueAnimator.cancel();
        }
        animatorArrayList.clear();
    }

    private ArrayList<Path> pathArrayList;
    private ArrayList<PathMeasure> pathMeasuresArrayList;
    private ArrayList<Float> progressList;

    private float lineWidth;    // 线宽
    private float padding;  // 控件的内间距
    private float lineSpacing;

    private float radius;   // 最外层圆半径
    private float circleRadius; // 绘制小圆球的半径
    private float innerCircleRadius;    // 最内层小圆球半径

    private Paint paint;

    private void init(){
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        int width = getWidth();
        int height = getHeight();

        lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        innerCircleRadius = padding;

        circleRadius = lineWidth * 3;

        lineSpacing = padding;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 60, getResources().getDisplayMetrics()));

        paint.setShader(new LinearGradient(0, getHeight()/2, getWidth(), getHeight()/2,
                0xff000000, 0xff00ff00, Shader.TileMode.CLAMP));

        radius = Math.max(width, height) / 2 - padding - lineWidth / 2;

        pathArrayList = new ArrayList<>();
        pathMeasuresArrayList = new ArrayList<>();
        progressList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Path path = new Path();

            // 圆形轨道
//            path.addCircle(0, 0, radius - i * lineSpacing, Path.Direction.CW);

            // 椭圆轨道
            float r = radius - i * lineSpacing;
            float x = r + (i % 2 == 0 ? -1 : 1) * lineWidth * 2;
            float y = r + (i % 2 == 0 ? 1 : -1) * lineWidth * 2;
            path.addOval(new RectF(-x, -y, x, y), Path.Direction.CW);

            pathArrayList.add(path);
            pathMeasuresArrayList.add(new PathMeasure(path, false));
            progressList.add(0.0f);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.parseColor("#aaaaaa"));

        if (pathArrayList == null) {
            init();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(-90);

        for (int i = 0; i < pathArrayList.size(); i++) {
            Path path = pathArrayList.get(i);
            PathMeasure pathMeasure = pathMeasuresArrayList.get(i);

            // 绘制圆点运行的路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);

            // 绘制动态圆点
            float[] point = new float[2];
            boolean flag = pathMeasure.getPosTan(progressList.get(i)*pathMeasure.getLength(), point, null);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawCircle(point[0], point[1], circleRadius, paint);

        }

        // 绘制中心的圆
        canvas.drawCircle(0, 0, innerCircleRadius, paint);

    }

}
