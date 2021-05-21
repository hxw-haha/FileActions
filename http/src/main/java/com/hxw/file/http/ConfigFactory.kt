package com.hxw.file.http

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.protobuf.ProtoConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/3/31</p>
 * <p>更改时间：2021/3/31</p>
 * <p>版本号：1</p>
 */
object ConfigFactory {
    private val gsonBuilder = GsonBuilder()
        .setLenient()
        .setDateFormat("yyyy-MM-dd HH:mm:ss").create()
    private val retrofitMap: ConcurrentHashMap<String, Retrofit> = ConcurrentHashMap()
    private var httpClient: OkHttpClient? = null

    fun getRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        if (retrofitMap.containsKey(baseUrl)) {
            var retrofit = retrofitMap[baseUrl]
            if (retrofit == null) {
                retrofit = createRetrofit(baseUrl, client)
            }
            retrofitMap[baseUrl] = retrofit
            return retrofit
        }
        val retrofit = createRetrofit(baseUrl, client)
        retrofitMap[baseUrl] = retrofit
        return retrofit
    }

    /**
     * 创建Retrofit对象
     *
     * @param baseUrl baseUrl
     * @return Retrofit
     */
    private fun createRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    fun getHttpClient(timeout: Long, interceptors: List<Interceptor>): OkHttpClient {
        if (httpClient == null) {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(timeout, TimeUnit.SECONDS)
            builder.readTimeout(timeout, TimeUnit.SECONDS)
            builder.writeTimeout(timeout, TimeUnit.SECONDS)
            builder.hostnameVerifier(getHostnameVerifier())
            //允许失败重试
            builder.retryOnConnectionFailure(true)
            builder.sslSocketFactory(getSSLSocketFactory(), getX509TrustManager())
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
            httpClient = builder.build()
        }
        return httpClient!!
    }

    private fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _, _ -> true }
    }

    private fun getSSLSocketFactory(): SSLSocketFactory {
        return try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(
                null,
                getTrustManager(),
                SecureRandom()
            )
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getTrustManager(): Array<TrustManager>? {
        return arrayOf(getX509TrustManager())
    }

    private fun getX509TrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }
}