package com.arist.pathanimator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.BallLoadingView;
import com.arist.pathanimator.view.BallRadiusLoadingView;

public class BallRadiusLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_ball_radius_loading);


        init();

    }

    @Override
    public void onBackPressed() {
        BallRadiusLoadingActivity.this.finish();
    }

    private BallRadiusLoadingView ballLoadingView;
    private void init(){

        ballLoadingView = (BallRadiusLoadingView) findViewById(R.id.ballLoadingView);
    }

    public void onStart(View view){
        ballLoadingView.startLoading();
    }

    public void onStop(View view){
        ballLoadingView.stopLoading();
    }

}
