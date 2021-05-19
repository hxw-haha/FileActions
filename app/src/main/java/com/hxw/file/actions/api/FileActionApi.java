package com.hxw.file.actions.api;

import androidx.lifecycle.LiveData;

import com.hxw.file.actions.entity.AllFilesResponse;
import com.hxw.file.actions.entity.UploadResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/18</p>
 * <p>更改时间：2021/5/18</p>
 * <p>版本号：1</p>
 */
public interface FileActionApi {
    @POST("fileAction/upload")
    LiveData<UploadResponse> upload(@Body MultipartBody multipartBody);

    @GET("fileAction/getAllFiles")
    LiveData<AllFilesResponse> getAllFiles();

    @Streaming
    @GET
    LiveData<ResponseBody> download(@Url String fileUrl);
}
