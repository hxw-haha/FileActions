package com.hxw.file.actions.view.draw.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hxw.file.actions.view.draw.DrawData;

import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/12</p>
 * <p>更改时间：2021/7/12</p>
 * <p>版本号：1</p>
 */
public class BarView extends View {
    /**
     * 默认大小
     */
    private static final int DEFAULT_SIZE = 200;

    /**
     * 宽
     */
    private int mWidth = DEFAULT_SIZE;
    /**
     * 高
     */
    private int mHeight = DEFAULT_SIZE;

    private Paint mPaint;
    private List<DrawData> mBarDataList;
    private int mMaxCount;
    private int mBarWidthSize, mTopHintTextSize, mBottomHintTextSize;

    public void setBarDataList(List<DrawData> barDataList) {
        if (barDataList == null || barDataList.size() == 0) {
            return;
        }
        for (DrawData pieData : barDataList) {
            mMaxCount = Math.max(mMaxCount, pieData.count);
        }
        this.mBarDataList = barDataList;
        invalidate();
    }

    public BarView(Context context) {
        this(context, null);
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        //防抖动
        mPaint.setDither(true);
        //去锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#B1B0AF"));
        mPaint.setStrokeWidth(3);
        mTopHintTextSize = sp2px(10);
        mBarWidthSize = sp2px(10);
        mBottomHintTextSize = sp2px(13);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = width + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = height + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }

        setMeasuredDimension(width, height);
        mWidth = width;
        mHeight = height;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBarDataList == null || mBarDataList.size() == 0) {
            return;
        }
        canvas.translate(0, mHeight - mBottomHintTextSize * 3f / 2);

        canvas.drawLine(0, 0, mWidth, 0, mPaint);

        final float averageWidth = (float) mWidth / mBarDataList.size();
        final int barDataLength = mBarDataList.size();
        for (int i = 0; i < barDataLength; i++) {
            final DrawData data = mBarDataList.get(i);
            //每个条形图宽度中心点
            final float itemCentre = averageWidth * i + averageWidth / 2;
            //每个条形图高度
            final int itemBarHeight = data.count * (mHeight - mBottomHintTextSize * 3 / 2 - mTopHintTextSize * 2) / mMaxCount;

            drawTopHintText(canvas, data, itemCentre, itemBarHeight);

            drawBar(canvas, data, itemCentre, itemBarHeight);

            drawBottomHintText(canvas, data, itemCentre);
        }
    }

    /**
     * 画顶部提示信息
     *
     * @param canvas
     * @param data          绘制内容
     * @param itemCentre    条形图宽度中心点
     * @param itemBarHeight 条形图高度
     */
    private void drawTopHintText(Canvas canvas, DrawData data, float itemCentre, int itemBarHeight) {
        mPaint.setTextSize(mTopHintTextSize);
        mPaint.setColor(Color.parseColor("#B1B0AF"));
        final String hint = String.valueOf(data.count);
        final int itemTextHeight = itemBarHeight + mTopHintTextSize;
        float hintTextWidth = measureWidth(hint);
        canvas.drawText(hint, itemCentre - hintTextWidth / 2f,
                -itemTextHeight, mPaint);
    }

    /**
     * 绘制条形图
     *
     * @param canvas
     * @param data          绘制内容
     * @param itemCentre    条形图宽度中心点
     * @param itemBarHeight 条形图高度
     */
    private void drawBar(Canvas canvas, DrawData data, float itemCentre, int itemBarHeight) {
        //每个条形图颜色
        mPaint.setColor(Color.parseColor(data.color));
        //画条形图
        canvas.drawRect(itemCentre - (mBarWidthSize / 2f),
                -itemBarHeight,
                itemCentre + (mBarWidthSize / 2f), 0, mPaint);

        //画顶部圆
        canvas.drawCircle(itemCentre,
                -itemBarHeight,
                mBarWidthSize / 2f, mPaint);
    }

    /**
     * 画底部提示信息
     *
     * @param canvas
     * @param data       绘制内容
     * @param itemCentre 条形图宽度中心点
     */
    private void drawBottomHintText(Canvas canvas, DrawData data, float itemCentre) {
        mPaint.setTextSize(mBottomHintTextSize);
        mPaint.setColor(Color.parseColor("#B1B0AF"));
        final String hint = String.valueOf(data.name);
        float hintTextWidth = measureWidth(hint);
        canvas.drawText(hint, itemCentre - hintTextWidth / 2f,
                mBottomHintTextSize, mPaint);
    }


    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

/*
    private int measureHeight() {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        return ~fm.top - (~fm.top - ~fm.ascent) - (fm.bottom - fm.descent);
    }
*/

    private int measureWidth(String text) {
        return (int) mPaint.measureText(text);
    }
}
