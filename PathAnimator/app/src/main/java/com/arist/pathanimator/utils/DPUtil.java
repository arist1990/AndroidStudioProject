package com.arist.pathanimator.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by arist on 16/9/13.
 */
public class DPUtil {

    public static float getDP(int num, Resources resources){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, resources.getDisplayMetrics());
    }

}
