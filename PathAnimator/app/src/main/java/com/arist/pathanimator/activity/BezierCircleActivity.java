package com.arist.pathanimator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.BezierCircleView;

public class BezierCircleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bezier_circle);

        init();

    }

    private BezierCircleView bezierCircle;
    private void init(){
        bezierCircle = (BezierCircleView) findViewById(R.id.bezierCircle);
    }

    public void onClick(View view){

        bezierCircle.startAnimation();
    }

}
