package com.sserdiuk.covid_19.utils

import android.annotation.SuppressLint
import com.sserdiuk.covid_19.entities.Main
import com.sserdiuk.covid_19.entities.MainItem
import com.sserdiuk.covid_19.entities.Result
import com.sserdiuk.covid_19.entities.ResultItem
import com.sserdiuk.covid_19.utils.network.ApiController
import com.sserdiuk.covid_19.utils.network.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RepoUt(networkUtil: NetworkUtil) {
    private val api = networkUtil.retrofit.create(ApiController::class.java)
    var onRequest: OnRequest<MainItem>? = null
    var onCountryRequest: OnRequest<List<ResultItem>>? = null

    fun fetchTotal() {
        Timber.d("Request for all data")
        val call: Call<Main> = api.fetchAll()
        call.enqueue(object : Callback<Main> {
            @SuppressLint("TimberArgCount")
            override fun onResponse(call: Call<Main>, response: Response<Main>) {
                Timber.d("Response is %s ${response.body()}")
                onRequest?.onSuccessRequest(response.body()!![0])
            }

            @SuppressLint("TimberArgCount")
            override fun onFailure(call: Call<Main>, t: Throwable) {
                Timber.d("On failure result is %s ${t.localizedMessage}")
                onRequest?.onFailureRequest(t.localizedMessage ?: "")
            }
        })
    }

    fun fetchItem(name: String) {
        val items: List<ResultItem> = ArrayList()
        val countries = arrayListOf("Canada", "Germany", "Italy", "France", "China", "Austria", "Australia", "Portugal", "Japan")
        var call: Call<Result> = api.getByCountryName(name)
        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
//                onCountryRequest?.onSuccessRequest(response.body()!![0])
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                onCountryRequest?.onFailureRequest(t.localizedMessage ?: "")
            }
        })
        for (item in countries) {
            call = api.getByCountryName(item)
            call.enqueue(object : Callback<Result> {
                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                    item.plus(response.body()!![0])
                }

                override fun onFailure(call: Call<Result>, t: Throwable) {

                }
            })

            onCountryRequest?.onSuccessRequest(items)
        }
    }
}

interface OnRequest<E> {
    fun onSuccessRequest(item: E)
    fun onFailureRequest(message: String)
}