package com.hxw.file.actions.view.draw.pie;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hxw.file.actions.R;
import com.hxw.file.actions.view.draw.DrawData;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/12</p>
 * <p>更改时间：2021/7/12</p>
 * <p>版本号：1</p>
 */
public class PieLinearView extends LinearLayout {
    private List<DrawData> mPieDataList = new ArrayList<>();
    private PieView mPieView;
    private ListView mListView;

    public void setPieDataList(List<DrawData> pieDataList) {
        if (pieDataList == null || pieDataList.size() == 0) {
            return;
        }
        mPieDataList.clear();
        mPieDataList.addAll(pieDataList);
        if (mPieView != null) {
            mPieView.setPieDataList(mPieDataList);
        }
        mListView.setAdapter(new PieListAdapter(getContext(), mPieDataList));
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
        LayoutInflater.from(context).inflate(R.layout.layout_pie_linear_view, this);
        mPieView = findViewById(R.id.pie_linear_view);
        mListView = findViewById(R.id.pie_linear_list_view);
    }

    static class PieListAdapter extends BaseAdapter {
        private final List<DrawData> mPieDataList;
        private LayoutInflater mInflate;

        PieListAdapter(Context context, List<DrawData> mPieDataList) {
            this.mPieDataList = mPieDataList;
            this.mInflate = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mPieDataList != null ? mPieDataList.size() : 0;
        }

        @Override
        public DrawData getItem(int position) {
            return mPieDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HolderView viewHolder;
            if (convertView == null) {
                convertView = mInflate.inflate(R.layout.layout_pie_linear_view_item, null);
                viewHolder = new HolderView(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HolderView) convertView.getTag();
            }
            final DrawData data = mPieDataList.get(position);
            viewHolder.color.setBackgroundColor(Color.parseColor(data.color));
            viewHolder.title.setText(data.name);
            viewHolder.count.setText(String.valueOf(data.count));
            return convertView;
        }
    }

    static class HolderView {
        View color;
        TextView title, count;

        public HolderView(View rootView) {
            color = rootView.findViewById(R.id.hint_color_view);
            title = rootView.findViewById(R.id.hint_title_view);
            count = rootView.findViewById(R.id.hint_count_view);
        }
    }
}
