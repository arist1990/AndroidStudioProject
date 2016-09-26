package com.arist.pathanimator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by arist on 16/9/12.
 */
public class CustomPointView extends View {

    public static String[] chars = new String[]{
            "A", "B", "C", "D",
            "E", "F", "G", "H",
            "I", "J", "K", "L",
            "M", "N", "O", "P",
            "Q", "R", "S", "T",
            "U", "V", "W", "X",
            "Y", "Z"
    };

    public static int VALUE_END = chars.length-1;
    public static int VALUE_START = 0;

    private int index = 0;
    private Paint paint;

    public CustomPointView(Context context) {
        super(context);
    }

    public CustomPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        paint = new Paint();
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (paint == null) {
            init();
        }

        if (index < VALUE_START) {
            index = VALUE_START;
        }

        if (index > VALUE_END) {
            index = VALUE_END;
        }

        String text = chars[index];

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);

        canvas.drawText(text, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);

    }

    public void setIndex(int index){
        this.index = index;
        invalidate();
    }


}
