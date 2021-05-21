package com.hxw.file.http.help;

import android.util.Log;

import androidx.annotation.NonNull;

import com.hxw.file.http.progress.IProgressListener;
import com.hxw.file.http.progress.upload.ProgressUploadBody;
import com.hxw.file.http.progress.download.ProgressDownloadBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HttpHelper {

    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressDownloadListener(final IProgressListener progressListener) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //增加拦截器
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());

                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressDownloadBody(originalResponse.body(), progressListener))
                        .build();
            }
        });
        return client.build();
    }

    /**
     * 包装OkHttpClient，用于上传文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressUploadListener(final IProgressListener progressListener) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //增加拦截器
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .method(original.method(), new ProgressUploadBody(original.body(), progressListener))
                        .build();
                return chain.proceed(request);
            }
        });
        return client.build();
    }

    /**
     * 请求 body
     */
    public RequestBody getStringBody(@NonNull String String) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String);
    }

    /**
     * 文件上传
     *
     * @param files
     * @return
     */
    public static MultipartBody filesToMultipartBody(@NonNull List<File> files) {
        return filesToMultipartBody("files", files);
    }

    public static MultipartBody filesToMultipartBody(@NonNull String name, @NonNull List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(name, file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }


    /**
     * 将下载文件，写入到磁盘
     *
     * @param body
     * @param absoluteFileName 下载文件绝对路径+文件名字
     * @return
     */
    public static boolean writeResponseBodyToDisk(@NonNull ResponseBody body, @NonNull File absoluteFileName) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(absoluteFileName);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    Log.d("writeResponseBodyToDisk", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}