package com.example.restkotlinized.model

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import java.io.File

class RequestHandler constructor(val context: Context){
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: RequestHandler? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(Any()) {
                INSTANCE ?: RequestHandler(context).also {
                    INSTANCE = it
                }
            }
    }

    fun <T> addToJsonRequestQueue(r: Request<T>){
        queue.add(r)
    }

    val queue: RequestQueue by lazy {
        getRequestQueue()
    }

    private fun getRequestQueue(): RequestQueue {
        val myCacheDir = File(context.cacheDir, "http")
        val cache = DiskBasedCache(myCacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())

        return RequestQueue(cache, network).apply {
            start()
        }
    }
}
