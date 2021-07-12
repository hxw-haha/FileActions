package com.hxw.file.actions.view.draw.pie;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
        LayoutInflater.from(context).inflate(R.layout.layout_pie_warp_linear_view, this);
        mPieView = findViewById(R.id.pie_warp_linear_view);
        mWarpLayout = findViewById(R.id.pie_warp_linear_layout);
    }
}
