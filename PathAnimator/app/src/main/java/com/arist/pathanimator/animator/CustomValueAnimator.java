package com.arist.pathanimator.animator;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;

/**
 * Created by arist on 16/9/21.
 */
public class CustomValueAnimator extends ValueAnimator {

    private Object tag;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public static CustomValueAnimator ofFloat(float... values) {
        CustomValueAnimator anim = new CustomValueAnimator();
        anim.setFloatValues(values);
        return anim;
    }

    public static CustomValueAnimator ofObject(TypeEvaluator evaluator, Object... values) {
        CustomValueAnimator anim = new CustomValueAnimator();
        anim.setObjectValues(values);
        anim.setEvaluator(evaluator);
        return anim;
    }

}
