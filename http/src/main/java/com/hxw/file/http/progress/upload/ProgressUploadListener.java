package com.hxw.file.http.progress.upload;

/**
 * 请求体进度回调接口，用于文件上传进度回调
 */
public interface ProgressUploadListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
