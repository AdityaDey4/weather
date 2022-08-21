package com.example.weather.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.models.Temperature
import com.example.weather.repo.TempRepo
import com.example.weather.util.Resource
import com.example.weather.util.TemperatureApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MyViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val repo: TempRepo,
    val app: TemperatureApplication): AndroidViewModel(app) {
    var cityName = MutableLiveData<Resource<Temperature>>()

    @ObsoleteCoroutinesApi
    val searchCity = MutableLiveData<String>()

    @ObsoleteCoroutinesApi
    fun setSearchCity(city: String) {
        searchCity.value = city
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getTemperature() {
        viewModelScope.launch {
            cityName.postValue(Resource.Loading())
            try {
                repo.getCity(searchCity.value.toString())
                    .collect{temp->
                        cityName.postValue(Resource.Success(temp))
                    }
            }catch (t: Throwable) {
                when(t) {
                    is IOException->cityName.postValue(Resource.Error(msg = "Turn on Internet"))
                    else ->cityName.postValue(Resource.Error(msg = "Enter valid city name"))
                }
            }
        }
    }

    /*private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI -> true
                    TYPE_ETHERNET -> true
                    TYPE_MOBILE -> true
                    else -> false
                }
            }
        }

        return false
    }*/
}