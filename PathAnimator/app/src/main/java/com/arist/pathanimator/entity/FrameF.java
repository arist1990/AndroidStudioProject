package com.arist.pathanimator.entity;

import android.graphics.PointF;

/**
 * Created by arist on 16/9/12.
 */
public class FrameF extends Object {

    private PointF origin;
    private SizeFCustom size;

    public FrameF(){
        this.origin = new PointF(0.0f, 0.0f);
        this.size = new SizeFCustom(0.0f, 0.0f);
    }

    public FrameF(PointF origin, SizeFCustom size) {
        this.origin = origin;
        this.size = size;
    }

    public PointF getOrigin() {
        return origin;
    }

    public void setOrigin(PointF origin) {
        this.origin = origin;
    }

    public SizeFCustom getSize() {
        return size;
    }

    public void setSize(SizeFCustom size) {
        this.size = size;
    }

}
