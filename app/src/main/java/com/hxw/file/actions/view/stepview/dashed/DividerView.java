package com.hxw.file.actions.view.stepview.dashed;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hxw.file.actions.R;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/27</p>
 * <p>更改时间：2021/7/27</p>
 * <p>版本号：1</p>
 */
public class DividerView extends View {
    private static final int ORIENTATION_HORIZONTAL = 0;
    private DividerStyle mDividerStyle;
    private Paint mPaint;

    public DividerView(Context context) {
        this(context, null);
    }

    public DividerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArrayData(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        //防抖动
        mPaint.setDither(true);
        //去锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mDividerStyle.dashThickness);
        mPaint.setColor(mDividerStyle.color);
        mPaint.setPathEffect(new DashPathEffect(
                new float[]{mDividerStyle.dashGap, mDividerStyle.dashLength,}, 0));
    }

    private void initTypedArrayData(Context context, AttributeSet attrs) {
        final int defaultTextColor = Color.parseColor("#000000");
        mDividerStyle = new DividerStyle();
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DividerView);
        mDividerStyle.dashGap = array.getDimensionPixelSize(R.styleable.DividerView_dashGap, 5);
        mDividerStyle.dashLength = array.getDimensionPixelSize(R.styleable.DividerView_dashLength, 5);
        mDividerStyle.dashThickness = array.getDimensionPixelSize(R.styleable.DividerView_dashThickness, 3);
        mDividerStyle.color = array.getColor(R.styleable.DividerView_divider_line_color, defaultTextColor);
        mDividerStyle.orientation = array.getInt(R.styleable.DividerView_divider_orientation, ORIENTATION_HORIZONTAL);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDividerStyle.orientation == ORIENTATION_HORIZONTAL) {
            float center = getHeight() * 0.5f;
            canvas.drawLine(0, center, getWidth(), center, mPaint);
        } else {
            float center = getWidth() * 0.5f;
            canvas.drawLine(center, 0, center, getHeight(), mPaint);
        }
    }


    static class DividerStyle {
        int dashGap;
        int dashLength;
        int dashThickness;
        int color;
        int orientation;
    }
}
