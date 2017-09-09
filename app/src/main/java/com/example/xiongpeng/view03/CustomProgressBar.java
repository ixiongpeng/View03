package com.example.xiongpeng.view03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiongpeng on 2017/9/9.
 */

public class CustomProgressBar extends View {

    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private Paint mPaint;
    private int mProgress;
    private int mSpeed;
    private boolean isNext = false;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        int n = a.getIndexCount();

        for(int i = 0; i < n; i++){
            int attr = a.getIndex(i);

            switch(attr){
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);

                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelOffset(attr, 20);
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(attr,20);
                    break;
            }

        }

        a.recycle();
        mPaint = new Paint();
        new Thread(){
            @Override
            public void run() {
                while(true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
        }.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = center - mCircleWidth / 2;
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(center - radius, center - radius, center+ radius, center + radius);


        if(!isNext){
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }else{
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval , -90, mProgress, false, mPaint);
        }
    }






}
