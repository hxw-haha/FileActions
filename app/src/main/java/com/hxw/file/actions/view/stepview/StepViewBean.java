package com.hxw.file.actions.view.stepview;

/**
 * @author hanxw
 * @time 2019/7/8 17:27
 */
public class StepViewBean {
    public String title;

    public IItemClickListener listener;

    public StepViewBean(String title) {
        this(title, null);
    }

    public StepViewBean(String title, IItemClickListener listener) {
        this.title = title;
        this.listener = listener;
    }
}
