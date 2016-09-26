package com.arist.pathanimator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.CountDownView;
import com.arist.pathanimator.view.RingLoadingView;

public class CountDownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_count_down);


        init();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        CountDownActivity.this.finish();
    }

    private CountDownView v;
    private void init(){

        v = (CountDownView) findViewById(R.id.view);
    }

    public void onStart(View view){
        v.startLoading();
    }

    public void onStop(View view){
        v.stopLoading();
    }

}
