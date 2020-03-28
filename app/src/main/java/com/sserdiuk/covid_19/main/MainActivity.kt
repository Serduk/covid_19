package com.sserdiuk.covid_19.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.sserdiuk.covid_19.App
import com.sserdiuk.covid_19.BaseActivity
import com.sserdiuk.covid_19.R
import com.sserdiuk.covid_19.country.CountryActivity
import com.sserdiuk.covid_19.entities.MainItem
import com.sserdiuk.covid_19.utils.Constants
import com.sserdiuk.covid_19.utils.Country
import com.sserdiuk.covid_19.utils.RepoUt
import com.sserdiuk.covid_19.utils.network.NetworkUtil

class MainActivity : BaseActivity(), MainCallbacks, MainRouter {

    private val presenter = MainPresenter(RepoUt(NetworkUtil(Constants.API_URL)))

    private lateinit var pb: ProgressBar
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvError: TextView

    private lateinit var confirmed: TextView
    private lateinit var recovered: TextView
    private lateinit var deaths: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pb = findViewById(R.id.mainPB)
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnSearch.setOnClickListener { presenter.onSearchClicked(etSearch.text.toString()) }
        tvError = findViewById(R.id.tvError)

        confirmed = findViewById(R.id.confirmed)
        recovered = findViewById(R.id.recovered)
        deaths = findViewById(R.id.deaths)

        presenter.run {
            takeView(this@MainActivity)
            takeRouter(this@MainActivity)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setData(item: MainItem) {
        if (tvError.visibility == VISIBLE) tvError.visibility = GONE
        showText(true)

        confirmed.text = "${getText(R.string.confirmed)}: ${item.confirmed}"
        recovered.text = "${getText(R.string.recovered)}: ${item.recovered}"
        deaths.text = "${getText(R.string.deaths)}: ${item.deaths}"
    }

    override fun showPB(isShow: Boolean) {
        if (isShow) {
            pb.visibility = VISIBLE
            tvError.visibility = GONE
            showText(false)
        } else pb.visibility = GONE
    }

    override fun showError(message: String) {
        pb.visibility = GONE
        showText(false)
        tvError.visibility = VISIBLE
    }

    override fun performSearch(country: String) {
        val int = Intent(this, CountryActivity::class.java)
        int.putExtra(Country.COUNTRY, country)
        startActivity(int)
    }

    override fun showWarning(message: String) {
        App.showToast(message = message)
    }

    private fun showText(show: Boolean) {
        if (show && confirmed.visibility == VISIBLE) return
        if (show) {
            confirmed.visibility = VISIBLE
            recovered.visibility = VISIBLE
            deaths.visibility = VISIBLE
        } else {
            confirmed.visibility = GONE
            recovered.visibility = GONE
            deaths.visibility = GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.run {
            dropRouter()
            dropView()
        }
    }
}