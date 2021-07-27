package com.hxw.file.actions.view.stepview.dashed;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hxw.file.actions.R;

import java.util.List;

/**
 * <p>文件描述：左右显示，中间虚线跟踪</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/7/27</p>
 * <p>更改时间：2021/7/27</p>
 * <p>版本号：1</p>
 */
public class DashedStepView extends FrameLayout {

    private RecyclerView mRecyclerView;

    public void setDashedDataList(List<DashedData> dashedDataList) {
        if (dashedDataList == null || dashedDataList.size() == 0) {
            return;
        }
        mRecyclerView.setAdapter(new DashedAdapter(getContext(), dashedDataList));
    }

    public DashedStepView(@NonNull Context context) {
        this(context, null);
    }

    public DashedStepView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashedStepView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        addView(mRecyclerView, params);
    }

    static class DashedAdapter extends RecyclerView.Adapter<DashedHolder> {
        private final List<DashedData> dashedDataList;
        private final LayoutParams TITLE_PARAMS;
        private final LayoutParams ITEM_PARAMS;

        DashedAdapter(Context context, List<DashedData> dashedDataList) {
            this.dashedDataList = dashedDataList;
            TITLE_PARAMS = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, sp2px(context, 32));
            ITEM_PARAMS = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, sp2px(context, 26));
        }

        @NonNull
        @Override
        public DashedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_step_view_dashed, parent, false);
            return new DashedHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull DashedHolder holder, int position) {
            final DashedData dashedData = dashedDataList.get(position);
            holder.leftTextView.setText(dashedData.leftText);
            holder.rightTextView.setText(dashedData.rightText);
            changeTextStyle(holder.leftTextView, dashedData.isTitle);
            changeTextStyle(holder.rightTextView, dashedData.isTitle);
            holder.rootView.setLayoutParams(dashedData.isTitle ? TITLE_PARAMS : ITEM_PARAMS);
        }

        @Override
        public int getItemCount() {
            return dashedDataList == null ? 0 : dashedDataList.size();
        }

        private void changeTextStyle(TextView textView, boolean isTitle) {
            if (isTitle) {
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(16);
                textView.setBackgroundColor(Color.parseColor("#5FADEC"));
            } else {
                textView.setTextSize(14);
                textView.setTextColor(Color.parseColor("#ACACA6"));
                textView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    static class DashedHolder extends RecyclerView.ViewHolder {
        LinearLayout rootView;
        TextView leftTextView, rightTextView;

        public DashedHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.dashed_root_view);
            leftTextView = itemView.findViewById(R.id.dashed_left_text);
            rightTextView = itemView.findViewById(R.id.dashed_right_text);
        }
    }

    private static int sp2px(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }
}
