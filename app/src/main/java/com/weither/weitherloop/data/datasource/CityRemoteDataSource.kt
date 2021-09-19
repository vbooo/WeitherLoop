package com.weither.weitherloop.data.datasource

import com.weither.weitherloop.data.network.CityService
import com.weither.weitherloop.data.network.response.GetWeatherCityResponse
import javax.inject.Inject

/**
 * This class handles remote data relative to City
 */
class CityRemoteDataSource @Inject constructor(private val cityService: CityService) {

    suspend fun getCurrentCityWeather(cityName: String): GetWeatherCityResponse {
        return cityService.getCurrentCityWeather(cityName)
    }
}