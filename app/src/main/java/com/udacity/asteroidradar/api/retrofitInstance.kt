package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val asteroidRetrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val picOfDayRetrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val picOfDayService: PicOfDayService = picOfDayRetrofit.create(PicOfDayService::class.java)

    val mainService: MainService = asteroidRetrofit.create(MainService::class.java)

}