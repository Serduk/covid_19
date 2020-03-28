package com.sserdiuk.covid_19.entities

data class ResultItem(
    val confirmed: String,
    val country: String,
    val deaths: String,
    val latitude: String,
    val longitude: String,
    val recovered: String
)