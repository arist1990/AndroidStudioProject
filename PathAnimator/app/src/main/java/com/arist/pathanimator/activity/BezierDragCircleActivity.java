package com.arist.pathanimator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arist.pathanimator.R;
import com.arist.pathanimator.SimpleListAdapter;

import java.util.ArrayList;

public class BezierDragCircleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bezier_drag_circle);

        init();

    }

    private void init(){


    }

}
