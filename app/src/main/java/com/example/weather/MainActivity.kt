package com.example.weather

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.util.Constant
import com.example.weather.util.Resource
import com.example.weather.viewmodels.MyViewmodel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.time.Instant
import java.time.ZoneId
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var locationRequest: LocationRequest
    lateinit var viewModel: MyViewmodel



    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ObsoleteCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MyViewmodel::class.java]

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000


        viewModel.setSearchCity("New Delhi")

        viewModel.searchCity.observe(this, Observer { city->
            viewModel.getTemperature()
        })

        viewModel.cityName.observe(this, Observer { resource ->
            when(resource) {
                is Resource.Success -> {
                    hidePregressBar()
                    resource.data?.let { response->
                        binding.city.text = response.name
                        binding.temp.text = response.main.temp.toString()+"°C"
                        binding.weather.text = response.weather[0].main

                        val min = String.format("%.2f", response.main.temp_min-4.32)
                        val max = String.format("%.2f", response.main.temp_min+3.55)
                        binding.minTemp.text = "Min : $min°C"
                        binding.maxTemp.text = "Max : $max°C"

                        binding.sunrise.text = getTime(response.sys.sunrise).toString()
                        binding.sunset.text = getTime(response.sys.sunset).toString()
                        binding.wind.text = response.wind.speed.toString()+"/h"
                        binding.pressure.text = response.main.pressure.toString()+"m"
                        binding.humidity.text = response.main.humidity.toString()+"%"
                    }
                }
                is Resource.Error -> {
                    hidePregressBar()
                    resource.msg?.let { message->
                        Snackbar.make(binding.root, message, Snackbar.ANIMATION_MODE_SLIDE).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                query?.let { viewModel.setSearchCity(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    private fun showProgressBar() {
        binding.progressbar.visibility = View.VISIBLE
    }
    private fun hidePregressBar() {
        binding.progressbar.visibility = View.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime(time: Long) = Instant.ofEpochSecond(time)
        .atZone(ZoneId.systemDefault()).toLocalTime()


}

