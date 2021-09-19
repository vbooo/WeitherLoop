package com.weither.weitherloop.domain.repository

import com.weither.weitherloop.domain.Result
import com.weither.weitherloop.domain.usecase.CurrentCityWeatherResult
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun getCurrentCityWeather(nameCity: String): Flow<Result<CurrentCityWeatherResult>>
}
