package com.sserdiuk.covid_19.country

import com.sserdiuk.covid_19.ViewCallback
import com.sserdiuk.covid_19.entities.ResultItem

interface CountryCallbacks : ViewCallback {
    fun showData(item: ResultItem)
    fun showWarning(message: String)
    fun showError(message: String)
    fun setBackground(path: String)
    fun showPB(show: Boolean)
}