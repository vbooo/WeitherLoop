package com.weither.weitherloop.domain.usecase

import com.weither.weitherloop.domain.IoDispatcher
import com.weither.weitherloop.domain.Result
import com.weither.weitherloop.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentCityWeatherUseCase @Inject constructor (
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<String, CurrentCityWeatherResult>(dispatcher) {

    override fun execute(parameters: String): Flow<Result<CurrentCityWeatherResult>> {
        return repository.getCurrentCityWeather(parameters)
    }
}

data class CurrentCityWeatherResult(
    val idCity: Int?,
    val name: String?,
    val temp: Double?,
    val temp_min: Double?,
    val temp_max: Double?,
    val cloudPercentage: Int?
)
