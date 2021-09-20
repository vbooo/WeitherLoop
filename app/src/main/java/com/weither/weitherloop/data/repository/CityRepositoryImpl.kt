package com.weither.weitherloop.data.repository

import com.weither.weitherloop.data.NetworkUtils
import com.weither.weitherloop.data.datasource.CityRemoteDataSource
import com.weither.weitherloop.data.network.response.GetWeatherCityResponse
import com.weither.weitherloop.domain.Result
import com.weither.weitherloop.domain.repository.CityRepository
import com.weither.weitherloop.domain.usecase.CurrentCityWeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of [CityRepository]. It manipulates data related to
 */
class CityRepositoryImpl @Inject constructor(
    private val datasourceCityRemote: CityRemoteDataSource,
    private val networkUtils: NetworkUtils
) : CityRepository {

    fun mapToCurrentCityWeatherResult(data: Any?): CurrentCityWeatherResult? {

        return when (data) {
            is GetWeatherCityResponse -> {
                CurrentCityWeatherResult(
                    idCity = data.id,
                    name = data.name,
                    temp = data.main?.temp,
                    temp_min = data.main?.temp_min,
                    temp_max = data.main?.temp_max,
                    cloudPercentage = data.clouds?.all
                )
            }
            else -> null
        }
    }

    override fun getCurrentCityWeather(nameCity: String): Flow<Result<CurrentCityWeatherResult>> = flow {
        emit(Result.Loading)

        if (networkUtils.hasNetworkConnection()) {
            try {
                // we emit the fresh data
                val response = datasourceCityRemote.getCurrentCityWeather(nameCity)
                emit(Result.Success(data = mapToCurrentCityWeatherResult(response)))
            } catch (e: Exception) {
                emit(Result.Error(exception = e))
            }
        } else {
            emit(Result.Error(exception = null))
        }
    }
}
