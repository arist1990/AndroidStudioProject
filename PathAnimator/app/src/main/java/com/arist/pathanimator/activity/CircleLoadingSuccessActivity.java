package com.arist.pathanimator.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.arist.pathanimator.R;
import com.arist.pathanimator.adapter.CircleLoadingSuccessAdapter;
import com.arist.pathanimator.view.CircleLoadingSuccessView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CircleLoadingSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_circle_loading_success);

        init();

    }

    private Random random = new Random();
    private int minSec = 2;    // 最少十秒下载完成
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0x101) {
                final String id = msg.obj.toString();

                // 开始下载
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float progress = (float) valueAnimator.getAnimatedValue();

                        updateProgress(id, progress);
                    }
                });
                valueAnimator.setDuration(1000 * (minSec + random.nextInt(4)));
                valueAnimator.start();

                mapAni.put(id, valueAnimator);

            } else if (msg.what == 0x102) {
                // 停止下载

                ValueAnimator valueAnimator = (ValueAnimator) mapAni.get(msg.obj.toString());
                valueAnimator.cancel();

            }

        }
    };

    private void updateProgress(String id, float progress){
        Log.e("info", "updateProgress:" + id + "  - progress:" + progress);
        for (JSONObject jsonObject : datas) {
            try {
                if (id.equals(jsonObject.getString("id"))) {
                    jsonObject.put("progress", "" + progress);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private ListView listView;
    private CircleLoadingSuccessAdapter adapter;
    private ArrayList<JSONObject> datas = new ArrayList<>();
    private HashMap<String, Animator> mapAni = new HashMap<>();

    private CircleLoadingSuccessView circleLoadingSuccessView;
    ValueAnimator valueAnimator;
    private void init(){

        circleLoadingSuccessView = (CircleLoadingSuccessView) findViewById(R.id.loadingView);
        circleLoadingSuccessView.setOnCircleLoadingListener(new CircleLoadingSuccessView.OnCircleLoadingListener() {
            @Override
            public void onStart(CircleLoadingSuccessView circleLoadingSuccessView) {
                Log.e("info", "onStart");
                // 开始下载
                valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float progress = (float) valueAnimator.getAnimatedValue();
                        CircleLoadingSuccessActivity.this.circleLoadingSuccessView.setProgress(progress);
                    }
                });
                valueAnimator.setDuration(1000 + random.nextInt(1000));
                valueAnimator.start();
            }

            @Override
            public void onStop(CircleLoadingSuccessView circleLoadingSuccessView) {
                Log.e("info", "onStop");
                valueAnimator.cancel();
            }
        });

        for (int i = 0; i < 20; i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("title", "自定义下载文件" + (i + 1));
                jsonObject.put("progress", "0.0");
                jsonObject.put("id", "" + (100 + i));

                datas.add(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listView = (ListView) findViewById(R.id.listView);

        adapter = new CircleLoadingSuccessAdapter(this, datas);
        adapter.handler = this.handler;

//        listView.setAdapter(adapter);
    }

    public void onStop(View view){
//        circleLoadingSuccessView
    }

}
