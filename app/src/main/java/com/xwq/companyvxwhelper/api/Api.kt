package com.xwq.companyvxwhelper.api

import android.text.TextUtils
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.google.gson.Gson
import com.xwq.companyvxwhelper.BuildConfig
import com.xwq.companyvxwhelper.api.converter.CustomerGsonConverterFactory
import com.xwq.companyvxwhelper.api.interceptor.GzipRequestInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.net.Proxy
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by xwq on 2021 05 27
 */
class Api {
    fun setBaseUrl(value: String) {
        baseUrl = value
    }

    companion object {
        private var baseUrl: String = BuildConfig.TEMPURL
        private var mRetrofit: Retrofit? = null

        /**
         * Retrofit初始化
         *
         * @return
         */
        fun createApi(): Retrofit? {
            val httpClientBuilder: OkHttpClient.Builder =
                OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .proxy(Proxy.NO_PROXY)
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )
            var sslContext: SSLContext? = null
            try {
                sslContext = SSLContext.getInstance("SSL")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            try {
                sslContext!!.init(null, trustAllCerts, SecureRandom())
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }
            val sslSocketFactory = sslContext!!.socketFactory
            httpClientBuilder.sslSocketFactory(sslSocketFactory)
            httpClientBuilder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            httpClientBuilder.addInterceptor(Interceptor { chain ->
                val original = chain.request()
                // Request customization: add request headers
                val requestBuilder = original.newBuilder()

                    .addHeader("User-Agent", BuildConfig.USER_AGENT)
                val request = requestBuilder.build()
                chain.proceed(request)
            })
            httpClientBuilder.addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            httpClientBuilder.addInterceptor(GzipRequestInterceptor())
            mRetrofit = Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CustomerGsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
            return mRetrofit
        }

        /*
        * 创建requestbody
         */
        fun createRequestBody(requestBodyStr : String) : RequestBody{
            var requestBodyStr : String = ""
            var CONTENT_TYPE: MediaType? = MediaType.parse("application/json")
            if (!requestBodyStr.isNullOrEmpty()) {
                var reqStr = Gson().toJson(requestBodyStr)
                return RequestBody.create(CONTENT_TYPE, reqStr)
            }
            return RequestBody.create(CONTENT_TYPE, requestBodyStr)
        }
    }
}