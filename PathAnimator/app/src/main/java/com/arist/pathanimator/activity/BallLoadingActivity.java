package com.arist.pathanimator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.BallLoadingView;
import com.arist.pathanimator.view.CircleLoadingView;

public class BallLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_ball_loading);


        init();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        BallLoadingActivity.this.finish();
    }

    private BallLoadingView ballLoadingView;
    private void init(){

        ballLoadingView = (BallLoadingView) findViewById(R.id.ballLoadingView);
    }

    public void onStart(View view){
        ballLoadingView.startLoading();
    }

    public void onStop(View view){
        ballLoadingView.stopLoading();
    }

}
