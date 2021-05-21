package com.hxw.file.http.progress;

/**
 * <p>文件描述：下载、上传文件进度回调接口</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/21</p>
 * <p>更改时间：2021/5/21</p>
 * <p>版本号：1</p>
 */
public interface IProgressListener {
    /**
     * @param currentLength 当前下载长度
     * @param totalLength   总长度
     * @param done          是否完成 <p>true：完成，false：失败</p>
     */
    void onProgress(long currentLength, long totalLength, boolean done);
}
