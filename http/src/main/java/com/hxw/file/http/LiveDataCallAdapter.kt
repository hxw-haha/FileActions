package com.hxw.file.http

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/3/31</p>
 * <p>更改时间：2021/3/31</p>
 * <p>版本号：1</p>
 */
class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<R>> {
    override fun responseType() = responseType
    override fun adapt(call: Call<R>): LiveData<R> {
        return object : LiveData<R>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(if (response.isSuccessful) response.body() else null)
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(null)
                        }
                    })
                }
            }
        }
    }
}
