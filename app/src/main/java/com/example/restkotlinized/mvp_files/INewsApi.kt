package com.example.restkotlinized.mvp_files

import com.example.restkotlinized.model.pojo.MyNewz
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface INewsApi {
    @GET("bins/1cwqty")
    fun getAPINewz(): Single<MyNewz>
    companion object Factory {
        private const val BASE_URL = "https://api.myjson.com/"
        fun create(): INewsApi {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(INewsApi::class.java)
        }
    }
}