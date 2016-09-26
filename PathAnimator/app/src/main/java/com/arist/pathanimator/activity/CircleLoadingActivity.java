package com.arist.pathanimator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.CircleLoadingView;
import com.arist.pathanimator.view.CustomSwitch;

public class CircleLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_circle_loading);

        init();

    }

    private CircleLoadingView circleLoadingView;
    private void init(){

        circleLoadingView = (CircleLoadingView) findViewById(R.id.circleLoadingView);
    }

    public void onStart(View view){
        circleLoadingView.startLoading();
    }

    public void onStop(View view){
        circleLoadingView.stopLoading();
    }

}
