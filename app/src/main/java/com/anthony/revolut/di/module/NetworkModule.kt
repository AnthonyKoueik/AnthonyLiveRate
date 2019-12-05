package com.anthony.revolut.di.module

import com.anthony.revolut.data.remote.ApiService
import com.anthony.revolut.data.remote.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Module
class NetworkModule {

    companion object {
        //https://revolut.duckdns.org/latest?base=EUR
        private const val NEWS_URL = "https://revolut.duckdns.org/"
    }

    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            //.addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = buildOkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(NEWS_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}