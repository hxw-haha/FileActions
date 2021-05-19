package com.hxw.file.actions.base

import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * @author hanxw
 * @time 2019/9/27 13:38
 */
interface IBase {
    /**
     * 获取布局ID
     */
    @LayoutRes
    abstract fun layoutId(): Int;

    /**
     *初始化控件
     */
    abstract fun initViews();

    /**
     *初始化数据
     */
    abstract fun initData(savedInstanceState: Bundle?);

    /**
     * 初始化监听器
     */
    abstract fun initListener();

    /**
     * 释放资源
     */
    abstract fun destroy();
}