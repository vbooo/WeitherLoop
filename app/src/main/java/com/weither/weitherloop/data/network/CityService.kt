package com.weither.weitherloop.data.network

import com.weither.weitherloop.data.network.response.GetWeatherCityResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for handle API City data
 */
interface CityService {

    companion object {
        const val baseUrl = "https://api.openweathermap.org/data/2.5/"
        const val token = "6e3bc35608ba7889c77d41ed1dd358e9"
    }

    @GET("weather?lang=fr&appid=$token")
    suspend fun getCurrentCityWeather(@Query("q") cityName: String): GetWeatherCityResponse
}