package com.apps.corona.tracker

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoronaCountModel(
    @Expose
    @SerializedName("total_cases")
    val total_cases: String,
    @Expose
    @SerializedName("total_deaths")
    val total_deaths: String,
    @Expose
    @SerializedName("total_recovered")
    val total_recovered: String,
    @Expose
    @SerializedName("new_cases")
    val new_cases: String,
    @Expose
    @SerializedName("new_deaths")
    val new_deaths: String,
    @Expose
    @SerializedName("statistic_taken_at")
    val updateTime: String

)

