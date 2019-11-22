package com.example.restkotlinized.presenter

import com.example.restkotlinized.MVPContract
import com.example.restkotlinized.model.ArtistsModel
import com.example.restkotlinized.model.pojo.Results

class Presenter(private val artistsView: MVPContract.View) : MVPContract.Presenter,
    MVPContract.Model.OnFinishedListener {

    private var artistsModel: ArtistsModel =
        ArtistsModel()

    override fun requestDataFromServer() {
        artistsModel.getArtistsList(this)
        artistsView.showProgress()
    }

    override fun onFinished(results: List<Results>) {
        artistsView.hideProgress()
        artistsView.setDataToRecyclerView(results)
    }

    override fun onFailed(t: Throwable) {
        artistsView.onResponseFailure(t)
    //    artistsView?.hideProgress() - if you want you may add such functional but it's optional
    }

    override fun onDestroy() {
    }
}