package com.weither.weitherloop.ui

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weither.weitherloop.domain.usecase.GetCurrentCityWeatherUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val getCurrentCityWeatherUseCase: GetCurrentCityWeatherUseCase
) : ViewModel() {

    val listOfCity = arrayListOf("First", "Second", "Three")

    var label: MutableLiveData<String>? = MutableLiveData("")
    var progress: MutableLiveData<Double> = MutableLiveData(0.0)
    var tabLabel = arrayListOf(
        "Nous téléchargeons les données...",
        "C’est presque fini...",
        "Plus que quelques secondes avant d’avoir le résultat..."
    )

    init {
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
                println("FINISH")
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
}
