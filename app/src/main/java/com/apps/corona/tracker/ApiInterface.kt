package com.shon.projects.payappmodel.utils.glide.networking

import com.apps.corona.tracker.CoronaByCountry
import com.apps.corona.tracker.CoronaCountModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface ApiInterface {

    @GET("worldstat.php")
    fun getData(
        @HeaderMap headers: Map<String, String>
    ): Call<CoronaCountModel>

    @GET("cases_by_country.php")
    fun getCountryStats(
        @HeaderMap headers: Map<String, String>
    ): Call<CoronaByCountry>


}