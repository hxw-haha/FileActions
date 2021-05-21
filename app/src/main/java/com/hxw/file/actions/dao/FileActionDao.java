package com.hxw.file.actions.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hxw.file.actions.api.FileActionApi;
import com.hxw.file.actions.entity.AllFilesResponse;
import com.hxw.file.actions.entity.UploadResponse;
import com.hxw.file.actions.executor.GlobalExecutor;
import com.hxw.file.actions.utils.ConvertUtils;
import com.hxw.file.actions.utils.FileUtil;
import com.hxw.file.http.HttpFactory;
import com.hxw.file.http.help.HttpHelper;
import com.hxw.file.http.progress.IProgressListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/18</p>
 * <p>更改时间：2021/5/18</p>
 * <p>版本号：1</p>
 */
public class FileActionDao {
    public static LiveData<UploadResponse> upload(@NonNull List<String> filePaths) {

        if (filePaths.size() == 0) {
            MutableLiveData<UploadResponse> liveData = new MutableLiveData<>();
            liveData.postValue(null);
            return liveData;
        }
        final List<File> uploadFiles = new ArrayList<>();
        for (String filePath : filePaths) {
            final File uploadFile = new File(filePath);
            if (uploadFile.exists() && uploadFile.isFile()) {
                uploadFiles.add(uploadFile);
            }
        }
        if (uploadFiles.size() == 0) {
            MutableLiveData<UploadResponse> liveData = new MutableLiveData<>();
            liveData.postValue(null);
            return liveData;
        }
        return HttpFactory.getInstance().createProgressUpload(FileActionApi.class, new IProgressListener() {
            @Override
            public void onProgress(long bytesWritten, long contentLength, boolean done) {
                Log.e("FileActionDao", "上传::::bytesWritten:" + bytesWritten
                        + ",contentLength:" + contentLength
                        + ",done:" + done);
            }
        }).upload(HttpHelper.filesToMultipartBody(uploadFiles));
    }

    public static LiveData<AllFilesResponse> getAllFiles() {
        return HttpFactory.getInstance().create(FileActionApi.class).getAllFiles();
    }

    public static void download(@NonNull final Context context, @NonNull List<String> downloadFileUrl) {
        if (downloadFileUrl.size() == 0) {
            return;
        }
        for (final String fileUrl : downloadFileUrl) {
            HttpFactory.getInstance().createProgressDownload(FileActionApi.class, new IProgressListener() {
                @Override
                public void onProgress(long bytesRead, long contentLength, boolean done) {
                    Log.e("FileActionDao", "下载::::bytesWritten:" + bytesRead
                            + ",contentLength:" + contentLength
                            + ",done:" + done);
                }
            }).download(fileUrl).observe((LifecycleOwner) context, new Observer<ResponseBody>() {
                @Override
                public void onChanged(final ResponseBody responseBody) {
                    if (responseBody != null) {
                        GlobalExecutor.INSTANCE.execute(0, new Runnable() {
                            @Override
                            public void run() {
                                FileUtil.writeResponseBodyToDisk(responseBody, context, ConvertUtils.lastUrlName(fileUrl));
                            }
                        });
                    }
                }
            });
        }
    }
}
