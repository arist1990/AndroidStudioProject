package com.arist.pathanimator.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arist.pathanimator.R;
import com.arist.pathanimator.entity.FrameF;
import com.arist.pathanimator.entity.SizeFCustom;
import com.arist.pathanimator.view.CustomPointView;

public class ValueAnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_value_animate);

        init();

    }

    private ImageView imageView;
    private void init(){
        imageView = (ImageView) findViewById(R.id.iv);
    }

    public void onStart(View view){

        PointF pointFStart = new PointF(getDP(160), getDP(20));
        PointF pointFEnd = new PointF(getDP(160), getDP(200));

        FrameF frameStart = new FrameF(pointFStart, new SizeFCustom(getDP(10), getDP(10)));
        FrameF frameEnd = new FrameF(pointFEnd, new SizeFCustom(getDP(100), getDP(100)));

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float v, Object o, Object t1) {

                Log.e("info", "v:" + v);

                FrameF frameStart = (FrameF) o;
                FrameF frameEnd = (FrameF) t1;

                PointF point1 = frameStart.getOrigin();
                PointF point2 = frameEnd.getOrigin();

                SizeFCustom size1 = frameStart.getSize();
                SizeFCustom size2 = frameEnd.getSize();

                float x = point1.x + v * (point2.x - point1.x);
                float y = point1.y + v * (point2.y - point1.y);

                float width = size1.getWidth() + v * (size2.getWidth() - size1.getWidth());
                float height = size1.getHeight() + v * (size2.getHeight() - size1.getHeight());

                x -= width / 2; // 居中显示

                PointF p = new PointF(x, y);

                return new FrameF(p, new SizeFCustom(width, height));
            }
        }, frameStart, frameEnd);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FrameF frameF = (FrameF) valueAnimator.getAnimatedValue();
                PointF pointF = frameF.getOrigin();
                SizeFCustom sizeF = frameF.getSize();

                imageView.setX(pointF.x);
                imageView.setY(pointF.y);

                RelativeLayout.LayoutParams flp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                flp.width = (int) sizeF.getWidth();
                flp.height = (int) sizeF.getHeight();

                imageView.setLayoutParams(flp);

            }
        });

        valueAnimator.setInterpolator(new BounceInterpolator());

        valueAnimator.setDuration(800);
        valueAnimator.start();

    }

    private float getDP(int num){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
    }

}
