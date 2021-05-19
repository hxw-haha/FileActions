package com.hxw.file.actions.entity;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/18</p>
 * <p>更改时间：2021/5/18</p>
 * <p>版本号：1</p>
 */
public class ActionEntity {
    /**
     * 名字
     */
    public String name;
    /**
     * 是否选中
     */
    public boolean isChecked;

    /**
     * 文件的绝对路径
     */
    public String absolutePath;

    public static ActionEntity factory(String absolutePath, String name, boolean isChecked) {
        final ActionEntity entity = new ActionEntity();
        entity.absolutePath = absolutePath;
        entity.name = name;
        entity.isChecked = isChecked;
        return entity;
    }
}
