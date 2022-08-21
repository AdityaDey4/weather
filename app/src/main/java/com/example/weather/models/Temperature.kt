package com.example.weather.models

data class Temperature(
    val cod: Any,
    val main: Main,
    val name: String,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind,
    val message: String
)