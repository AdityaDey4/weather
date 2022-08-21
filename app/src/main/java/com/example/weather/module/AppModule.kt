package com.example.weather.module

import com.example.weather.api.TemperatureApi
import com.example.weather.util.Constant
import com.example.weather.util.TemperatureApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getTemperatureApi(retrofit: Retrofit): TemperatureApi {
        return retrofit.create(TemperatureApi::class.java)
    }

    @Provides
    @Singleton
    fun applicationInstance() = TemperatureApplication()
}