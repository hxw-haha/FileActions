package com.hxw.file.actions.view.stepview;

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


import com.hxw.file.actions.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanxw
 * @time 2019/7/8 15:55
 */
public class VerticalStepView extends LinearLayout implements IDrawIndicatorListener {

    private VerticalStepViewIndicator mStepsViewIndicator;
    private FrameLayout mTextContainer;
    private List<StepViewBean> mTitleText;
    private List<TextView> mTextViews = new ArrayList<>();

    public VerticalStepView(Context context) {
        this(context, null);
    }

    public VerticalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_step_view_vertical, this);
        mStepsViewIndicator = rootView.findViewById(R.id.steps_indicator);
        mStepsViewIndicator.setOnDrawListener(this);
        mTextContainer = rootView.findViewById(R.id.rl_text_container);
    }

    /**
     * 设置显示的文字
     *
     * @param stepViewTexts
     * @return
     */
    public void setStepViewTexts(List<StepViewBean> stepViewTexts) {
        mTitleText = stepViewTexts;
        if (stepViewTexts != null) {
            mStepsViewIndicator.setStepNum(mTitleText.size());
        } else {
            mStepsViewIndicator.setStepNum(0);
        }
    }


    @Override
    public void onDrawIndicator() {
        if (mTextContainer != null) {
            mTextViews.clear();
            mTextContainer.removeAllViews();
            final List<Float> complectedXPosition = mStepsViewIndicator.getCircleCenterPointPositionList();
            if (mTitleText != null && complectedXPosition != null && complectedXPosition.size() > 0) {
                final int titleTextSize = mTitleText.size();
                for (int i = 0; i < titleTextSize; i++) {
                    final int index = i;
                    final StepViewBean stepViewBean = mTitleText.get(index);
                    final TextView textView = new TextView(getContext());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    textView.setText(stepViewBean.title);
                    textView.setPadding(3, 2, 0, 2);

                    // 测量宽度
                    final int w = View.MeasureSpec.makeMeasureSpec(0,
                            View.MeasureSpec.UNSPECIFIED);
                    final int h = View.MeasureSpec.makeMeasureSpec(0,
                            View.MeasureSpec.UNSPECIFIED);
                    textView.measure(w, h);

                    float textViewY = complectedXPosition.get(index)
                            - textView.getMeasuredHeight() / 2;

                    textView.setY(textViewY);
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    textView.setTextColor(Color.GRAY);


                    mTextContainer.addView(textView);
                    mTextViews.add(textView);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (stepViewBean.listener != null) {
                                stepViewBean.listener.onItemClick(index, stepViewBean.title);
                            }
                        }
                    });
                }
            }
        }
    }
}
