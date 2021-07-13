package com.hxw.file.actions.view.draw.pie;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hxw.file.actions.R;
import com.hxw.file.actions.view.draw.DrawData;
import com.hxw.file.actions.view.WarpLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/12</p>
 * <p>更改时间：2021/7/12</p>
 * <p>版本号：1</p>
 */
public class PieWarpLinearView extends LinearLayout {
    private List<DrawData> mPieDataList = new ArrayList<>();
    private PieView mPieView;
    private WarpLinearLayout mWarpLayout;
    private PieWarpStyle mPieStyle;

    public void setPieDataList(List<DrawData> pieDataList) {
        if (pieDataList == null || pieDataList.size() == 0) {
            return;
        }
        mPieDataList.clear();
        mPieDataList.addAll(pieDataList);
        if (mPieView != null) {
            mPieView.setPieDataList(mPieDataList);
        }
        notifyWarpLinearLayout(pieDataList);
    }

    private void notifyWarpLinearLayout(List<DrawData> pieDataList) {
        if (pieDataList == null || pieDataList.size() == 0) {
            return;
        }
        mWarpLayout.removeAllViewsInLayout();
        for (DrawData data : pieDataList) {
            final View itemRootView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_pie_warp_linear_view_item, null);
            final View color = itemRootView.findViewById(R.id.hint_color_view);
            final TextView title = itemRootView.findViewById(R.id.hint_title_view);
            color.setBackgroundColor(Color.parseColor(data.color));
            title.setText(data.name);
            title.setTextColor(mPieStyle.titleColor);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPieStyle.titleSize);
            mWarpLayout.addView(itemRootView);
        }
    }

    public PieWarpLinearView(Context context) {
        this(context, null);
    }

    public PieWarpLinearView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieWarpLinearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initTypedArrayData(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_pie_warp_linear_view, this);
        mPieView = findViewById(R.id.pie_warp_linear_view);
        mPieView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mPieStyle.pieHeight));
        mWarpLayout = findViewById(R.id.pie_warp_linear_layout);
        mWarpLayout.setGravity(mPieStyle.gravity);
        mWarpLayout.setHorizontal_Space(mPieStyle.horizontalSpace);
        mWarpLayout.setVertical_Space(mPieStyle.verticalSpace);
    }

    private void initTypedArrayData(Context context, AttributeSet attrs) {
        final int defaultTextColor = Color.parseColor("#7E7E7D");
        mPieStyle = new PieWarpStyle();
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PieWarpLinearView);
        mPieStyle.pieHeight = array.getDimensionPixelSize(R.styleable.PieWarpLinearView_pie_wl_height, sp2px(180));
        mPieStyle.titleSize = array.getDimensionPixelSize(R.styleable.PieWarpLinearView_pie_wl_title_size, sp2px(12));
        mPieStyle.titleColor = array.getColor(R.styleable.PieWarpLinearView_pie_wl_title_color, defaultTextColor);
        mPieStyle.gravity = array.getInt(R.styleable.PieWarpLinearView_pie_wl_gravity, 1);
        mPieStyle.horizontalSpace = array.getDimensionPixelSize(R.styleable.PieWarpLinearView_pie_wl_horizontal_space, sp2px(8));
        mPieStyle.verticalSpace = array.getDimensionPixelSize(R.styleable.PieWarpLinearView_pie_wl_vertical_space, sp2px(5));
        array.recycle();
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    static class PieWarpStyle {
        int pieHeight;
        int titleSize;
        int titleColor;
        /**
         * 对齐方式 right 0，left 1，center 2
         */
        int gravity;
        /**
         * 水平间距
         */
        int horizontalSpace;
        /**
         * 垂直间距
         */
        int verticalSpace;
    }
}
