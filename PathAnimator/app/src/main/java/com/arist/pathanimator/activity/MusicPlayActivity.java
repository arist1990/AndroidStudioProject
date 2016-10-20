package com.arist.pathanimator.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.MusicPlayProgressView;

import java.util.Random;

public class MusicPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music_play_layout);

        init();

    }

    private MusicPlayProgressView musicPlayProgressView;
    ValueAnimator valueAnimator;
    private void init(){

        musicPlayProgressView = (MusicPlayProgressView) findViewById(R.id.loadingView);
        musicPlayProgressView.setOnPlayingListener(new MusicPlayProgressView.OnPlayingListener() {
            @Override
            public void onStart(MusicPlayProgressView view) {
                view.setPlayingState(MusicPlayProgressView.PlayingState.playing);

                Toast.makeText(MusicPlayActivity.this, "开始播放", Toast.LENGTH_SHORT).show();

                float startProgress = view.getProgress();
                if (view.getProgress() >= 1.0f) {
                    startProgress = 0;
                }

                // 开始播放
                valueAnimator = ValueAnimator.ofFloat(startProgress, 1.0f);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float progress = (float) valueAnimator.getAnimatedValue();
                        MusicPlayActivity.this.musicPlayProgressView.setProgress(progress);
                    }
                });
                valueAnimator.setDuration((long) (10000 * (1-startProgress)));
//                valueAnimator.setDuration((long) ((10000 + new Random().nextInt(10000)) * (1-startProgress)));
                valueAnimator.setInterpolator(new LinearInterpolator());

                valueAnimator.start();
            }

            @Override
            public void onPause(MusicPlayProgressView view) {
                Toast.makeText(MusicPlayActivity.this, "暂停播放", Toast.LENGTH_SHORT).show();
                view.setPlayingState(MusicPlayProgressView.PlayingState.none);
                valueAnimator.cancel();
            }
        });
    }

}
