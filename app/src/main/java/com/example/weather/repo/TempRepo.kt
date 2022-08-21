package com.example.weather.repo

import android.util.Log
import com.example.weather.api.TemperatureApi
import com.example.weather.models.Temperature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TempRepo @Inject constructor(val api: TemperatureApi) {
    fun getCity(myCity: String) = flow<Temperature> {
        val response = api.getTemperature(city = myCity)
        Log.d("check", "Called Api")
        emit(response)
    }.flowOn(Dispatchers.IO).conflate()

}