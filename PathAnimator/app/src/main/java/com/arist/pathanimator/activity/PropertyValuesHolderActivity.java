package com.arist.pathanimator.activity;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.arist.pathanimator.R;
import com.arist.pathanimator.adapter.SimpleGridAdapter;
import com.arist.pathanimator.entity.FrameF;
import com.arist.pathanimator.entity.SizeFCustom;
import com.arist.pathanimator.view.CustomPointView;

import org.json.JSONObject;

import java.util.ArrayList;

public class PropertyValuesHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_property_values_holder);

        init();

    }

    private ImageView imageView;
    private CustomPointView customPointView;
    private GridView gridView;
    private SimpleGridAdapter simpleGridAdapter;
    private ArrayList<JSONObject> datas = new ArrayList<>();
    private void init(){
        imageView = (ImageView) findViewById(R.id.iv);
        customPointView = (CustomPointView) findViewById(R.id.customPointView);

        gridView = (GridView) findViewById(R.id.gridView);

        for (int i = 0; i < 160; i++) {
            JSONObject jsonObject = new JSONObject();
            datas.add(jsonObject);
        }
        simpleGridAdapter = new SimpleGridAdapter(this, datas);
        simpleGridAdapter.handler = handler;

        gridView.setAdapter(simpleGridAdapter);

    }

    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<Animator> animations = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x10) {
                Object object = msg.obj;
                if (object instanceof ImageView) {
                    if (!imageViews.contains(object)) {
                        imageViews.add((ImageView) object);
                    }
                }
            }
        }
    };

    public void onStart(View view){

        Keyframe keyframe0 = Keyframe.ofFloat(0.0f, 0.0f);

        Keyframe keyframe1 = Keyframe.ofFloat(0.25f, -5.0f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.75f, 5.0f);
        Keyframe keyframe3 = Keyframe.ofFloat(1.0f, 0.0f);

        PropertyValuesHolder holderKeyFrame = PropertyValuesHolder.ofKeyframe("rotation",
                keyframe0, keyframe1, keyframe2, keyframe3);

        for (int i = 0; i < imageViews.size(); i++) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageViews.get(i), holderKeyFrame);

            objectAnimator.setDuration(200);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.setInterpolator(new LinearInterpolator());
            objectAnimator.start();

            animations.add(objectAnimator);
        }


//        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("x", getDP(20));
//        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("x", getDP(100));
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder1, holder2);

//        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 0f, 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
//        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0x00ff0000, 0xffff0000, 0x00ff0000);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, rotationHolder, colorHolder);
//
//        objectAnimator.setDuration(3000);
//        objectAnimator.start();


        // KeyFrame

//        Keyframe keyframe0 = Keyframe.ofFloat(0.0f, getDP(100));
//
//        Keyframe keyframe1 = Keyframe.ofFloat(0.1f, getDP(110));
//        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, getDP(120));
//        Keyframe keyframe3 = Keyframe.ofFloat(0.3f, getDP(130));
//        Keyframe keyframe4 = Keyframe.ofFloat(0.4f, getDP(140));
//        Keyframe keyframe5 = Keyframe.ofFloat(0.5f, getDP(150));
//
//        Keyframe keyframe6 = Keyframe.ofFloat(0.6f, getDP(160));
//        Keyframe keyframe7 = Keyframe.ofFloat(0.7f, getDP(170));
//        Keyframe keyframe8 = Keyframe.ofFloat(0.8f, getDP(180));
//        Keyframe keyframe9 = Keyframe.ofFloat(0.9f, getDP(190));
//        Keyframe keyframe10 = Keyframe.ofFloat(1.0f, getDP(200));
//
//        PropertyValuesHolder holderKeyFrame = PropertyValuesHolder.ofKeyframe("y",
//                keyframe1, keyframe2, keyframe3, keyframe4, keyframe5,
//                keyframe6, keyframe7, keyframe8, keyframe9, keyframe10);
//
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderKeyFrame);
//
//        objectAnimator.setDuration(300);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        objectAnimator.start();


//        Keyframe keyframe0 = Keyframe.ofFloat(0.0f, 0);
//
//        Keyframe keyframe1 = Keyframe.ofFloat(0.1f, 10);
//        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 30);
//        Keyframe keyframe3 = Keyframe.ofFloat(0.3f, 60);
//        Keyframe keyframe4 = Keyframe.ofFloat(0.4f, 100);
//        Keyframe keyframe5 = Keyframe.ofFloat(0.5f, 150);
//
//        Keyframe keyframe6 = Keyframe.ofFloat(0.6f, 210);
//        Keyframe keyframe7 = Keyframe.ofFloat(0.7f, 280);
//        Keyframe keyframe8 = Keyframe.ofFloat(0.8f, 360);
//        Keyframe keyframe9 = Keyframe.ofFloat(0.9f, 450);
//        Keyframe keyframe10 = Keyframe.ofFloat(1.0f, 550);
//
//        PropertyValuesHolder holderKeyFrame = PropertyValuesHolder.ofKeyframe("rotation",
//                keyframe0, keyframe1, keyframe2, keyframe3, keyframe4, keyframe5,
//                keyframe6, keyframe7, keyframe8, keyframe9, keyframe10);
//
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderKeyFrame);
//
//        objectAnimator.setDuration(1000);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        objectAnimator.start();

        // shake
//        Keyframe keyframe0 = Keyframe.ofFloat(0.0f, 0.0f);
//
//        Keyframe keyframe1 = Keyframe.ofFloat(0.25f, -5.0f);
//        Keyframe keyframe2 = Keyframe.ofFloat(0.75f, 5.0f);
//        Keyframe keyframe3 = Keyframe.ofFloat(1.0f, 0.0f);
//
//        PropertyValuesHolder holderKeyFrame = PropertyValuesHolder.ofKeyframe("rotation",
//                keyframe0, keyframe1, keyframe2, keyframe3);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderKeyFrame);

        objectAnimator.setDuration(200);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setInterpolator(new LinearInterpolator());
//        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

    }

    public void onStop(View view){

        for (Animator animator : animations) {
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            }
        }

        for (ImageView iv : imageViews) {
            iv.setRotation(0);
        }


    }

    private float getDP(int num){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
    }

}
