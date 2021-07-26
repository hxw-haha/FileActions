package com.hxw.file.actions.view.chart.pie;

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
import androidx.recyclerview.widget.RecyclerView;

import com.hxw.file.actions.R;
import com.hxw.file.actions.view.chart.ChartData;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件描述：饼图-线性</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/12</p>
 * <p>更改时间：2021/7/12</p>
 * <p>版本号：1</p>
 */
public class PieLinearView extends LinearLayout {
    private List<ChartData> mPieDataList = new ArrayList<>();
    private PieView mPieView;
    private RecyclerView mListView;
    private PieLinearStyle mPieStyle;

    public void setPieDataList(List<ChartData> pieDataList) {
        if (pieDataList == null || pieDataList.size() == 0) {
            return;
        }
        mPieDataList.clear();
        mPieDataList.addAll(pieDataList);
        if (mPieView != null) {
            mPieView.setPieDataList(mPieDataList);
        }
        mListView.setAdapter(new PieListAdapter(mPieDataList, mPieStyle));
    }

    public PieLinearView(Context context) {
        this(context, null);
    }

    public PieLinearView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieLinearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initTypedArrayData(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_pie_linear_view, this);
        mPieView = findViewById(R.id.pie_linear_view);
        mPieView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mPieStyle.pieHeight));
        mListView = findViewById(R.id.pie_linear_list_view);
    }

    private void initTypedArrayData(Context context, AttributeSet attrs) {
        final int defaultTextColor = Color.parseColor("#7E7E7D");
        mPieStyle = new PieLinearStyle();
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PieLinearView);
        mPieStyle.pieHeight = array.getDimensionPixelSize(R.styleable.PieLinearView_pie_l_height, sp2px(180));
        mPieStyle.titleSize = array.getDimensionPixelSize(R.styleable.PieLinearView_pie_l_title_size, sp2px(12));
        mPieStyle.titleColor = array.getColor(R.styleable.PieLinearView_pie_l_title_color, defaultTextColor);
        mPieStyle.scaleSize = array.getDimensionPixelSize(R.styleable.PieLinearView_pie_l_scale_size, sp2px(12));
        mPieStyle.scaleColor = array.getColor(R.styleable.PieLinearView_pie_l_scale_color, defaultTextColor);
        array.recycle();
    }

    static class PieListAdapter extends RecyclerView.Adapter<HolderView> {
        private final List<ChartData> mPieDataList;
        private final PieLinearStyle mPieStyle;

        PieListAdapter(List<ChartData> pieDataList, PieLinearStyle pieStyle) {
            this.mPieDataList = pieDataList;
            this.mPieStyle = pieStyle;
        }

        @Override
        public HolderView onCreateViewHolder(ViewGroup viewGroup, int i) {
            View rootView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_pie_linear_view_item, viewGroup, false);
            return new HolderView(rootView);
        }

        @Override
        public void onBindViewHolder(HolderView viewHolder, int position) {
            final ChartData data = mPieDataList.get(position);
            viewHolder.color.setBackgroundColor(Color.parseColor(data.color));
            viewHolder.title.setText(data.name);
            viewHolder.title.setTextColor(mPieStyle.titleColor);
            viewHolder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPieStyle.titleSize);
            viewHolder.count.setText(String.valueOf(data.count));
            viewHolder.count.setTextColor(mPieStyle.scaleColor);
            viewHolder.count.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPieStyle.scaleSize);
        }


        @Override
        public int getItemCount() {
            return mPieDataList != null ? mPieDataList.size() : 0;
        }
    }

    static class HolderView extends RecyclerView.ViewHolder {
        View color;
        TextView title, count;

        public HolderView(View rootView) {
            super(rootView);
            color = rootView.findViewById(R.id.hint_color_view);
            title = rootView.findViewById(R.id.hint_title_view);
            count = rootView.findViewById(R.id.hint_count_view);
        }
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    static class PieLinearStyle {
        int pieHeight;
        int titleSize;
        int titleColor;
        int scaleSize;
        int scaleColor;
    }
}
