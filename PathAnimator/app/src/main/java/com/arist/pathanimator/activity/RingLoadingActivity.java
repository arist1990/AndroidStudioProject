package com.arist.pathanimator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.BallLoadingView;
import com.arist.pathanimator.view.IOSLoadingView;
import com.arist.pathanimator.view.RingLoadingView;
import com.arist.pathanimator.view.RingLoadingView2;

public class RingLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_ring_loading);


        init();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        RingLoadingActivity.this.finish();
    }

    private RingLoadingView ringLoadingView;
    private RingLoadingView2 ringLoadingView2;
    private IOSLoadingView iOSLoadingView;
    private void init(){

        ringLoadingView = (RingLoadingView) findViewById(R.id.ringLoadingView);
        ringLoadingView2 = (RingLoadingView2) findViewById(R.id.ringLoadingView2);
        iOSLoadingView = (IOSLoadingView) findViewById(R.id.iOSLoadingView);
    }

    public void onStart(View view){
        ringLoadingView.startLoading();
        ringLoadingView2.startLoading();
        iOSLoadingView.startLoading();
    }

    public void onStop(View view){
        ringLoadingView.stopLoading();
        ringLoadingView2.stopLoading();
        iOSLoadingView.stopLoading();
    }

}
