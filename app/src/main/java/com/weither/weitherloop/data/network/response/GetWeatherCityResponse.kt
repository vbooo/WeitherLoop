package com.weither.weitherloop.data.network.response

data class GetWeatherCityResponse(val name: String?, val id: Int?, val cloud: CloudResponse?, val main: MainResponse?)
