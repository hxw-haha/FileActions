package com.hxw.file.actions.utils;

import android.text.TextUtils;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/19</p>
 * <p>更改时间：2021/5/19</p>
 * <p>版本号：1</p>
 */
public class ConvertUtils {
    /**
     * 返回路径最后对应的名字
     *
     * @param path 路径名字
     * @return
     */
    public static String lastUrlName(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        if (!path.contains("/")) {
            return path;
        }
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
