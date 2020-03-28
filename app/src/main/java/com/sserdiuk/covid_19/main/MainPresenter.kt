package com.sserdiuk.covid_19.main

import com.sserdiuk.covid_19.App
import com.sserdiuk.covid_19.Presenter
import com.sserdiuk.covid_19.R
import com.sserdiuk.covid_19.entities.MainItem
import com.sserdiuk.covid_19.utils.OnRequest
import com.sserdiuk.covid_19.utils.RepoUt
import timber.log.Timber

class MainPresenter(private val repo: RepoUt) : Presenter<MainCallbacks, MainRouter>(),
    OnRequest<MainItem> {
    override fun onTakeView() {
        super.onTakeView()
        view?.showPB(true)
        repo.run {
            onRequest = this@MainPresenter
            fetchTotal()
        }
    }

    fun onSearchClicked(country: String?) {
        if (country.isNullOrBlank()) view?.showWarning(App.appContext.getString(R.string.type_valid_country))
        else view?.performSearch(country)
    }

    override fun onSuccessRequest(item: MainItem) {
        Timber.d("On Success response")
        view?.run {
            setData(item)
            showPB(false)
        }

    }

    override fun onFailureRequest(message: String) {
        Timber.d("On Failure")
        view?.showError(message)
    }
}