package com.example.restkotlinized.model

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.restkotlinized.MVPContract
import com.example.restkotlinized.model.pojo.MyNewz
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Suppress("UNREACHABLE_CODE")
class ArtistsModel(val context: Context) : MVPContract.Model {
    private val TAG = "MyTAG"
    private val URL = "https://api.myjson.com/bins/1cwqty"
    private var onFinishedListener: MVPContract.Model.OnFinishedListener? = null

    override fun getArtistsList(onFinishedListener: MVPContract.Model.OnFinishedListener) {
        this.onFinishedListener = onFinishedListener
        RequestHandler.getInstance(context).addToJsonRequestQueue(request)
    }

    private val request: JsonObjectRequest by lazy {
        JsonObjectRequest(Request.Method.GET, URL, null,
            Response.Listener {
                val myNewz: MyNewz = Gson().fromJson(it.toString(), MyNewz::class.java)
                onFinishedListener?.onFinished(myNewz.results.toList())
            },
            Response.ErrorListener {
                println(it)
            }
        ).apply {
            tag = TAG
        }
    }
}