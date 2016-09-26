package com.arist.pathanimator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.arist.pathanimator.R;
import com.arist.pathanimator.adapter.SimpleListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();

    }

//    private ListView listView;
//    private SimpleListAdapter adapter;
    private GridView gridView;
    private SimpleListAdapter adapterGrid;
    private ArrayList<String> datas = new ArrayList<>();
    private ArrayList<Class> datasClass = new ArrayList<>();
    private void init(){

//        listView = (ListView) findViewById(R.id.listView);
//        listView.setOnItemClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);

        datas.add("二次贝塞尔曲线");
        datasClass.add(QuadBezierPathActivity.class);

        datas.add("三次贝塞尔曲线");
        datasClass.add(CubicBezierPathActivity.class);

        datas.add("路径动画演示");
        datasClass.add(PathAnimationActivity.class);

        datas.add("拖拽球");
        datasClass.add(BezierDragCircleActivity.class);

        datas.add("圆形进度动画");
        datasClass.add(CircleLoadingSuccessActivity.class);

        datas.add("Path Test");
        datasClass.add(PathTestActivity.class);

        datas.add("Path Test2");
        datasClass.add(PathTest2Activity.class);

        datas.add("Value Animator");
        datasClass.add(ValueAnimatorActivity.class);

        datas.add("Object Animator");
        datasClass.add(ObjectAnimatorActivity.class);

        datas.add("Property Values Holder");
        datasClass.add(PropertyValuesHolderActivity.class);

        datas.add("组合动画");
        datasClass.add(AnimatorSetActivity.class);

        datas.add("水波纹效果");
        datasClass.add(PathWaterWaveActivity.class);

        datas.add("自定义开关");
        datasClass.add(CustomSwitchActivity.class);

        datas.add("Circle Loading View");
        datasClass.add(CircleLoadingActivity.class);

        datas.add("Gradient Test");
        datasClass.add(GradientTestActivity.class);

        datas.add("Ball Loading");
        datasClass.add(BallLoadingActivity.class);

        datas.add("Ring Loading");
        datasClass.add(RingLoadingActivity.class);

        datas.add("Count Down");
        datasClass.add(CountDownActivity.class);

//        adapter = new SimpleListAdapter(MainActivity.this, datas);
//        listView.setAdapter(adapter);

        adapterGrid = new SimpleListAdapter(MainActivity.this, datas);
        gridView.setAdapter(adapterGrid);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(MainActivity.this, datasClass.get(i)));
    }

}
