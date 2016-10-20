package com.arist.pathanimator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.AiQiYiLoadingView;
import com.arist.pathanimator.view.IOSLoadingView;
import com.arist.pathanimator.view.RingLoadingView;
import com.arist.pathanimator.view.RingLoadingView2;

public class AiQiYiLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_aiqiyi_loading);

        init();

    }

    @Override
    public void onBackPressed() {
        AiQiYiLoadingActivity.this.finish();
    }

    private AiQiYiLoadingView loadingView;
    private void init(){

        loadingView = (AiQiYiLoadingView) findViewById(R.id.loadingView);
    }

    public void onStart(View view){
        loadingView.startLoading();
    }

    public void onStop(View view){
        loadingView.stopLoading();
    }

}
