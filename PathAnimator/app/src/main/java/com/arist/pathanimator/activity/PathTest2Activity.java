package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.PathTestView;
import com.arist.pathanimator.view.PathTestView2;

public class PathTest2Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_test2);

        init();

    }

    private PathTestView2 pathTestView2;
    private SeekBar seekBar;
    private void init(){
        pathTestView2 = (PathTestView2) findViewById(R.id.pathTestView2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pathTestView2.setProgress(progress * 1.0f / seekBar.getMax());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void onStart(View view){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pathTestView2.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(1500);
        valueAnimator.start();

    }


}
