package com.cc.t820.net

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuild {
    companion object {
        //单例一
        private val mInstance: RetrofitBuild by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitBuild()
        }

        fun create(url: String): MyApi {
            return mInstance.getRetrofit(url).create(MyApi::class.java)
        }


        //单例二  效果一样
        @Volatile
        private var instance: RetrofitBuild? = null

        fun getInstance(): RetrofitBuild {
            return instance ?: synchronized(this) {
                instance ?: RetrofitBuild()
            }
        }

    }

    private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        //项目中设置头信息
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)//连接 超时时间
            writeTimeout(10, TimeUnit.SECONDS)//写操作 超时时间
            readTimeout(10, TimeUnit.SECONDS)//读操作 超时时间
            retryOnConnectionFailure(false)//错误不重连

            //  addInterceptor(headerInterceptor)
        }
    }

    private fun getRetrofit(url: String): Retrofit {
        val builder = getOkHttpClientBuilder()
        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
    }

    fun create(url: String): MyApi {
        instance?.let {
            return it.getRetrofit(url).create(MyApi::class.java)
        } ?: let {
            return mInstance.create(url)
        }

    }

}