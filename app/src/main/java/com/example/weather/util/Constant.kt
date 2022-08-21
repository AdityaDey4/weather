package com.example.weather.util

object Constant {
    const val UNITS = "metric"
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val ACCESS_KEY = "1338f7bd76907c0191827010c3aa85fb"
    const val LOCATION_REQUEST_CODE = 1
    const val GPS_TURN_ON_REQUEST_CODE = 2
}
/* CURRENT LOCATION RELATED CODE :-
private suspend fun currentLocation() {
        withContext(Dispatchers.Default) {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if(isGPSTurnOn()) {
                LocationServices.getFusedLocationProviderClient(this)
                    .requestLocationUpdates(locationRequest, object : LocationCallback(){

                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)

                            LocationServices.getFusedLocationProviderClient(this@MainActivity)
                                .removeLocationUpdates(this)
                            //removes all prev location , because we want actual location not last location

                            if(locationResult.locations.size>0) {
                                val index = locationResult.locations.size-1;
                                latitude = locationResult.locations[index].latitude
                                longitude = locationResult.locations[index].longitude

                                Log.d("check", "latitude : $latitude & longitude : $longitude")
                            }
                        }

                    }, Looper.getMainLooper())
            }else turnOnGPS()

        }else checkLocationPermissions()
    }

    private fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(this)
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener(object : OnCompleteListener<LocationSettingsResponse>{
            override fun onComplete(task: Task<LocationSettingsResponse>) {
                try {
                    val response: LocationSettingsResponse = task.getResult(ApiException::class.java)
                    getCurrentLocation()
                }catch(e: ApiException) {
                    when(e.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED->{
                            try {
                                val resolvableApiException = e as ResolvableApiException
                                resolvableApiException.startResolutionForResult(this@MainActivity, Constant.GPS_TURN_ON_REQUEST_CODE)
                            }catch (ex: IntentSender.SendIntentException){}
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> return
                    }
                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constant.GPS_TURN_ON_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                getCurrentLocation()
            }else Snackbar.make(binding.root, "can't access your current location", Snackbar.ANIMATION_MODE_SLIDE).show()
        }
    }
    private fun isGPSTurnOn(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun checkCoarsePermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
    private fun checkFinePermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED

    private fun checkLocationPermissions() {
        val list = mutableListOf<String>()
        if(!checkCoarsePermission()) list.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if(!checkFinePermission()) list.add(Manifest.permission.ACCESS_FINE_LOCATION)

        if(list.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, list.toTypedArray(), Constant.LOCATION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constant.LOCATION_REQUEST_CODE) {
            var allGranted = true
            if(grantResults.isNotEmpty()) {
                for(i in grantResults.indices) {
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED) allGranted = false
                }
            }
            if(allGranted) getCurrentLocation()
            else Snackbar.make(binding.root, "can't access your current location", Snackbar.ANIMATION_MODE_FADE).show()
        }
    }
 */