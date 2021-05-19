package com.hxw.file.actions.page.adapter;

import android.widget.CompoundButton;

import com.hxw.file.actions.R;
import com.hxw.file.actions.base.adapter.BaseRecyclerBindingAdapter;
import com.hxw.file.actions.databinding.ItemLayoutActionsBinding;
import com.hxw.file.actions.entity.ActionEntity;

import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/18</p>
 * <p>更改时间：2021/5/18</p>
 * <p>版本号：1</p>
 */
public class ActionsAdapter extends BaseRecyclerBindingAdapter<ActionEntity, ItemLayoutActionsBinding,
        BaseRecyclerBindingAdapter.BindingHolder<ItemLayoutActionsBinding>> {
    public ActionsAdapter(List<ActionEntity> datas) {
        super(datas);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_layout_actions;
    }

    @Override
    public void convert(BindingHolder<ItemLayoutActionsBinding> holder, final ActionEntity data, int position) {
        holder.dataBinding.setAction(data);
        holder.dataBinding.itemActionView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.isChecked = isChecked;
            }
        });
    }
}
