package com.cc.t820.data


data class DataBean(var status: String, var data: InfoDataBean)

data class InfoDataBean(
    var token: String,
    var ip: String,
    var region: String,
    var weatherData: WeatherDataBean
)

data class WeatherDataBean(
    var temperature: String,
    var windDirection: String,
    var weather: String,
    var pm25: String
)


data class PoetryBean(var status: String, var data: PoetryDataBean)

data class PoetryDataBean(var content: String, var origin: PoetryOriginBean, var ipAddress:String,)

data class PoetryOriginBean(
    var title: String,
    var dynasty: String,
    var author: String,
    var content: MutableList<String> = mutableListOf(),
    var matchTags: MutableList<String> = mutableListOf(),

)


