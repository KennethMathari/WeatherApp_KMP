package co.ke.weather.multiplatform.data.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item0(
    @SerialName("clouds")
    val clouds: Clouds,
    @SerialName("dt")
    val dt: Int,
    @SerialName("dt_txt")
    val dtTxt: String,
    @SerialName("main")
    val main: Main,
    @SerialName("pop")
    val pop: Double,
    @SerialName("rain")
    val rain: Rain? = null,
    @SerialName("sys")
    val sys: Sys,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("weather")
    val weather: List<Weather>,
    @SerialName("wind")
    val wind: Wind
)