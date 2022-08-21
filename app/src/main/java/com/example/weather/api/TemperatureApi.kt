package com.example.weather.api

import com.example.weather.models.Temperature
import com.example.weather.util.Constant
import retrofit2.http.GET
import retrofit2.http.Query

interface TemperatureApi {

    @GET("weather/")
    suspend fun getTemperature(
        @Query("q") city: String,
        @Query("appid") appId: String = Constant.ACCESS_KEY,
        @Query("units") units: String = Constant.UNITS
    ): Temperature
}