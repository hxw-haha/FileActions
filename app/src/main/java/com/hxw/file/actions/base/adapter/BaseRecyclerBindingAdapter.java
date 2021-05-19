package com.hxw.file.actions.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView 适配器的基类
 *
 * @param <T>  数据类型
 * @param <VH> extends  BaseRecyclerBindingAdapter.BindingHolder
 * @author hanxw
 */
public abstract class BaseRecyclerBindingAdapter<T, DB extends ViewDataBinding,
        VH extends BaseRecyclerBindingAdapter.BindingHolder<DB>>
        extends RecyclerView.Adapter<VH> {

    private List<T> mDatas;
    private Context mContext;

    public BaseRecyclerBindingAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    /**
     * 设置加载数据
     *
     * @param datas
     */
    public void setData(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 设置加载的布局文件
     *
     * @param viewType
     * @return
     */
    @LayoutRes
    public abstract int layoutId(int viewType);

    /**
     * 创建 ViewHolder
     *
     * @param dataBinding 若DB不是 BindingHolder本身，则必须实现该方法
     * @return VH extends RecyclerView.ViewHolder
     */
    public VH createViewHolder(DB dataBinding) {
        return (VH) BaseRecyclerBindingAdapter.createBindingHolder(dataBinding);
    }

    /**
     * 执行具体操作
     *
     * @param holder
     * @param data
     * @param position
     */
    public abstract void convert(VH holder, T data, int position);

    @Override
    @NonNull
    @CallSuper
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        final DB dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                layoutId(viewType), parent, false);
        return createViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @CallSuper
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.isRecyclable() && holder.dataBinding != null) {
            holder.dataBinding.unbind();
        }
    }

    /**
     * 创建默认的 ViewHolder
     *
     * @param dataBinding
     * @param <DB>
     * @return
     */
    public static <DB extends ViewDataBinding> BindingHolder<DB> createBindingHolder(@NonNull DB dataBinding) {
        return new BindingHolder<DB>(dataBinding);
    }

    public static class BindingHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public DB dataBinding;

        public BindingHolder(@NonNull DB dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }
    }

    protected void showToast(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
