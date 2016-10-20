package com.arist.pathanimator.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arist.pathanimator.R;
import com.arist.pathanimator.view.PageControlAnimated;

public class AnimatedPageControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animated_page_control);

        init();

    }

    PageControlAnimated control;
    private EditText et, etNum;
    private void init(){

        et = (EditText) findViewById(R.id.et);
        etNum = (EditText) findViewById(R.id.etNum);

        control = (PageControlAnimated) findViewById(R.id.view);

        control.setColorOn(Color.parseColor("#553F51B5"));
        control.setColorOff(Color.parseColor("#55FF4081"));
    }

    public void onPre(View view){
        int index = control.getmCurrentIndex();
        int count = control.getmCount();

        if (--index < 0)
            index = count - 1;

        control.setCurrentIndex(index, true);
    }

    public void onNext(View view){
        int index = control.getmCurrentIndex();
        int count = control.getmCount();

        if (++index >= count)
            index = 0;

        control.setCurrentIndex(index, true);
    }

    long t;
    public void onJump(View view){

        int count = control.getmCount();

        int jumpIndex = 0;
        try {
            jumpIndex = Integer.parseInt(et.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(AnimatedPageControlActivity.this, "请输入正确的页数", Toast.LENGTH_LONG).show();
            return;
        }

        if (jumpIndex < 0 || jumpIndex >= count) {
            Toast.makeText(AnimatedPageControlActivity.this, "请输入正确的页数", Toast.LENGTH_LONG).show();
            return;
        }

        control.setCurrentIndex(jumpIndex, true);
    }

    public void onCount(View view){

        int count = 0;
        try {
            count = Integer.parseInt(etNum.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(AnimatedPageControlActivity.this, "请输入正确的", Toast.LENGTH_LONG).show();
            return;
        }

        if (count < 0) {
            Toast.makeText(AnimatedPageControlActivity.this, "请输入正确的", Toast.LENGTH_LONG).show();
            return;
        }

        control.setmCount(count);
    }

}
