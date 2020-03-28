package com.sserdiuk.covid_19.utils.network

import com.sserdiuk.covid_19.entities.Main
import com.sserdiuk.covid_19.entities.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiController {

    @GET("totals?format=undefined")
    fun fetchAll(): Call<Main>

    @GET("country?format=undefined")
    fun getByCountryName(@Query("name") key: String): Call<Result>

    @GET("country/code?format=undefined&code={key}")
    fun getByCode(@Query("key") key: String): Call<Result>
}