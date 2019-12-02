package com.example.restkotlinized.model

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.restkotlinized.model.remote.ArtistsRemoteSource
import com.example.restkotlinized.model.remote.OnDataRemoteReadyCallback
import com.example.restkotlinized.model.remote.Results
import com.example.restkotlinized.model.sqlite.ArtistsLocalSource
import com.example.restkotlinized.model.sqlite.OnDataLocalReadyCallback
import com.example.restkotlinized.model.sqlite.ProductViewModel

class ModelRepository(
    private val applicationContext: Context,
    viewModelStoreOwner: ViewModelStoreOwner,
    lifecycleOwner: LifecycleOwner
) {
    private val remoteSource = ArtistsRemoteSource()
    private val artistsViewModel =
        ViewModelProvider(viewModelStoreOwner).get(ProductViewModel::class.java)
    private val localSource = ArtistsLocalSource(artistsViewModel, lifecycleOwner)

    fun retrieveData(onDataReadyCallback: OnDataReadyCallback) {
        NetManager(applicationContext).isConnectedToInternet?.let {
            if (it) {
                println("Data from server")
                remoteSource.retrieveData(object : OnDataRemoteReadyCallback {
                    override fun onRemoteDataReady(artists: ArrayList<Results>) {
                        onDataReadyCallback.onDataReady(artists)
                        localSource.saveData(artists)
                    }
                })
            } else {
                println("Data from database")
                localSource.retrieveData(object : OnDataLocalReadyCallback {
                    override fun onLocalDataReady(artists: ArrayList<Results>) {
                        onDataReadyCallback.onDataReady(artists)
                        artists.forEach { artist -> println(artist) }
                    }
                })
            }
        }
    }
}

interface OnDataReadyCallback {
    fun onDataReady(artists: ArrayList<Results>)
}

private class NetManager(private val applicationContext: Context) {
    val isConnectedToInternet: Boolean?
        @SuppressLint("MissingPermission")
        get() {
            val conManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = conManager.activeNetworkInfo
            return network != null && network.isConnected
        }
}