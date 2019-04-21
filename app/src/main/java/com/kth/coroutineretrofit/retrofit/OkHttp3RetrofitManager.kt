package com.kth.mycoroutine.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kth.coroutineretrofit.Const
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object OkHttp3RetrofitManager {

    private val ALL_TIMEOUT = 10L

    private var okHttpClient: OkHttpClient
    var retrofit: Retrofit

    init{
        /**
         * 로깅 인터셉터 연결
         */
        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BASIC

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
            addInterceptor(HeaderSettingInterceptor())
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
        }.build()

        /**
         * Retrofit2 + OKHttp3를 연결한다
         */
        retrofit = Retrofit.Builder().apply{
            baseUrl(Const.SERVER_URL)
            client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build()
    }

    /**
     *  Request Header를 세팅하는 Interceptor
     */
    private  class HeaderSettingInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val chainRequest = chain.request()

            val request = chainRequest.newBuilder().apply{
                addHeader("Content-Type", "application/json");
            }.build()

            return chain.proceed(request)
        }
    }
    /*
     * 현재 선언된 요청 인터페이스 객체(RetrofitInterface)를 리턴한다
     */
    internal fun <T> getRetrofitService(restClass: Class<T>): T {
        return retrofit.create(restClass)
    }
}
