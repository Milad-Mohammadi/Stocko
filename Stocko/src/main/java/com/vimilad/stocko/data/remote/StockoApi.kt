package com.vimilad.stocko.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockoApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apikey") apiKey: String = API_KEY): ResponseBody

    companion object {
        const val API_KEY = "4YHMRH6F07HTKHRJ"
        const val BASE_URL = "https://alphavantage.co"
    }
}