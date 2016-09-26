package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.arist.pathanimator.view.PathView;
import com.arist.pathanimator.R;

import java.util.HashMap;

public class PathAnimationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bezier_animation);

        init();


    }

    private PathView pathView;
    private ImageView iv;
    private void init(){
        pathView = (PathView) findViewById(R.id.bezierPathView);
        iv = (ImageView) findViewById(R.id.iv);
        _10dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
    }

//    PathMeasure pathMeasure;
    float _10dp;
    private HashMap<String, PathMeasure> pathMeasureMap = new HashMap<>();
    public void onClick(View view){

        if (pathView.getPathArrayList().isEmpty())
            return;

        pathMeasureMap.clear();

        long delay = 0;
        for (int i = 0; i < pathView.getPathArrayList().size(); i++) {
            Path path = pathView.getPathArrayList().get(i);

            final PathMeasure pathMeasure = new PathMeasure(path, false);

            float length = pathMeasure.getLength();

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, length);
            valueAnimator.setDuration((int) length);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.hashCode();
            pathMeasureMap.put("" + valueAnimator.hashCode(), pathMeasure);

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    // 获取当前点坐标封装到mCurrentPosition
                    float[] pointF = new float[2];
                    PathMeasure pathMeasure1 = pathMeasureMap.get("" + animation.hashCode());
                    pathMeasure1.getPosTan(value, pointF, null);

                    iv.setX(pointF[0] - _10dp);
                    iv.setY(pointF[1] - _10dp);
                }
            });
            valueAnimator.setStartDelay(delay);
            delay += valueAnimator.getDuration();

            valueAnimator.start();
        }

    }

    public void onClear(View view){
//        pathView.getPath().reset();
        pathView.getPathArrayList().clear();
        pathView.invalidate();
    }

}
