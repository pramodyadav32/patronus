package com.patronus.network.interceptors

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.GsonBuilder
import com.patronus.util.Constant
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.converter.jackson.JacksonConverterFactory


/**
 * Created by Pramod on 3/31/23.
 */
object ApiFactory {
    const val aPIBaseUrl = Constant.BASE_URL
    private var httpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private val yelpFusionApi: ApiService
        private get() {
            httpClient = Builder()
                .addInterceptor(ErrorHandlingInterceptor())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(aPIBaseUrl)
                .addConverterFactory(jacksonFactory)
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    private val okHttpClient: OkHttpClient?
        private get() {
            if (httpClient == null) {
                val builder: Builder = Builder()
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .writeTimeout(90, TimeUnit.SECONDS)
                httpClient = builder.build()
            }
            return httpClient
        }
    val application: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(aPIBaseUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(SynchronousCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
    private val jacksonFactory: JacksonConverterFactory
        private get() {
            val mapper = ObjectMapper()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            return JacksonConverterFactory.create(mapper)
        }
    val gsonConverterFactory: Converter.Factory
        get() = GsonConverterFactory.create(GsonBuilder().disableHtmlEscaping().create())
}