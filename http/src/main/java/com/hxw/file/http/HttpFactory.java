package com.hxw.file.http;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.hxw.file.http.help.HttpHelper;
import com.hxw.file.http.progress.IProgressListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/3/31</p>
 * <p>更改时间：2021/3/31</p>
 * <p>版本号：1</p>
 */
public class HttpFactory {
    private HttpConfig mConfig;
    private OkHttpClient mOkHttpClient;

    private HttpFactory() {
    }

    private static class Holder {
        private static HttpFactory INSTANCE = new HttpFactory();
    }

    public static HttpFactory getInstance() {
        return Holder.INSTANCE;
    }

    public void init(HttpConfig config) {
        this.mConfig = config;
        mOkHttpClient = ConfigFactory.INSTANCE.getHttpClient(mConfig.timeout, mConfig.interceptors);
    }

    public Retrofit get() {
        return get("");
    }

    public Retrofit get(String baseUrl) {
        if (this.mConfig == null) {
            throw new IllegalArgumentException("请先调用{HttpManager.init(config)}");
        }
        final String url = TextUtils.isEmpty(baseUrl) ? mConfig.baseUrl : baseUrl;
        return ConfigFactory.INSTANCE.getRetrofit(url,
                mOkHttpClient);
    }

    /**
     * 创建具体实例
     *
     * @param service api实例
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return create(service, "");
    }

    /**
     * 创建具体实例
     *
     * @param service api实例
     * @param baseUrl base url
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service, String baseUrl) {
        return get(baseUrl).create(service);
    }

    /**
     * 创建文件下载、带进度条 service
     *
     * @param service
     * @param listener 进度回调监听器
     * @param <T>
     * @return
     */
    public <T> T createProgressDownload(Class<T> service, IProgressListener listener) {
        return createProgressDownload(service, "", listener);
    }

    /**
     * 创建文件下载、带进度条 service
     *
     * @param service
     * @param baseUrl
     * @param listener 进度回调监听器
     * @param <T>
     * @return
     */
    public <T> T createProgressDownload(Class<T> service, String baseUrl, IProgressListener listener) {
        return get(baseUrl)
                .newBuilder().client(HttpHelper.addProgressDownloadListener(listener))
                .build()
                .create(service);
    }

    /**
     * 创建文件上传、带进度条 service
     *
     * @param service
     * @param listener 进度回调监听器
     * @param <T>
     * @return
     */
    public <T> T createProgressUpload(Class<T> service, IProgressListener listener) {
        return createProgressUpload(service, "", listener);
    }

    /**
     * 创建文件上传、带进度条 service
     *
     * @param service
     * @param baseUrl
     * @param listener 进度回调监听器
     * @param <T>
     * @return
     */
    public <T> T createProgressUpload(Class<T> service, String baseUrl, IProgressListener listener) {
        return get(baseUrl)
                .newBuilder().client(HttpHelper.addProgressUploadListener(listener))
                .build()
                .create(service);
    }


    public <T> void enqueue(@NonNull final Call<T> call, @NonNull final IHttpListener<T> listener) {
        enqueue(call, listener, "");
    }

    public <T> void enqueue(@NonNull final Call<T> call, @NonNull final IHttpListener<T> listener, final Object flag) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.body() != null) {
                    listener.onHttpCall(true, response.body(), flag);
                } else {
                    listener.onHttpCall(false, null, flag);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                listener.onHttpCall(false, null, flag);
            }
        });
    }

    public static class HttpConfig {
        /**
         * 超时时间，单位 s
         */
        private long timeout = 30;
        private String baseUrl;
        private List<Interceptor> interceptors;

        public HttpConfig(String baseUrl) {
            interceptors = new ArrayList<>();
            this.baseUrl = baseUrl;
        }

        /**
         * 超时时间，单位 s
         */
        public HttpConfig setTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * 添加适配器
         *
         * @param interceptor
         * @return
         */
        public HttpConfig addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }
    }
}
