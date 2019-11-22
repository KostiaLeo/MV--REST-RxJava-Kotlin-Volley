package com.example.restkotlinized.model

import io.reactivex.Single
import okhttp3.Cache
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.io.File

interface INewsApi {
    @GET("bins/1cwqty")
    fun getAPINewz(): Single<MyNewz>
    companion object Factory {
        private val BASE_URL = "https://api.myjson.com/"
        fun create(): INewsApi {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(INewsApi::class.java)
        }
    }
}