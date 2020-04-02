package com.sserdiuk.covid_19.country

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import com.sserdiuk.covid_19.App
import com.sserdiuk.covid_19.BaseActivity
import com.sserdiuk.covid_19.R
import com.sserdiuk.covid_19.entities.ResultItem
import com.sserdiuk.covid_19.utils.Constants
import com.sserdiuk.covid_19.utils.Country
import com.sserdiuk.covid_19.utils.RepoUt
import com.sserdiuk.covid_19.utils.network.NetworkUtil

class CountryActivity : BaseActivity(), CountryCallbacks, CountryRouter, CountryAdapter.ListItemClickListener {

    private val presenter = CountryPresenter(RepoUt(NetworkUtil(Constants.API_URL)))

    private lateinit var pb: ProgressBar
    private lateinit var country: TextView
    private lateinit var confirmed: TextView
    private lateinit var recovered: TextView
    private lateinit var deaths: TextView
    private lateinit var latitude: TextView
    private lateinit var longitude: TextView
    private lateinit var errorMessage: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        pb = findViewById(R.id.progressBar)
        country = findViewById(R.id.country)
        confirmed = findViewById(R.id.confirmed)
        recovered = findViewById(R.id.recovered)
        deaths = findViewById(R.id.deaths)
        latitude = findViewById(R.id.latitude)
        longitude = findViewById(R.id.longitude)
        errorMessage = findViewById(R.id.tvError)

        presenter.run {
            if (intent.extras?.containsKey(Country.COUNTRY) == true) countryReceived(
                intent.getStringExtra(
                    Country.COUNTRY
                )!!
            )
            takeView(this@CountryActivity)
            takeRouter(this@CountryActivity)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showData(item: ResultItem) {
        isShowUI(true)
        if (errorMessage.visibility == VISIBLE) errorMessage.visibility = GONE
        country.text = item.country
        confirmed.text = "${getText(R.string.confirmed)}: ${item.confirmed}"
        recovered.text = "${getText(R.string.recovered)}: ${item.recovered}"
        deaths.text = "${getText(R.string.deaths)}: ${item.deaths}"
        latitude.text = "${getString(R.string.latitude)}: ${item.latitude}"
        longitude.text = "${getString(R.string.longitude)}: ${item.longitude}"
    }

    override fun showWarning(message: String) {
        App.showToast(message = message)
    }

    override fun showError(message: String) {
        isShowUI(false)
        pb.visibility = GONE
    }

    override fun setBackground(path: String) {
        TODO("Not yet implemented")
    }

    override fun showPB(show: Boolean) {
        isShowUI(false)
        if (show) {
            pb.visibility = VISIBLE
            errorMessage.visibility = GONE
        } else {
            pb.visibility = GONE
        }
    }

    override fun showTopCountries(items: List<ResultItem>) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.run {
            dropView()
            dropRouter()
        }
    }

    private fun isShowUI(boolean: Boolean) {
        if (boolean && country.visibility == VISIBLE) return
        if (boolean) {
            country.visibility = VISIBLE
            confirmed.visibility = VISIBLE
            recovered.visibility = VISIBLE
            deaths.visibility = VISIBLE
            latitude.visibility = VISIBLE
            longitude.visibility = VISIBLE
        } else {
            country.visibility = GONE
            confirmed.visibility = GONE
            recovered.visibility = GONE
            deaths.visibility = GONE
            latitude.visibility = GONE
            longitude.visibility = GONE
        }
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}