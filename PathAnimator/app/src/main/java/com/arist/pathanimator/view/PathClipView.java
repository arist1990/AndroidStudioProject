package com.arist.pathanimator.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.arist.pathanimator.R;

/**
 * Created by arist on 16/9/1.
 */
public class PathClipView extends View {

    private Paint paint = new Paint();

    public PathClipView(Context context) {
        super(context);
    }

    public PathClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathClipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int lineSize = 0;
    private Path pathClip;
    private Bitmap bitmap;
    @Override
    protected void onDraw(Canvas canvas) {

        Log.e("info", "onDraw start");
        long t = System.currentTimeMillis();

//        canvas.drawColor(Color.LTGRAY);

        if (lineSize == 0) {
            lineSize = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));

            paint.setStrokeWidth(lineSize);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);

            float width = getWidth();
            float height = getHeight();

            pathClip = new Path();
//            pathClip.moveTo(width / 2, 0);
//            pathClip.lineTo(0, height);
//            pathClip.lineTo(width, height);
//            pathClip.lineTo(width / 2, 0);

            pathClip.addCircle(width / 2, height / 2, Math.min(width, height) / 2, Path.Direction.CCW);

//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_photo);

        }

        canvas.clipPath(pathClip);
        canvas.drawColor(Color.BLUE);
//        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, getWidth(), getHeight()), paint);


        Log.e("info", "onDraw end:" + (System.currentTimeMillis() - t));
        Log.e("info", "=========");

    }

}
