package com.hxw.file.http;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/4/6</p>
 * <p>更改时间：2021/4/6</p>
 * <p>版本号：1</p>
 */
public interface IHttpListener<T> {
    void onHttpCall(boolean succeed, T data, Object flag);
}
