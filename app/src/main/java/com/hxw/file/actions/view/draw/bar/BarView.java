package com.hxw.file.actions.view.draw.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.hxw.file.actions.R;
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
     * 宽，高
     */
    private int mWidth = 200, mHeight = 200;
    /**
     * 设置显示样式
     */
    private BarStyle mBarStyle;

    private Paint mPaint;
    private List<DrawData> mBarDataList;
    private int mMaxCount;

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
        initTypedArrayData(context, attrs);
        initPaint();
    }

    private void initTypedArrayData(Context context, AttributeSet attrs) {
        final int defaultTextColor = Color.parseColor("#CCCAC8");
        mBarStyle = new BarStyle();
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BarView);
        mBarStyle.barItemWidth = array.getDimensionPixelSize(R.styleable.BarView_bar_item_width, sp2px(10));
        mBarStyle.topTextSize = array.getDimensionPixelSize(R.styleable.BarView_bar_t_text_size, sp2px(10));
        mBarStyle.topTextColor = array.getColor(R.styleable.BarView_bar_t_text_color, defaultTextColor);
        mBarStyle.bottomTextSize = array.getDimensionPixelSize(R.styleable.BarView_bar_b_text_size, sp2px(13));
        mBarStyle.bottomTextColor = array.getColor(R.styleable.BarView_bar_b_text_color, defaultTextColor);
        mBarStyle.lineColor = array.getColor(R.styleable.BarView_bar_line_color, defaultTextColor);
        array.recycle();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        //防抖动
        mPaint.setDither(true);
        //去锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
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
        canvas.translate(0, mHeight - mBarStyle.bottomTextSize * 3f / 2);

        drawLine(canvas);

        final float averageWidth = (float) mWidth / mBarDataList.size();
        final int barDataLength = mBarDataList.size();
        for (int i = 0; i < barDataLength; i++) {
            final DrawData data = mBarDataList.get(i);
            //每个条形图宽度中心点
            final float itemCentre = averageWidth * i + averageWidth / 2;
            //每个条形图高度
            final int itemBarHeight = data.count * (mHeight - mBarStyle.bottomTextSize * 3 / 2 - mBarStyle.topTextSize * 2) / mMaxCount;

            drawTopHintText(canvas, data, itemCentre, itemBarHeight);

            drawBar(canvas, data, itemCentre, itemBarHeight);

            drawBottomHintText(canvas, data, itemCentre);
        }
    }

    /**
     * 绘制线条
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        mPaint.setColor(mBarStyle.lineColor);
        canvas.drawLine(0, 0, mWidth, 0, mPaint);
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
        mPaint.setTextSize(mBarStyle.topTextSize);
        mPaint.setColor(mBarStyle.topTextColor);
        final String hint = String.valueOf(data.count);
        final int itemTextHeight = itemBarHeight + mBarStyle.topTextSize;
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
        canvas.drawRect(itemCentre - (mBarStyle.barItemWidth / 2f),
                -itemBarHeight,
                itemCentre + (mBarStyle.barItemWidth / 2f), 0, mPaint);

        //画顶部圆
        canvas.drawCircle(itemCentre,
                -itemBarHeight,
                mBarStyle.barItemWidth / 2f, mPaint);
    }

    /**
     * 画底部提示信息
     *
     * @param canvas
     * @param data       绘制内容
     * @param itemCentre 条形图宽度中心点
     */
    private void drawBottomHintText(Canvas canvas, DrawData data, float itemCentre) {
        mPaint.setTextSize(mBarStyle.bottomTextSize);
        mPaint.setColor(mBarStyle.bottomTextColor);
        final String hint = String.valueOf(data.name);
        float hintTextWidth = measureWidth(hint);
        canvas.drawText(hint, itemCentre - hintTextWidth / 2f,
                mBarStyle.bottomTextSize, mPaint);
    }

    private int measureWidth(String text) {
        return (int) mPaint.measureText(text);
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    static class BarStyle {
        int barItemWidth;
        int topTextSize;
        int topTextColor;
        int bottomTextSize;
        int bottomTextColor;
        int lineColor;
    }
}
