package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.arist.pathanimator.view.CubicBezierPathView;
import com.arist.pathanimator.R;

public class CubicBezierPathActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cubic_bezier);

        init();


    }

    private CubicBezierPathView cubicBezierPathView;
    private ImageView iv;
    private RadioButton rb1, rb2;
    private void init(){
        cubicBezierPathView = (CubicBezierPathView) findViewById(R.id.bezierPathView);
        iv = (ImageView) findViewById(R.id.iv);
        _10dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        ((RadioGroup) findViewById(R.id.rg)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                cubicBezierPathView.setMode(i == R.id.rb1);
            }
        });

    }

    Path path;
    PathMeasure pathMeasure;
    float _10dp;
    public void onClick(View view){

        path = cubicBezierPathView.getPath();
        if (path == null) {
            Toast.makeText(CubicBezierPathActivity.this, "Path为空，无法执行动画", Toast.LENGTH_SHORT);
            return;
        }

        iv.setVisibility(View.VISIBLE);

        pathMeasure = new PathMeasure(path, false);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
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
