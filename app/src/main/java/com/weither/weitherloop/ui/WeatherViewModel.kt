package com.weither.weitherloop.ui

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weither.weitherloop.domain.Result
import com.weither.weitherloop.domain.usecase.CurrentCityWeatherResult
import com.weither.weitherloop.domain.usecase.GetCurrentCityWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.* // ktlint-disable no-wildcard-imports
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val getCurrentCityWeatherUseCase: GetCurrentCityWeatherUseCase
) : ViewModel() {

    val listCurrentWeather: MutableLiveData<MutableList<CurrentCityWeatherResult>> =
        MutableLiveData(
            mutableListOf()
        )
    val currentWeatherError: MutableLiveData<Boolean> = MutableLiveData(false)

    val countDownIsFinished: MutableLiveData<Boolean> = MutableLiveData(false)

    val listOfCity = arrayListOf("Rennes", "Paris", "Nantes", "Bordeaux", "Lyon", "Montpellier")

    var label: MutableLiveData<String>? = MutableLiveData("")
    var progress: MutableLiveData<Double> = MutableLiveData(0.0)
    var tabLabel = arrayListOf(
        "Nous téléchargeons les données...",
        "C'est presque fini...",
        "Plus que quelques secondes avant d'avoir le résultat..."
    )

    init {
        launchWeatherLoop()
    }

    fun launchWeatherLoop() {
        progress.value = 0.0
        countDownIsFinished.value = false
        listCurrentWeather.value?.clear()
        repeatLabel()
        getCitiesWeather()
        handleProgressBar()
    }

    private fun getCitiesWeather() {
        var iterator = 0
        Timer().schedule(
            Date(), 10000
        ) {

            println("Call API " + listOfCity[iterator])
            getWeather(listOfCity[iterator])

            iterator++
            if (iterator == listOfCity.size) {
                this.cancel()
                println("All cities has been called: timer canceled")
            }
        }
    }

    private fun handleProgressBar() {
        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                progress.postValue(progress.value?.plus(1))
            }

            override fun onFinish() {
                println("FINISH $progress")
                countDownIsFinished.value = true
            }
        }.start()
    }

    private fun repeatLabel() {
        viewModelScope.launch {
            viewModelScope.launch {
                for (item in tabLabel) {
                    label?.postValue(item)
                    delay(6000)
                }
                repeatLabel()
            }
        }
    }

    fun getWeather(nameCity: String) {
        viewModelScope.launch {
            getCurrentCityWeatherUseCase(nameCity).collect {

                when (it) {
                    is Result.Loading -> println("API loading")
                    is Result.Error -> {
                        println("error")
                        currentWeatherError.postValue(true)
                    }

                    is Result.Success -> {
                        println("success")
                        it.data?.let { item ->
                            listCurrentWeather.value?.add(item)
                            println("dataaa $item")
                        }
                        currentWeatherError.postValue(false)
                        println("List response: ${listCurrentWeather.value}")
                    }
                }
            }
        }
    }
}
