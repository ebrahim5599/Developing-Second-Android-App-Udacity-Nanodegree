package com.udacity.asteroidradar.api;

import com.udacity.asteroidradar.PictureOfDay

import retrofit2.http.GET;
import retrofit2.http.Query;


interface PicOfDayService {
    @GET("planetary/apod")
    suspend fun getImageOfDay(
        @Query("api_key")
        key: String = "D7nnLXm5TB4RSycHBU7Su1e8MkfkjQVwBzxQmbYr"
    ): PictureOfDay
}

interface MainService {
    @GET("neo/rest/v1/feed")
    suspend fun getALlAsteroids(
        @Query("start_date") startDate: String = "",
        @Query("end_date") endDate: String = "",
        @Query("api_key")
        key: String = "D7nnLXm5TB4RSycHBU7Su1e8MkfkjQVwBzxQmbYr"
    ): String

}



