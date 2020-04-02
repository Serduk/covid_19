package com.sserdiuk.covid_19.country

import android.annotation.SuppressLint
import com.sserdiuk.covid_19.Presenter
import com.sserdiuk.covid_19.entities.ResultItem
import com.sserdiuk.covid_19.utils.OnRequest
import com.sserdiuk.covid_19.utils.RepoUt
import timber.log.Timber

class CountryPresenter(private val repo: RepoUt) : Presenter<CountryCallbacks, CountryRouter>(), OnRequest<ResultItem> {

    private var country: String? = null

    fun countryReceived(country: String) {
        this.country = country
    }

    @SuppressLint("TimberArgCount")
    override fun onTakeView() {
        super.onTakeView()
        view?.showPB(true)
        Timber.d("Country should be: %s $country")
        repo.onCountryRequest = this
        repo.fetchItem(country ?: "")
    }

    fun onCountryClicked(position: Int) {

    }

    @SuppressLint("TimberArgCount")
    override fun onSuccessRequest(item: List<ResultItem>) {
        Timber.d("item is: %s $item")
        view?.run {
            showPB(false)
            showData(item[0])
        }
    }

    override fun onFailureRequest(message: String) {
        view?.run {
            showPB(false)
            showError(message)
        }
    }
}