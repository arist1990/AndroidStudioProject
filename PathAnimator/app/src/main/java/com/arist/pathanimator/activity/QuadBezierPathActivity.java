package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.arist.pathanimator.view.QuadBezierPathView;
import com.arist.pathanimator.R;

public class QuadBezierPathActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quad_bezier);

        init();


    }

    private QuadBezierPathView quadBezierPathView;
    private ImageView iv;
    private void init(){
        quadBezierPathView = (QuadBezierPathView) findViewById(R.id.bezierPathView);
        iv = (ImageView) findViewById(R.id.iv);
        _10dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
    }

    Path path;
    PathMeasure pathMeasure;
    float _10dp;
    public void onClick(View view){

        path = quadBezierPathView.getPath();
        if (path == null) {
            Toast.makeText(QuadBezierPathActivity.this, "Path为空，无法执行动画", Toast.LENGTH_SHORT);
            return;
        }

        iv.setVisibility(View.VISIBLE);

        pathMeasure = new PathMeasure(path, false);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(1500);
//        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setInterpolator(new OvershootInterpolator());
        /**
         *
         AccelerateInterpolator　　　　　     加速，开始时慢中间加速
         DecelerateInterpolator　　　 　　   减速，开始时快然后减速
         AccelerateDecelerateInterolator　   先加速后减速，开始结束时慢，中间加速
         AnticipateInterpolator　　　　　　  反向 ，先向相反方向改变一段再加速播放
         AnticipateOvershootInterpolator　   反向加回弹，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值
         BounceInterpolator　　　　　　　  跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100
         CycleIinterpolator　　　　　　　　 循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 * mCycles * Math.PI * input)
         LinearInterpolator　　　　　　　　 线性，线性均匀改变
         OvershottInterpolator　　　　　　  回弹，最后超出目的值然后缓慢改变到目的值
         TimeInterpolator　　　　　　　　   一个接口，允许你自定义interpolator，以上几个都是实现了这个接口
         */

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Log.e("e", "value:" + animation.getAnimatedValue().toString());

                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                float[] pointF = new float[2];
                pathMeasure.getPosTan(value, pointF, null);

                iv.setX(pointF[0] - _10dp);
                iv.setY(pointF[1] - _10dp);
            }
        });

        valueAnimator.start();

    }

}
