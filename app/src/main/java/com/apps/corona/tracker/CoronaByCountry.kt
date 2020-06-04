package com.apps.corona.tracker

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoronaByCountry(
    @Expose
    @SerializedName("countries_stat")
    val countries_stat: ArrayList<DataModel>


)

data class DataModel(
    @Expose
    @SerializedName("country_name")
    val country_name: String,
    @Expose
    @SerializedName("cases")
    val cases: String,
    @Expose
    @SerializedName("deaths")
    val deaths: String,
    @Expose
    @SerializedName("total_recovered")
    val total_recovered: String,
    @Expose
    @SerializedName("active_cases")
    val active_cases: String,
    @Expose
    @SerializedName("new_cases")
    val new_cases: String,
    @Expose
    @SerializedName("new_deaths")
    val new_deaths: String

)




