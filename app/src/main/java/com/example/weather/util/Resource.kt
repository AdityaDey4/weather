package com.example.weather.util

sealed class Resource<T>(
    val data: T? = null,
    val msg: String? = null
){
    class Loading<T>(): Resource<T>()
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(data: T?=null, msg: String): Resource<T>(data, msg)
}
