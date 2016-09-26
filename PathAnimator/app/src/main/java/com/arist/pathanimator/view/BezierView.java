package com.arist.pathanimator.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arist.pathanimator.R;


public class BezierView extends FrameLayout {

    // 默认定点圆半径
    public static float DEFAULT_RADIUS = 20;

    private Paint paint;
    private Path path;

    // 手势坐标
    float x = 400;
    float y = 400;

    // 锚点坐标
    float anchorX = 300;
    float anchorY = 400;

    // 起点坐标
    float startX = 200;
    float startY = 200;

    // 定点圆半径
    float radius = DEFAULT_RADIUS;

    // 判断动画是否开始
    boolean isAnimStart;
    // 判断是否开始拖动
    boolean isTouch;

    ImageView exploredImageView;
    TextView tipImageView;

    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private float _10dp;
    private void init(){

        _10dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

        DEFAULT_RADIUS = _10dp;
        radius = _10dp;

        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);

        LayoutParams params = new LayoutParams((int) (_10dp * 3), (int) (_10dp * 3));

        exploredImageView = new ImageView(getContext());
        exploredImageView.setLayoutParams(params);
        exploredImageView.setImageResource(R.drawable.tip_anim);
        exploredImageView.setVisibility(View.INVISIBLE);

        tipImageView = new TextView(getContext());
        tipImageView.setLayoutParams(new LayoutParams((int) (_10dp * 1.5), (int) (_10dp * 1.5)));
        tipImageView.setBackgroundResource(R.drawable.drawable_color_red_circle);
        tipImageView.setTextColor(Color.WHITE);
        tipImageView.setGravity(Gravity.CENTER);
        tipImageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tipImageView.setText("9+");

        addView(tipImageView);
        addView(exploredImageView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        exploredImageView.setX(startX - exploredImageView.getWidth()/2);
        exploredImageView.setY(startY - exploredImageView.getHeight()/2);

        tipImageView.setX(startX - tipImageView.getWidth()/2);
        tipImageView.setY(startY - tipImageView.getHeight()/2);

        super.onLayout(changed, left, top, right, bottom);
    }

    private void calculate(){
        float distance = (float) Math.sqrt(Math.pow(y-startY, 2) + Math.pow(x-startX, 2));
        radius = -distance/15 + DEFAULT_RADIUS;

        if(radius < 9){
            isAnimStart = true;

            exploredImageView.setVisibility(View.VISIBLE);
            exploredImageView.setImageResource(R.drawable.tip_anim);

            ((AnimationDrawable) exploredImageView.getDrawable()).stop();
            ((AnimationDrawable) exploredImageView.getDrawable()).start();

            tipImageView.setVisibility(View.GONE);
        }

        // 根据角度算出四边形的四个点
        float offsetX = (float) (radius* Math.sin(Math.atan((y - startY) / (x - startX))));
        float offsetY = (float) (radius* Math.cos(Math.atan((y - startY) / (x - startX))));

        float x1 = startX - offsetX;
        float y1 = startY + offsetY;

        float x2 = x - offsetX;
        float y2 = y + offsetY;

        float x3 = x + offsetX;
        float y3 = y - offsetY;

        float x4 = startX + offsetX;
        float y4 = startY - offsetY;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);
        path.lineTo(x1, y1);

        // 更改图标的位置
        tipImageView.setX(x - tipImageView.getWidth()/2);
        tipImageView.setY(y - tipImageView.getHeight()/2);

    }

    @Override
    protected void onDraw(Canvas canvas){

        if(isAnimStart || !isTouch){

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);

        } else {
            calculate();

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
            canvas.drawPath(path, paint);
            canvas.drawCircle(startX, startY, radius, paint);
            canvas.drawCircle(x, y, radius, paint);
        }

        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 判断触摸点是否在tipImageView中
            Rect rect = new Rect();
            int[] location = new int[2];
            tipImageView.getDrawingRect(rect);
            tipImageView.getLocationOnScreen(location);
            rect.left = location[0];
            rect.top = location[1];
            rect.right = rect.right + location[0];
            rect.bottom = rect.bottom + location[1];
            if (rect.contains((int)event.getRawX(), (int)event.getRawY())) {
                isTouch = true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
            isTouch = false;

            final float animateStartX = tipImageView.getX();
            final float animateStartY = tipImageView.getY();
            final float animateEndX = startX - tipImageView.getWidth() / 2;
            final float animateEndY = startY - tipImageView.getHeight() / 2;

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setInterpolator(new OvershootInterpolator());
            valueAnimator.setDuration(300);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tipImageView.setX(animateEndX + (1-(float)valueAnimator.getAnimatedValue()) * (animateStartX - animateEndX));
                    tipImageView.setY(animateEndY + (1-(float)valueAnimator.getAnimatedValue()) * (animateStartY - animateEndY));
                    Log.e("info", "tipImageView x:" + tipImageView.getX());
                    Log.e("info", "tipImageView y:" + tipImageView.getY());
                }
            });
            valueAnimator.start();

        }

        invalidate();

        if(isAnimStart){
            return super.onTouchEvent(event);
        }

        anchorX =  (event.getX() + startX)/2;
        anchorY =  (event.getY() + startY)/2;

        x =  event.getX();
        y =  event.getY();

        return true;
    }


}
