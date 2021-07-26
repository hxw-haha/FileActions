package com.hxw.file.actions.view.chart.pie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hxw.file.actions.view.chart.ChartData;

import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/12</p>
 * <p>更改时间：2021/7/12</p>
 * <p>版本号：1</p>
 */
public class PieView extends View {
    private static final String[] DEFAULT_COLOR = {"#333", "#568", "#456", "#890"};
    /**
     * 圆默认半径
     */
    private static final int DEFAULT_RADIUS = 200;
    private int mRadius = DEFAULT_RADIUS;
    private Paint mPaint;

    private List<ChartData> mPieDataList;
    private int mSumCount = 0;

    public void setPieDataList(List<ChartData> pieDataList) {
        if (pieDataList == null || pieDataList.size() == 0) {
            return;
        }
        for (ChartData pieData : pieDataList) {
            mSumCount += pieData.count;
        }
        this.mPieDataList = pieDataList;
        invalidate();
    }

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        //防抖动
        mPaint.setDither(true);
        //去锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mRadius * 2 + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mRadius * 2 + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(width, heightSize);
            }
        }

        setMeasuredDimension(width, height);
        mRadius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPieDataList == null || mPieDataList.size() == 0) {
            return;
        }
        //平移Canvas到屏幕中心，之后的绘制以中心点为初始点
        canvas.translate((getWidth() + getPaddingLeft() - getPaddingRight()) >> 1,
                (getHeight() + getPaddingTop() - getPaddingBottom()) >> 1);
        //定义一个RectF对象，表示扇形绘制区域
        final RectF oval = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        float firstAngle = -90.0f;
        float divideAngle;

        for (ChartData pieData : mPieDataList) {
            divideAngle = pieData.count * (360 * 1.0f) / mSumCount;
            mPaint.setColor(Color.parseColor(pieData.color));
            canvas.drawArc(oval, firstAngle, divideAngle, true, mPaint);
            firstAngle += divideAngle;
        }
    }

}
