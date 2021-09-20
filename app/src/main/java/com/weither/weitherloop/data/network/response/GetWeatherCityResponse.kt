package com.weither.weitherloop.data.network.response

data class GetWeatherCityResponse(val name: String?, val id: Int?, val clouds: CloudResponse?, val main: MainResponse?)
