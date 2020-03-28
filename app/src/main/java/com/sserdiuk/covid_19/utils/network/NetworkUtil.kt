package com.sserdiuk.covid_19.utils.network

import com.sserdiuk.covid_19.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtil(url: String) {

    private fun getClient() : OkHttpClient {
        val httpCLient = OkHttpClient.Builder()
        httpCLient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("x-rapidapi-host", Constants.API_HOST)
                .header("x-rapidapi-key", Constants.API_KEY)
                .method(original.method(), original.body())
                .build()
            return@Interceptor chain.proceed(request)
        })

        return httpCLient.build()
    }

    private fun client() : OkHttpClient {
        val httpClient = OkHttpClient()
        httpClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("x-rapidapi-host", Constants.API_HOST)
                .header("x-rapidapi-key", Constants.API_KEY)
            chain.proceed(requestBuilder.build())
        })
        return httpClient
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}