package com.sserdiuk.covid_19.main

import com.sserdiuk.covid_19.ViewCallback
import com.sserdiuk.covid_19.entities.MainItem

interface MainCallbacks : ViewCallback {
    fun setData(item: MainItem)
    fun showPB(isShow: Boolean)
    fun showError(message: String)
    fun performSearch(country: String)
    fun showWarning(message: String)
}