package com.arist.pathanimator;

import android.app.Application;

import org.xutils.x;

/**
 * Created by arist on 16/9/6.
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);

    }


}
